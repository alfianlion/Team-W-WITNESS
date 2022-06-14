package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectWorkoutType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workout);

        Button addRunningActivity = findViewById(R.id.addRunActivity);
        Button addWorkoutActivity = findViewById(R.id.addWorkoutActivity);

        addRunningActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction f = getSupportFragmentManager().beginTransaction();
                f.replace(R.id.workoutInputContainer, new addRun());
                f.commit();
            }
        });
        addWorkoutActivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction f = getSupportFragmentManager().beginTransaction();
                f.replace(R.id.workoutInputContainer, new addWorkout());
                f.commit();
            }
        });
    }

}