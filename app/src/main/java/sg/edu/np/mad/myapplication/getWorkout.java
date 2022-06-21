package sg.edu.np.mad.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class getWorkout extends AppCompatActivity {

    //1. Variables (store etInput & firebase with reference)
    EditText workoutTitle, numReps, numSets, timeTaken;
    Button addWorkoutBtn;

    Date date;
    String type, id;
    Random random;
    String nameNum;
    int name;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_workout);

        //2. Hooks to all xml elements in activity_get_workout.xml
        //2.1 Hooks to all userInput
        workoutTitle = findViewById(R.id.titleInput);
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
                SharedPreferences session = getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                id = session.getString("userId","");
                type = "Workout";

                date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                String date_string = sdf.format(date);

                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User/" + id + "/eList");

                //Creating obj name
                random = new Random();
                name = random.nextInt((1000000000+1000000000) - 1000000000);
                nameNum = Integer.toString(name); //as name is a string in the Obj we must convert it

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

                /* This function > Results in error
                    //Get date
                    long millis=System.currentTimeMillis();

                    // creating a new object of the class Date
                    java.sql.Date date = new java.sql.Date(millis);
                */

                //6. Create and Store workout obj in local DB
                Exercise workoutObj = new Workout(woTitle_string,timeTaken_final,date_string,id,numReps_final,numSets_final,type);

                //Toast message to indicate successful recording
                Toast.makeText(getWorkout.this, "WORKOUT RECORDED", Toast.LENGTH_SHORT).show();

                //7. Pass in Workout Obj into Firebase with Id nesting(as identifier)
                myRef.child(nameNum).setValue(workoutObj);
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
