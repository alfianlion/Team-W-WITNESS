package sg.edu.np.mad.WittnessFittness;

import static android.content.ContentValues.TAG;

import android.app.Activity;
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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    private Date date;
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

        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String date_string = sdf.format(date);

        TextView dateDisplay = view.findViewById(R.id.dateDisplay);
        dateDisplay.setText(date_string);

        Button addActivity = view.findViewById(R.id.addActivity);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addActivityView = new Intent(getActivity(), selectExercise.class );
                startActivity((addActivityView));
                ((Activity) getActivity()).overridePendingTransition(0,0);
            }
        });

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
        DatabaseReference myRef2 = database.getReference("User/" + id + "/");

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

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // To ensure it does not store the previous iteration data

                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises/Workouts/
                if(dataSnapshot.exists()){
                    String e = dataSnapshot.child("name").getValue(String.class);
                    SharedPreferences.Editor storeUserInfo = session.edit();
                    storeUserInfo.putString("name",e);
                    storeUserInfo.commit();
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