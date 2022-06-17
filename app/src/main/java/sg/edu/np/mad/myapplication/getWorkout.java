package sg.edu.np.mad.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getWorkout extends AppCompatActivity {

    Date dateObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_workout);
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button add = findViewById(R.id.addWorkoutInputButton);
        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                //Defines Variables
                EditText workoutTitle;
                EditText numReps;
                EditText numSets;
                EditText timeTaken;

                int id = 0;
                String type = "Workout";

                // Access User input
                workoutTitle = (EditText) findViewById(R.id.workoutTitleInput);

                numReps = (EditText) findViewById(R.id.numRepsInput);
                numSets = (EditText) findViewById(R.id.numSetsInput);
                timeTaken = (EditText) findViewById(R.id.timeTakenInput);

                // Convert EditText to String
                String numReps_string = numReps.getText().toString();
                String numSets_string = numSets.getText().toString();
                String timeTaken_string = timeTaken.getText().toString();

                // Convert to dataType for Database
                int numReps_final = Integer.parseInt(numReps_string);
                int numSets_final = Integer.parseInt(numSets_string);
                int timeTaken_final = Integer.parseInt(timeTaken_string);

                //Get date
                long millis=System.currentTimeMillis();

                // creating a new object of the class Date
                java.sql.Date date = new java.sql.Date(millis);

                Workout w = new Workout(workoutTitle.toString(),timeTaken_final,date,id,numReps_final,numSets_final,type);
                finish();
            }
        });
    }
}