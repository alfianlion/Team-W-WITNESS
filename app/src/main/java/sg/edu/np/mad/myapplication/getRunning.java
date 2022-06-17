package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class getRunning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_running);


        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Button add = findViewById(R.id.addRunningInputButton);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText workoutTitle;
                EditText distanceTravelled;
                EditText timeTaken;

                int id = 0;
                String type = "Running";

                workoutTitle = (EditText) findViewById(R.id.workoutTitleInput);
                distanceTravelled = (EditText) findViewById(R.id.distanceTravelledInput);
                timeTaken = (EditText) findViewById(R.id.timeTakenInput);

                String timeTaken_string = timeTaken.getText().toString();
                String distanceTravelled_string = distanceTravelled.getText().toString();

                int distanceTravelled_final = Integer.parseInt(distanceTravelled_string);
                int timeTaken_final = Integer.parseInt(timeTaken_string);
                double distanceTravelled_double = distanceTravelled_final;
                //Get date
                long millis=System.currentTimeMillis();

                // creating a new object of the class Date
                java.sql.Date date = new java.sql.Date(millis);

                Running r = new Running(workoutTitle.getText().toString(), timeTaken_final, date,id, distanceTravelled_double, type);


                finish();
            }
        }

        );
    }
}