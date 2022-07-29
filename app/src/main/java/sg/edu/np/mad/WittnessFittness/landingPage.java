package sg.edu.np.mad.WittnessFittness;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
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

        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                // No location access granted.
                            }
                        }
                );

// ...

// Before you perform the actual permission request, check whether your app
// already has the permissions, and whether your app needs to show a permission
// rationale dialog. For more details, see Request permissions.
        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.emptyFrag, new home()).commit();
            bottomNavigationView.setSelectedItemId(R.id.home);
            bottomNavigationView.setOnItemSelectedListener(this);
        }
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