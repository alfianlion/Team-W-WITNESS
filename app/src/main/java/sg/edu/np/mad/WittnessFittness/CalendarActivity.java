package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    //Session
    SharedPreferences session;
    String userId;

    //Context variable
    Context context;

    //Variables (from activity_main containing RV)
    EditText input_minimum, input_maximum;
    Button btn_minimum, btn_maximum, btnSearch;

    //When user press "fab_Add" button, (used as eventListener)
    FloatingActionButton fab_add;
    AlertDialog builderAlert; //to be used for user confirmation

    // ArrayList to contain all (calendarEvent obj)
    ArrayList<CalendarEvent> listEventObjs = new ArrayList<>();

    // For RecyclerView to use
    AdapterItem adapterItem;
    RecyclerView recyclerView;

    //FireBase Database (instantiation)
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    //Layout Inflater (to inflate form for Registration of event)
    LayoutInflater layoutInflater; //used to inflate layout into MainActivity
    View showInput; //stores user input from the form (Register event)

    //Calendar(used to allow user to choose date)
    Calendar calendar = Calendar.getInstance();

    //
    //Locale id = new Locale("in", "ID");

    //format-ing date time for display (purposes)
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    Date date_minimum; //to store user minimum date from btn_minimal as Date (datatype) into "input_minimum" date field with type (editText)
    Date date_maximum; //to store user maximum date from btn_maximum as Date (datatype) into "input_maximum" date field with type (editText)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Initializing the relevant Variables
        context = this;
        fab_add = findViewById(R.id.fab_add);

        input_minimum = findViewById(R.id.input_minimum);
        input_maximum = findViewById(R.id.input_maximum);

        btn_minimum = findViewById(R.id.btn_minimum);
        btn_maximum = findViewById(R.id.btn_maximum);
        btnSearch = findViewById(R.id.btnSearch);

        recyclerView = findViewById(R.id.recyclerView); //this RV is is refer-ing to the Calendar Events Recycler View.


        //1. Getting Session (user ID attribute in FB db)
        session = getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        userId = session.getString("userId","");

        //2. DatePicker (used when user define Min/Max date)
        //2.1 BUTTON MINIMUM (DATE)
        //when btn_minimum(for user to define minimum date) is clicked this is triggered
        btn_minimum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create DatePickerDialog to listen to which Date user pick-ed
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth); //take the constructor values and set it to Calendar

                        //set the Date user picked into the EditText field
                        input_minimum.setText(simpleDateFormat.format(calendar.getTime()));

                        //set user picked date from Btn into Edit Text as Calendar Datatype
                        date_minimum = calendar.getTime();

                        //determine if both (minimum & maximum) date field is filled
                        String input1 = input_minimum.getText().toString();
                        String input2 = input_maximum.getText().toString();
                        if(input1.isEmpty() || input2.isEmpty()){
                            //if either button have no date defined user is not allowed to search for events
                            btnSearch.setEnabled(false);
                        }else{
                            btnSearch.setEnabled(true);
                        }
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //retrieving the actual dates user picked and storing it in our calendar

                datePickerDialog.show();
            }
        });

        //2.2 BUTTON MAXIMUM (DATE)
        //when btn_maximum(for user to define maximum date) is clicked this is triggered
        btn_maximum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create DatePickerDialog to listen to which Date user pick-ed
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth); //take the constructor values and set it to Calendar

                        //set the Date user picked into the EditText field
                        input_maximum.setText(simpleDateFormat.format(calendar.getTime()));

                        //set user picked date from Btn into Edit Text as Calendar Datatype
                        date_maximum = calendar.getTime();

                        //determine if both (minimum & maximum) date field is filled
                        String input1 = input_maximum.getText().toString();
                        String input2 = input_minimum.getText().toString();
                        if(input1.isEmpty() || input2.isEmpty()){
                            //if either button have no date defined user is not allowed to search for events
                            btnSearch.setEnabled(false);
                        }else{
                            btnSearch.setEnabled(true);
                        }
                    }
                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //retrieving the actual dates user picked and storing it in our calendar

                datePickerDialog.show();
            }
        });

        //3. Find Event based on Date(FB db)
        //SEARCH FOR EVENT (based on (min/max)Date specified)
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create association to FB db reference (to CalendarList event)
                //DatabaseReference databaseReference = database.getReference("User/" + id + "/calList/");
                DatabaseReference databaseReference = database.getReference("User/" + userId);

                //Query from the FB database (based on the user as ref, meaning it differentiate and pick the correct user)
                Query query = databaseReference.child("calList")
                        .orderByChild("dateOfEvent") //This will order it based on the attribute inside of FB db
                        .startAt(date_minimum.getTime()).endAt(date_maximum.getTime());

                //if query successfully returned values this listener will be executed
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Call the showLisener mtd which display the respective obj from the query in the RV
                        showLisener(snapshot); //calls method (3)
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        //4. Add Event (into FB db)
        //ADD/REGISTER EVENT
        //once fab_add(for adding/registering event) button is clicked this is triggered
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //allow user to create an event (with specifications of the event inputs)
                inputData(); //calls method (1)
            }
        });


        //Instantiate all (calEvent objs) into CalendarEvent class, to then display all calEvent in RV
        showData(); //calls method (2)
    }


    //Variables
    //Variable to access (Register event) input-ed data
    EditText et_name, et_timeAllocated, et_dateOfEvent;
    RadioGroup rb_group;
    RadioButton radioButton, rb_workout, rb_running;
    Button btnDateOfEvent, btnSaveData;
    Date registrationDate_input; //to store user registration date from EditText as Date (datatype)


    //TextView to set the user input into the textView (item_layout.xml cardView)
    TextView tv_exerciseType, tv_timeAllocated, tv_dateOfEvent;


    //(1). Allows users to create an Event
    //allow user to create an event (with specifications of the event inputs)
    private void inputData() {
        //Create builder for Alert Dialog (initializing the builderAlert dialog property)
        builderAlert = new AlertDialog.Builder(context).create();

        //inflate the "Register event" activity, where user will be able register event, in mainActivity input_Layout is inflated
        layoutInflater = getLayoutInflater(); //get inflater
        showInput = layoutInflater.inflate(R.layout.input_event_layout, null); //input-ing layout for user to input Data of "register event"

        //Alert Dialog to show the user input
        builderAlert.setView(showInput);

        //Instantiate(by retrieving) all input-ed data elements, by user
        //the input will then be recorded (firebase realtimeDB) and then be displayed in the CardView as (item_cal_layout.xml)
        et_name = showInput.findViewById(R.id.et_name);
        et_timeAllocated = showInput.findViewById(R.id.et_timeAllocated);
        et_dateOfEvent = showInput.findViewById(R.id.et_dateOfEvent);

        rb_group = showInput.findViewById(R.id.rb_group);

        btnDateOfEvent = showInput.findViewById(R.id.btnDateOfEvent);
        btnSaveData = showInput.findViewById(R.id.btnSaveData);

        //Required line to Display AlertDialog
        builderAlert.show();

        //Determine what data is empty(and return error msg if so), before saving into firebase realtime firebase
        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting user input info (and storing it in String Var)
                String name = et_name.getText().toString();
                String timeAllocated = et_timeAllocated.getText().toString();
                String dateOfEvent = et_dateOfEvent.getText().toString();

                if(name.isEmpty()){
                    et_name.setError("This field cannot be empty");
                    et_name.requestFocus();

                }else if(timeAllocated.isEmpty()){
                    et_timeAllocated.setError("This field cannot be empty");
                    et_timeAllocated.requestFocus();

                }else if(dateOfEvent.isEmpty()){
                    et_dateOfEvent.setError("This field cannot be empty");
                    et_dateOfEvent.requestFocus();

                }else{
                    //check selected (input of) Radio Btn grp (Male/Female)
                    int selected = rb_group.getCheckedRadioButtonId();
                    radioButton = showInput.findViewById(selected);

                    //**Creating instance in FB Database
                    //references Firebase Database, point-ing to the user ref
                    //Pointing to the Ref in the DB
                    DatabaseReference databaseReference = database.getReference("User/" + userId);

                    // then creating an instance of the user(OBJ) based on the date and store
                    // the specification input-ed in (Register event), nested inside of date ref inn Fb Database
                    databaseReference.child("calList").child(dateOfEvent).setValue(new CalendarEvent(
                            name,
                            radioButton.getText().toString(),
                            timeAllocated,
                            registrationDate_input.getTime()
                    )).addOnSuccessListener(new OnSuccessListener<Void>() { //executed when user(obj) of event is successfully stored inside Fb database
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Data of event added Successfully", Toast.LENGTH_SHORT).show();
                            builderAlert.dismiss();
                        }
                    }).addOnFailureListener(new OnFailureListener() {       //executed when user(obj) of event failed to stored inside Fb database
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                            builderAlert.dismiss();
                        }
                    });
                }



            }
        });

        //Allow user to pick a date (and set into et_dateOfEvent field)
        //Open the Calendar and Allow user to pick a calendar date for the field on (register Date)
        btnDateOfEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create DatePickerDialog to listen to which Date user pick-ed
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth); //take the constructor values and set it to Calendar

                        //set the Date user picked into the EditText field
                        et_dateOfEvent.setText(simpleDateFormat.format(calendar.getTime()));

                        //set user registration data from Edit Text as Calendar Datatype
                        registrationDate_input = calendar.getTime();
                    }

                },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                //retrieving the actual dates user picked and storing it in our calendar

                datePickerDialog.show();
            }
        });
    }

    //(2). Shows all the Data from FB db (regardless if any date was set)
    //Instantiate all (calendarEvent objs) into CalendarEvent class, to then display all CalendarEvent in RV
    private void showData() {

        //Creating Reference to the DB
        DatabaseReference databaseReference = database.getReference("User/" + userId);


        databaseReference.child("calList").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //this method is called to iterate through dataSnapshot and store it in a list before displaying in RV.
                //the mtd is not written here and called instead is because it can be used repeatedly.
                showLisener(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //(3). Process Snapshots and display data
    //A method to Read the respective User event
    //Store events in a list before displaying in RV
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showLisener(DataSnapshot snapshot) {
        //ensure to not store previous data's
        listEventObjs.clear();

        //iterate through all the event obj's from DataSnapshot
        for(DataSnapshot item : snapshot.getChildren() ){
            //cast the read-ed obj into CalendarEvent obj class
            CalendarEvent calendarEvent = item .getValue(CalendarEvent.class);
            //then add the item into the list of events
            listEventObjs.add(calendarEvent);
        }

        //instantiating an instance of Adapter to use to display all the users in the listEventObjs (which store all instances of events)
        adapterItem = new AdapterItem(context, listEventObjs);
        recyclerView.setAdapter(adapterItem); //allowing the RV to use the adapter
    }


}