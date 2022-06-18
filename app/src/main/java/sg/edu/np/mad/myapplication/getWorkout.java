package sg.edu.np.mad.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getWorkout extends AppCompatActivity {


    //1. Variables (store etInput & firebase with reference)
    EditText workoutTitle, numReps, numSets, timeTaken;
    Button addWorkoutBtn;

    Date date;
    String type, id;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_workout);

        //2. Hooks to all xml elements in activity_get_workout.xml
        //2.1 Hooks to all userInput
        workoutTitle = findViewById(R.id.workoutTitleInput);
        numReps = findViewById(R.id.numRepsInput);
        numSets = findViewById(R.id.numSetsInput);
        timeTaken = findViewById(R.id.timeTakenInput);

        //2.2 Hooks to Button (to listen to onClick listener)
        addWorkoutBtn = findViewById(R.id.addWorkoutInputButton);

        //3. "add" button OnClickListener
        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                //4. Initialise and get reference to Workout node
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("Exercises/Workouts/");

                //5. Retrieve user inputs
                // Convert EditText to String
                String woTitle_string = workoutTitle.getText().toString();
                String numReps_string = numReps.getText().toString();
                String numSets_string = numSets.getText().toString();
                String timeTaken_string = timeTaken.getText().toString();

                // Convert to dataType for Firebase Database
                int numReps_final = Integer.parseInt(numReps_string);
                int numSets_final = Integer.parseInt(numSets_string);
                int timeTaken_final = Integer.parseInt(timeTaken_string);

                SharedPreferences session = getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                id = session.getString("userId","");
                type = "Workout";
                date = new Date();


                /* This function > Results in error
                    //Get date
                    long millis=System.currentTimeMillis();

                    // creating a new object of the class Date
                    java.sql.Date date = new java.sql.Date(millis);
                */

                //6. Create and Store workout obj in local DB
                Workout workoutObj = new Workout(woTitle_string,timeTaken_final,date,id,numReps_final,numSets_final,type);

                //7. Pass in Workout Obj into Firebase with Id nesting(as identifier)
                myRef.child("7772333").setValue(workoutObj);
                finish();
            }
        });

        //"back" button OnClickListener
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
