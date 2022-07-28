package sg.edu.np.mad.WittnessFittness;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class catalogue extends Fragment implements catalogueAdapter.ItemClickListener{

    View view;
    private Date date;
    MainActivity mainActivity;
    ArrayList<Exercise> datalist;
    public DatePickerDialog datePickerDialog;
    public Button dateButton;
    public ArrayList<Exercise> sortedList = new ArrayList<Exercise>();
    public catalogueAdapter adapter;

    public catalogue() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatePicker();
        SharedPreferences session = requireContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = session.getString("exercises",null);
        Type type = new TypeToken<ArrayList<Workout>>(){
        }.getType();

        datalist = gson.fromJson(json, type);
        if(datalist == null){
            datalist = new ArrayList<>();
        }
        //System.out.println(datalist.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_catalogue, container, false);

            /*date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String date_string = sdf.format(date);*/

        dateButton = view.findViewById(R.id.dateDisplay);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(view);
            }
        });
        //System.out.println("Date Button : " + dateButton.getText().toString());

        for (Exercise w: datalist
        ) {
            //System.out.println("Object Date : " + w.DateDone);
            String chosenDate = w.DateDone;
            if(chosenDate.equals(dateButton.getText().toString())){
                sortedList.add(w);
                //System.out.println("Sorted List : " + sortedList.toString());
            }
        }

        RecyclerView rv = view.findViewById(R.id.workoutRv);
        rv.setHasFixedSize(true);
        adapter = new catalogueAdapter(sortedList);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);

        Button addActivity = view.findViewById(R.id.addActivity);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivityView = new Intent(getActivity(), selectExercise.class );
                startActivity((addActivityView));
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });

        return view;
    }

    private void updateCatalogue(String date){
        System.out.println("Clear");
        sortedList.clear();
        for (Exercise w: datalist
        ) {
            System.out.println("Object Date : " + w.DateDone);
            String chosenDate = w.DateDone;
            if(chosenDate.equals(date)){
                sortedList.add(w);
                System.out.println(w.Title);
            }
        }
        adapter.notifyDataSetChanged();
        System.out.println("Sorted List : " + sortedList.toString());
    }

    @Override
    public void onItemClick(int position) {

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                updateCatalogue(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return day + "-" + getMonthFormat(month) + "-" + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "Jan";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}