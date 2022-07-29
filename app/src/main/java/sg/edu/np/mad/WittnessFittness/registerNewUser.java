package sg.edu.np.mad.WittnessFittness;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class registerNewUser extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText ETname,ETemail,ETpassword;
    private ArrayList<Exercise> eList = new ArrayList<>();
    private ArrayList<CalendarEvent> calList = new ArrayList<>();
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
        eList = new ArrayList<>();
        calList = new ArrayList<>(); //CalendarList to store CalendarEvents
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerSaveBtn:
                registerUser(ETemail.getText().toString(),ETname.getText().toString(),
                        ETpassword.getText().toString(), eList, calList);
//                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    private void registerUser(String e,String n,String p, ArrayList<Exercise> eList, ArrayList<CalendarEvent> calList){
        String email = e;
        String name = n;
        String password = p;
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String date_string = sdf.format(date);

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

                            DatabaseReference userId = FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            //Default List obj used to store Exercise Objects and CalendarEvent Objects
                            Exercise w1 = new Running("Test",1, date_string, userId.getKey(),1,"Test");
                            eList.add(w1); //Default Excercise obj, to be inserted into eList as a node

                            CalendarEvent cal1 = new CalendarEvent("Test", "Type default", "TA default",0);
                            calList.add(cal1); //Default CalEvent obj, to be inserted into calList as a node

                            //Instantiating an User obj (to be created in the FbDB)
                            User user = new User(name,password,email,eList, calList);

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
