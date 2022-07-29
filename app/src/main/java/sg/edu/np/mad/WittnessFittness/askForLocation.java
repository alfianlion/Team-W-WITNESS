package sg.edu.np.mad.WittnessFittness;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class askForLocation extends AppCompatActivity {
    boolean permissionAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask_for_location);
        Button agree = findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(askForLocation.this, landingPage.class);
            startActivity(toMain);
//                ActivityCompat.requestPermissions(askForLocation.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
//                if (ContextCompat.checkSelfPermission(askForLocation.this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
//                        ContextCompat.checkSelfPermission(askForLocation.this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED );
//                {
//                    permissionAccept = true;
//                }
            }
        });
//
//        if (permissionAccept = true){
//            Intent toMain = new Intent(askForLocation.this, landingPage.class);
//            startActivity(toMain);
//        }

//        Button decline = findViewById(R.id.decline);
//        decline.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toMain = new Intent(askForLocation.this, landingPage.class);
//                startActivity(toMain);
//            }
//        });
    };
}
