package sg.edu.np.mad.WittnessFittness;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class landingPage extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener
{
    BottomNavigationView bottomNavigationView;
    private int mMenuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new home()).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setOnItemSelectedListener(this);
        }

        ActivityCompat.requestPermissions(landingPage.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.home:
//                *** Enable / uncomment only for Assignment2 ***
                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new home()).commit();
                return true;

            case R.id.catalogue:
                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new catalogue()).commit();
                return true;

            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new profile()).commit();
                return true;
        }
        return false;
    }
}