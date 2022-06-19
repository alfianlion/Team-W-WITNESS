package sg.edu.np.mad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class registerNewUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText ETname,ETemail,ETpassword;
    private TextView registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_newregister);

        mAuth = FirebaseAuth.getInstance();

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
                registerUser(ETemail.getText().toString(),ETname.getText().toString(),ETpassword.getText().toString());
                break;
        }
    }

    private void registerUser(String e, String n, String p){
        String email = e;
        String name = n;
        String password = p;

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

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name,password,email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(registerNewUser.this,"User has been successfully registered",
                                                        Toast.LENGTH_LONG).show();
                                                finish();

                                                //Redirect user to login page/homepage/layout
                                            }else{
                                                Toast.makeText(registerNewUser.this, "Failed to register. Please try again",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(registerNewUser.this, "Failed to register. Please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
