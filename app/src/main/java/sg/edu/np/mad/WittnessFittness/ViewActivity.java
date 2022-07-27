package sg.edu.np.mad.WittnessFittness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity_details);

        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra("exerciseTitle");
        String timetaken = getIntent.getStringExtra("exerciseTimeTaken");
        String type = getIntent.getStringExtra("exerciseType");

        TextView titletxt = findViewById(R.id.viewWorkoutTitle);
        TextView timetakentxt = findViewById(R.id.timeTaken);
        TextView workouttypetxt = findViewById(R.id.workoutType);

        titletxt.setText(title);
        timetakentxt.setText(timetaken + "minutes");
        workouttypetxt.setText(type);
    }
}