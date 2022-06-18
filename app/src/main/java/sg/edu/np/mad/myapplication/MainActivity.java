package sg.edu.np.mad.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_login);

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        Button registerBtn = findViewById(R.id.registerIntentBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        registerBtn.setOnClickListener(this::onClick);
        loginBtn.setOnClickListener(this::onClick);
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.loginBtn:
                userLogin(username.getText().toString(),password.getText().toString());
                break;

            case R.id.registerIntentBtn:
                Intent registerPage = new Intent(MainActivity.this, registerNewUser.class);
                startActivity(registerPage);
                break;
        }
    }

    private void userLogin(String u, String p){
        String username_string = u;
        String password_string = p;


        if(username_string.isEmpty()){
            username.setError("Name is required");
            username.requestFocus();
            return;
        }

        if(password_string.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(username_string, password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this,landingPage.class));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

