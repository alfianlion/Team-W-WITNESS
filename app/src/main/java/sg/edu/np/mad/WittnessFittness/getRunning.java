package sg.edu.np.mad.WittnessFittness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

import sg.edu.np.mad.WittnessFittness.R;

public class getRunning extends AppCompatActivity {

    EditText workoutTitle, distanceTravelled, timeTaken;
    Button addRunningBtn;
    Date date;
    String type, id;
    Random random;
    String nameNum;
    Integer name;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_running);

        //2. Hooks to all xml elements in activity_get_running.xml
        //2.1 Hooks to all userInput
        workoutTitle = findViewById(R.id.titleInput);
        distanceTravelled = findViewById(R.id.distanceTravelledInput);
        timeTaken = findViewById(R.id.timeTakenInput);

        //2.2 Hooks to Button (to listen to onClick listener)
        addRunningBtn = findViewById(R.id.addRunningInputButton);

        //3. "add" button OnClickListener
        addRunningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // DATABASE
                //Default user input <needs to be edit-ed to assign real Id and real date-time>
                SharedPreferences session = getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                id = session.getString("userId","");
                type = "Running";

                date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                String date_string = sdf.format(date);

                //Creating obj name
                random = new Random();
                name = random.nextInt((1000000000+1000000000) - 1000000000);
                nameNum = Integer.toString(name); //as name is a string in the Obj we must convert it

                //4. Initialise and get reference to Running node
                database = FirebaseDatabase.getInstance();
                myRef = database.getReference("User/" + id + "/eList");

                // Convert EditText to String
                String woTitle_string = workoutTitle.getText().toString();
                String distanceTravelled_string = distanceTravelled.getText().toString();
                String timeTaken_string = timeTaken.getText().toString();

                // Convert to dataType for Firebase Database
                Double distanceTravelled_final = Double.parseDouble(distanceTravelled_string);
                int timeTaken_final = Integer.parseInt(timeTaken_string);
                double distanceTravelled_double = (double) distanceTravelled_final;

                //6. Create and Store running obj in local DB
                Running runningObj = new Running(woTitle_string, timeTaken_final, date_string, id, distanceTravelled_double, type);

                //Toast message to indicate successful recording
                Toast.makeText(getRunning.this, "RUN RECORDED", Toast.LENGTH_SHORT).show();

                //7. Pass in Running Obj into Firebase with Id nesting(as identifier)
                myRef.child(nameNum).setValue(runningObj);
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
