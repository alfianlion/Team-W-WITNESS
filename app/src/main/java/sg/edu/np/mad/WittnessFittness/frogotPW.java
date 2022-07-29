package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class frogotPW extends AppCompatActivity {

    private TextView emailInput;
    private Button resetButt;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frogot_pw);

        emailInput = (TextView) findViewById(R.id.resetpwEmail);
        resetButt = (Button) findViewById(R.id.resetPW);

        auth = FirebaseAuth.getInstance();

        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(v);
            }
        });
    }

    private void resetPassword(View v){
        String email = emailInput.getText().toString().trim();

        if(email.isEmpty()){
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailInput.setError("Provide a valid email");
            emailInput.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(frogotPW.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(frogotPW.this, "Try again! Error occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}