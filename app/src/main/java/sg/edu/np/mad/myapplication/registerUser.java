package sg.edu.np.mad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class registerUser extends AppCompatActivity implements View.OnClickListener{

    private EditText ETname,ETemail,ETpassword;
    private TextView registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_register);

        registerbtn = (TextView) findViewById(R.id.registerSaveBtn);
        registerbtn.setOnClickListener(this);

        ETname = (EditText) findViewById(R.id.usernameInput);
        ETpassword = (EditText) findViewById(R.id.passwordInput);
        ETemail = (EditText) findViewById(R.id.emailInput);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerSaveBtn:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    private void registerUser(){
        String email = ETemail.getText().toString().trim();
        String name = ETname.getText().toString().trim();
        String password = ETpassword.getText().toString().trim();

        if(name.isEmpty()){
            ETname.setError("Name is required");
            ETname.requestFocus();
            return;
        }

        if(email.isEmpty()){
            ETemail.setError("Email is required");
            ETemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            ETpassword.setError("Password is required");
            ETpassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETemail.setError("Please provide valid email");
            ETemail.requestFocus();
            return;
        }
    }
}
