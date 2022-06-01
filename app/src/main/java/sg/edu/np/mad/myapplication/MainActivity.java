package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

//        //To use the dropdown view in the Activity Start xml
//        Spinner dropdownMenu = findViewById(R.id.exerciseDropdown); //Define object
//        ArrayAdapter<CharSequence>spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.exerciseTypes, R.layout.activity_start);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        dropdownMenu.setAdapter(spinnerAdapter);
    }
}