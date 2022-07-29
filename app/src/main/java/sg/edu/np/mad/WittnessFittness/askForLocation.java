package sg.edu.np.mad.WittnessFittness;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class askForLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_location);
        Button agree = findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }

            Intent toMain = new Intent(askForLocation.this, landingPage.class);

        });
        Button decline = findViewById(R.id.decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(askForLocation.this, landingPage.class);
            }
        });
    }
}