package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import org.w3c.dom.Text;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView username, password,forgotPW;
    public String userId;
    SharedPreferences session;
    public ArrayList<Exercise> datalist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

        setContentView(R.layout.profile_login);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        Button registerBtn = findViewById(R.id.registerIntentBtn);
        Button loginBtn = findViewById(R.id.loginBtn);

        session = getSharedPreferences("userPreference",Context.MODE_PRIVATE);

        forgotPW = (TextView) findViewById(R.id.LoginChangePW);
        forgotPW.setOnClickListener(this::onClick);
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

            case R.id.LoginChangePW:
                Intent forgotpw = new Intent(MainActivity.this,frogotPW.class);
                startActivity(forgotpw);
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
                    ReadDBRunnings();
                    SharedPreferences.Editor storeUserInfo = session.edit();
                    storeUserInfo.putString("userId",userId);
                    storeUserInfo.commit();

                    Toast.makeText(MainActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
                    String prevStarted = "yes";
                    if (!sharedPreferences.getBoolean(prevStarted, false)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(prevStarted, Boolean.TRUE);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, askForLocation.class);
                        startActivity(intent);
                    }
                    else {
                        Intent toMain = new Intent(MainActivity.this, landingPage.class);
                        startActivity(toMain);
                    }



                } else {
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //Set a OnClickListener to execute this mtd(like when user a button to view recyclerView)
    //** Read FireBase Data to Populate catalogue
    public void ReadDBRunnings(){
        //1. Get root node of Firebase [**This needs to be determined as global variable**]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SharedPreferences session = getApplicationContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);

        //2. Retrieve respective user ID (to locate the item in the node)
        String id = session.getString("userId","");

        //3. Reference to the User's Exercises
        DatabaseReference myRef = database.getReference("User/" + id + "/eList/");
        DatabaseReference myRef2 = database.getReference("User/" + id + "/");

        //4. Check If userID exists in Db (done in authentication)
//        Query checkUserWO = myRef.child("1"); //checks if user exists in DB
        //userID matches the key(userID) in the Firebase DB //the query will query all instances the matches the given userID


        //5. Read from the database
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises/Workouts/
                if(task.isSuccessful()){

                    //**FOR LOOP**
                    for (DataSnapshot ds : task.getResult().getChildren()){  //for all snapshot, iterate through all snapshots

                        String e = ds.child("type").getValue(String.class);
                        System.out.println("Object: " + e);

                        if (e.equals("Running")){
                            Exercise r = ds.getValue(Running.class);
                            datalist.add(r);
                            System.out.println("Run DONE");
                        } else if (e.equals("Workout")){
                            Exercise w = ds.getValue(Workout.class);
                            System.out.println(w);
                            datalist.add(w);
                            System.out.println("Workout DONE");
                        } else{
                            continue;
                        }
                    }

                    Gson gson = new Gson();
                    String json = gson.toJson(datalist);
                    SharedPreferences.Editor storeUserInfo = session.edit();
                    storeUserInfo.putString("exercises",json);
                    storeUserInfo.commit();
                }
            }
        });

        myRef2.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                // To ensure it does not store the previous iteration data

                //Determines if reference(myRef) has been made successfully, so in order to read the obj the snapShot should be able to access Exercises/Workouts/
                if(task.isSuccessful()){
                    String e = task.getResult().child("name").getValue(String.class);
                    SharedPreferences.Editor storeUserInfo = session.edit();
                    storeUserInfo.putString("name",e);
                    storeUserInfo.commit();
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

