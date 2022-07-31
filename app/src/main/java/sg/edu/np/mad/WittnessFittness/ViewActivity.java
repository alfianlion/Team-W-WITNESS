package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ViewActivity extends AppCompatActivity {

    ArrayList<Exercise> datalist;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_details);

        Intent getIntent = getIntent();
        title = getIntent.getStringExtra("exerciseTitle");
        String timetaken = getIntent.getStringExtra("exerciseTimeTaken");
        String type = getIntent.getStringExtra("exerciseType");
        String userId = getIntent.getStringExtra("exerciseUserID");

        TextView titletxt = findViewById(R.id.viewWorkoutTitle);
        TextView timetakentxt = findViewById(R.id.timeTaken);
        TextView workouttypetxt = findViewById(R.id.workoutType);

        SharedPreferences session = getApplicationContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);

        SharedPreferences.Editor storeUserInfo = session.edit();
        storeUserInfo.putString("title",title);
        storeUserInfo.commit();

        titletxt.setText(title);
        if (timetaken == "1"){
            timetakentxt.setText(timetaken + "minute");
        } else {
            timetakentxt.setText(timetaken + "minutes");
        }
        workouttypetxt.setText(type);

        Button fav = findViewById(R.id.favouriteBtn);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DALExercise(userId);
            }
        });
    }

    //Set a OnClickListener to execute this mtd(like when user a button to view recyclerView)
    //** Read FireBase Data to Populate catalogue
    public void DALExercise(String id){
        //1. Get root node of Firebase [**This needs to be determined as global variable**]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //2. Reference to the User's Exercises
        DatabaseReference myRef = database.getReference("User/" + id + "/eList/");
        DatabaseReference myRef2 = database.getReference("User/" + id + "/");
        DatabaseReference exercisechild = FirebaseDatabase.getInstance().getReference("User/" + id + "/eList/");

        //3. Check If userID exists in Db (done in authentication)
//        DatabaseReference ExerciseId = myRef.getKey().getReference("ZONES");//checks if user exists in DB
        //userID matches the key(userID) in the Firebase DB //the query will query all instances the matches the given userID


        //4. Read from the database
        exercisechild.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                SharedPreferences session = getApplicationContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                String t = session.getString("title","");
                for (DataSnapshot zoneSnapshot: task.getResult().getChildren()) {
                    String titleDB = zoneSnapshot.child("title").getValue(String.class);
                    System.out.println(zoneSnapshot.child("favourite").getValue(String.class));

                    if (Objects.equals(titleDB, t)){
                        if(Objects.equals(zoneSnapshot.child("favourite").getValue(),"false")){
                            zoneSnapshot.child("favourite").getRef().setValue("true");
                            System.out.println(zoneSnapshot.child("favourite").getValue(String.class));
                            Toast.makeText(ViewActivity.this, "This exercise has been added to your favourite list",Toast.LENGTH_LONG).show();

                        } else {
                            zoneSnapshot.child("favourite").getRef().setValue("false");
                            System.out.println(zoneSnapshot.child("favourite").getValue(String.class));
                            Toast.makeText(ViewActivity.this, "This exercise has been removed from your favourite list",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }
}