package sg.edu.np.mad.myapplication;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Log;
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
import com.google.firebase.database.Query;
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
    MainActivity mainActivity;
    SharedPreferences session;


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
/*
        Exercise w = new Workout("Example1", 2, new Date(), "123", 20, 2, "Workout");
        Exercise w2 = new Workout("Example2", 2, new Date(), "123", 10, 2, "Workout");
        Exercise w3 = new Workout("Example3", 2, new Date(), "123", 15, 2, "Workout");
        Exercise w4 = new Workout("Example4", 2, new Date(), "123", 12, 2, "Workout");
        Exercise w5 = new Workout("Example5", 2, new Date(), "123", 13, 2, "Workout");
        Exercise w6 = new Workout("Example6", 2, new Date(), "123", 13, 2, "Workout");
        datalist.add(w);
        datalist.add(w2);
        datalist.add(w3);
        datalist.add(w4);
        datalist.add(w5);
        datalist.add(w6);*/

        ReadDBRunnings();

        RecyclerView rv = view.findViewById(R.id.workoutRv);
        catalogueAdapter adapter = new catalogueAdapter(datalist, catalogue.this);
        LinearLayoutManager layout = new LinearLayoutManager(getContext());

        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);

        return view;
    }


    //Set a OnClickListener to execute this mtd(like when user a button to view recyclerView)
    //** Read FireBase Data to Populate catalogue
    public void ReadDBRunnings(){
        //1. Get root node of Firebase [**This needs to be determined as global variable**]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SharedPreferences session = getActivity().getSharedPreferences("userPreference", Context.MODE_PRIVATE);

        //2. Retrieve respective user ID (to locate the item in the node)
        String id = session.getString("userId","");

        //3. Reference to the User's Exercises
        DatabaseReference myRef = database.getReference("User/" + id + "/eList/");

        //4. Check If userID exists in Db (done in authentication)
//        Query checkUserWO = myRef.child("1"); //checks if user exists in DB
        //userID matches the key(userID) in the Firebase DB //the query will query all instances the matches the given userID


        //5. Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // To ensure it does not store the previous iteration data
                datalist.clear();

                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises/Workouts/
                if(dataSnapshot.exists()){

                    //**FOR LOOP**
                    for (DataSnapshot ds : dataSnapshot.getChildren()){  //for all snapshot, iterate through all snapshots
                        String e = ds.child("type").getValue(String.class);
                        System.out.println("Object: " + e);

                        if (e.equals("Running")){
                            Exercise r = ds.getValue(Running.class);
                            datalist.add(r);
                            System.out.println("Run DONE");
                        } else if (e.equals("Workout")){
                            Exercise w = ds.getValue(Workout.class);
                            datalist.add(w);
                            System.out.println("Workout DONE");
                        }
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled failed", databaseError.toException());
            }
        });
    }


}