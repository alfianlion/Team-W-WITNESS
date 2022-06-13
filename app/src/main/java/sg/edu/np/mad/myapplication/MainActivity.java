package sg.edu.np.mad.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

//        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction f = getSupportFragmentManager().beginTransaction();
//                f.replace(R.id.emptyFrag, new profile());
//                f.commit();
//            }
//        });
        bottomNavigationView.setOnItemSelectedListener(this);
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
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
//                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new home()).commit();
//                return true;
                return false;

            case R.id.catalog:
                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new catalogue()).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new profile()).commit();
                return true;
        }
        return false;
    }
}