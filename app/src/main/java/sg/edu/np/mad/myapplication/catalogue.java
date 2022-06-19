package sg.edu.np.mad.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link catalogue#newInstance} factory method to
 * create an instance of this fragment.
 */

public class catalogue extends Fragment{

    View view;
    private FirebaseDatabase database;
    private String userId;
    private DatabaseReference myRef;
    private catalogue cat;
    private Date currentDate;
    private ArrayList<Exercise> datalist = new ArrayList<>();

    public catalogue() {
        // Required empty public constructor
    }

    public static catalogue newInstance(String param1, String param2) {
        catalogue fragment = new catalogue();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_catalogue, container, false);
        Button addActivity = view.findViewById(R.id.addActivity);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivityView = new Intent(getActivity(), selectExercise.class );
                startActivity((addActivityView));
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });

        Exercise w = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        Exercise w2 = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        Exercise w3 = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        Exercise w4 = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        Exercise w5 = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        Exercise w6 = new Workout("Your mom", 2, new Date(), "123", 1, 2, "Workout");
        datalist.add(w);
        datalist.add(w2);
        datalist.add(w3);
        datalist.add(w4);
        datalist.add(w5);
        datalist.add(w6);

//        ReadDBRunning();

        RecyclerView rv = view.findViewById(R.id.workoutRv);
        catalogueAdapter adapter = new catalogueAdapter(datalist, catalogue.this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);

        return view;
    }


    //Set a OnClickListener to execute this mtd(like when user a button to view recyclerView)
    public void ReadDBRunning(){

        //1. Get root node of Firebase [**This needs to be determined as global variable**]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //2. Retrieve respective user ID (when user clicks on button(intent that displays recycler View)
        SharedPreferences session = getContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        String id = session.getString("userId","");

        //3. Reference to the Database user
        DatabaseReference myRef = database.getReference("Exercises/Workouts/" + 7772333);

        //4. Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot Snapshot) {

                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises
                if(Snapshot.exists()){
                    // items in "key/value" pairs
                    //One object is used as a key (index) to another object (value).
                    //To access a value in the HashMap, use the get() method and refer to its key

                    Map <String, Object> map = (HashMap<String, Object>) Snapshot.getValue();

                    //this obj can be then accessed via its get method
                    //note the whole obj with the respective/matching userId will be stored in the hash map using key/value pairs,
                    //where the usedId will be the key to identify the obj

                    //Sets global variable, to store what is read
                    String workOutTitle = "";
                    int numOfReps = 1;
                    int numOfSets = 1;
                    String workOutId = "";
                    int timeTaken = 1;
                    Date dateDone = new Date();

                    Iterator<Exercise> it = datalist.iterator();

                    while(it.hasNext()) {
                        //Retriving and Determining if running data exists
                        try {
                            String workoutType = map.get("type").toString();
                            workOutTitle = map.get("title").toString();
                            timeTaken = Integer.parseInt(map.get("timeTaken").toString());
                            numOfSets = Integer.parseInt(map.get("numOfSets").toString());
                            numOfReps = Integer.parseInt(map.get("numOfReps").toString());

                            Workout w = new Workout(workOutTitle, timeTaken, dateDone, workOutId, numOfReps, numOfSets, workoutType);
                            datalist.add(w);
                            it.next();
                        }
                        //if unsuccessful there is no INSTANCE of Running done by user
                        catch (Exception e) {
                            //if unsuccessful does not exists send toast msg
                            Toast.makeText(getContext(), "There is no Workouts completed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    String display = String.valueOf(Snapshot.getValue());
                    Toast.makeText(getContext(), display, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }

        });
    }
}