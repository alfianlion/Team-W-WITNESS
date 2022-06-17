package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectExercise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise);

        Button running = findViewById(R.id.rerouteToRunningButton);
        Button workout = findViewById(R.id.rerouteToWorkoutButton);
        Button returnPage = findViewById(R.id.returnButton);

        running.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent run = new Intent(selectExercise.this, getRunning.class);
                startActivity(run);
            }
        });

        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent workout = new Intent(selectExercise.this, getWorkout.class);
                startActivity(workout);
            }
        });

        returnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}