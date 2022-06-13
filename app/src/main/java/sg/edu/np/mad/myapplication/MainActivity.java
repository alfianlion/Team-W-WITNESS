package sg.edu.np.mad.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(null);
        bottomNavigationView.setSelectedItemId(R.id.catalog);

//        ***** To use the dropdown view in the Activity Start xml *****
//        Spinner dropdownMenu = findViewById(R.id.exerciseDropdown); //Define object
//        ArrayAdapter<CharSequence>spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.exerciseTypes, R.layout.activity_start);
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        dropdownMenu.setAdapter(spinnerAdapter);

    }

// ****** Fragment for bottom Navigation - Hui Xin ******
//    FirstFragment firstFragment = new FirstFragment();
//    SecondFragment secondFragment = new SecondFragment();
//    ThirdFragment thirdFragment = new ThirdFragment();
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.person:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
//                return true;
//
//            case R.id.home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
//                return true;
//
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
//                return true;
//        }
//        return false;
//    }
}