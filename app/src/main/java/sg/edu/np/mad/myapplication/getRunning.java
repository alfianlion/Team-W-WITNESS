package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class getRunning extends AppCompatActivity {

    //1. Variables (store etInput & firebase with reference)
    EditText workoutTitle, distanceTravelled, timeTaken;
    Button addRunningBtn;

    Date date;
    String type, id;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_running);

        //2. Hooks to all xml elements in activity_get_running.xml
        //2.1 Hooks to all userInput
        workoutTitle = findViewById(R.id.workoutTitleInput);
        distanceTravelled = findViewById(R.id.distanceTravelledInput);
        timeTaken = findViewById(R.id.timeTakenInput);

        //2.2 Hooks to Button (to listen to onClick listener)
        addRunningBtn = findViewById(R.id.addRunningInputButton);

        //3. "add" button OnClickListener
        addRunningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //4. Initialise and get reference to Running node
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("Exercises/Runnings/");

                //5. Retrieve user inputs
                // Convert EditText to String
                String woTitle_string = workoutTitle.getText().toString();
                String distanceTravelled_string = distanceTravelled.getText().toString();
                String timeTaken_string = timeTaken.getText().toString();

                // Convert to dataType for Firebase Database
                int distanceTravelled_final = Integer.parseInt(distanceTravelled_string);
                int timeTaken_final = Integer.parseInt(timeTaken_string);
                double distanceTravelled_double = (double) distanceTravelled_final;

                //Default user input <needs to be edit-ed to assign real Id and real date-time>
                SharedPreferences session = getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                id = session.getString("userId","");
                type = "Running";
                date = new Date();


                /* This function > Results in error
                    //Get date
                    long millis=System.currentTimeMillis();

                    // creating a new object of the class Date
                    java.sql.Date date = new java.sql.Date(millis);
                */

                //6. Create and Store running obj in local DB
                Running runningObj = new Running(woTitle_string, timeTaken_final, date, id, distanceTravelled_double, type);

                //7. Pass in Running Obj into Firebase with Id nesting(as identifier)
                myRef.child("1115777").setValue(runningObj);
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
