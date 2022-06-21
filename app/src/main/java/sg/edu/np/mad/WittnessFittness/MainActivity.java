package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private TextView username, password;
    public String userId;
    SharedPreferences session;
    Context context = MainActivity.this;

    //1. List to store all Exercise instances(objects) from FB
    ArrayList<Exercise> exercisesObjList = new ArrayList<Exercise>();
    //ArrayList<Workout> woList = new ArrayList<Workout>();
    //ArrayList<Running> runList = new ArrayList<Running>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TEST
//        ReadDBRunnings();
        setContentView(R.layout.profile_login);

        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        Button registerBtn = findViewById(R.id.registerIntentBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        session = getSharedPreferences("userPreference",Context.MODE_PRIVATE);

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

                    //Getting and setting Userid from login for catalog, session and other uses
                    userId = mAuth.getCurrentUser().getUid();
                    SharedPreferences.Editor storeUserInfo = session.edit();
                    storeUserInfo.putString("userId",userId);
                    storeUserInfo.commit();

                    Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,landingPage.class));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /*
    //** Read FireBase Data to Populate catalogue
    public void ReadDBRunnings(){
        //1. Get root node of Firebase [***This needs to be determined as global variable***]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //2. Retrieve respective user ID (to locate the item in the node)
        String defaultUserIDRun  = "1115777";
        String defaultUserIDWO = "7772333";

        //3. Reference to the User's Exercises
        DatabaseReference myRefWO = database.getReference("Exercises/Workouts/"); //***userID needs to be retrieved via intent
        DatabaseReference myRefRUN = database.getReference("Exercises/Runnings/" + defaultUserIDRun);
        DatabaseReference myRef = database.getReference("Exercises/");

        //4. Check If userID exists in Db (done in authentication)
        Query checkUserWO = myRefWO.orderByChild("userID").equalTo(defaultUserIDWO); //checks if user exists in DB
        //userID matches the key(userID) in the Firebase DB //the query will query all instances the matches the given userID


        //5. Read from the database
        checkUserWO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // To ensure it does not store the previous iteration data
                exercisesObjList.clear();

                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises/Workouts/
                if(dataSnapshot.exists()){

                    //***FOR LOOP***
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){  //for all snapshot, iterate through all snapshots
                        Workout workoutDB = snapshot.getValue(Workout.class);

                        Log.e("Count: ", ""+ snapshot.getChildrenCount()+ "(number of children)");

                        exercisesObjList.add(workoutDB);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled failed", databaseError.toException());
            }
        });
    }
    */



}

