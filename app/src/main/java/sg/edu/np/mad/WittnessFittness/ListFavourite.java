package sg.edu.np.mad.WittnessFittness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ListFavourite extends AppCompatActivity {

    View view;
    ArrayList<Exercise> datalist;
    public favAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_favourite);

        if (datalist != null){
            datalist.clear();
        }

        DALExercise();

        SharedPreferences session = ListFavourite.this.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = session.getString("fav",null);
        Type type = new TypeToken<ArrayList<Workout>>(){
        }.getType();

        datalist = gson.fromJson(json, type);
        if(datalist == null){
            datalist = new ArrayList<>();
        }

        RecyclerView rv = findViewById(R.id.listRV);
        rv.setHasFixedSize(true);
        adapter = new favAdapter(datalist);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setLayoutManager(layout);
        rv.setAdapter(adapter);
    }

    public void DALExercise(){
        //1. Get root node of Firebase [**This needs to be determined as global variable**]
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        SharedPreferences session = getApplicationContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);

        //2. Retrieve respective user ID (to locate the item in the node)
        String id = session.getString("userId","");

        //2. Reference to the User's Exercises
        DatabaseReference exercisechild = FirebaseDatabase.getInstance().getReference("User/" + id + "/eList/");

        //3. Check If userID exists in Db (done in authentication)
//        DatabaseReference ExerciseId = myRef.getKey().getReference("ZONES");//checks if user exists in DB
        //userID matches the key(userID) in the Firebase DB //the query will query all instances the matches the given userID


        //4. Read from the database
        exercisechild.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                datalist.clear();
                SharedPreferences session = getApplicationContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);
                String t = session.getString("title","");
                if (task.isSuccessful()){
                    for (DataSnapshot zoneSnapshot: task.getResult().getChildren()) {
                        String fav = zoneSnapshot.child("favourite").getValue(String.class);
                        String e = zoneSnapshot.child("type").getValue(String.class);
                        System.out.println("Object: " + e);

                        if (e.equals("Running") && fav.equals("true")){
                            Exercise r = zoneSnapshot.getValue(Running.class);
                            datalist.add(r);
                            System.out.println("Run DONE");
                        } else if (e.equals("Workout")  && fav.equals("true")){
                            Exercise w = zoneSnapshot.getValue(Workout.class);
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
                    storeUserInfo.putString("fav",json);
                    storeUserInfo.commit();
                }
            }
        });
    }
}