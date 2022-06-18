package sg.edu.np.mad.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class landingPage extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener

{
    BottomNavigationView bottomNavigationView;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.catalog);

//        register = (TextView) findViewById(R.id.registerIntentBtn);
//        register.setOnClickListener((View.OnClickListener) this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
//                *** Enable / uncomment only for Assignment2 ***
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