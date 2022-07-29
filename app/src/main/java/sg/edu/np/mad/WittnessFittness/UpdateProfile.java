package sg.edu.np.mad.WittnessFittness;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {
    private TextView nameInput,resetButt;
    DatabaseReference reference;
    private Button backToprofile;
    private View view;
    String UID,Email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        reference = FirebaseDatabase.getInstance().getReference("User");
        mAuth = FirebaseAuth.getInstance();

        nameInput =  findViewById(R.id.nameUpdate);
        resetButt = findViewById(R.id.PUChangePass);

        Intent receive = getIntent();
        UID = receive.getStringExtra("UID");
        Email = mAuth.getCurrentUser().getEmail().toString();

        resetButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(v);
            }
        });
    }

    public void ToProfile(View v){
        Intent backtoProfile = new Intent(v.getContext(),landingPage.class);
        startActivity(backtoProfile);
    }

    public void update(View view){
        if (isNamechanged()){
            Toast.makeText(this, "Profile has been updated", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Nothing has been updated", Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNamechanged() {

        if(nameInput.getText().toString() != null){
            reference.child(UID).child("name").setValue(nameInput.getText().toString());
            return true;
        }
        else{
            return false;
        }
    }//Dont touch

    private void resetPassword(View v){
        mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateProfile.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(UpdateProfile.this, "Try again! Error occured", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}