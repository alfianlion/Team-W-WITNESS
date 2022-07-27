package sg.edu.np.mad.WittnessFittness;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class catalogue extends Fragment implements catalogueAdapter.ItemClickListener{

        View view;
        private Date date;
        MainActivity mainActivity;
        ArrayList<Exercise> datalist;

        public catalogue() {
            // Required empty public constructor
        }

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            SharedPreferences session = requireContext().getSharedPreferences("userPreference", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = session.getString("exercises",null);
            Type type = new TypeToken<ArrayList<Workout>>(){
            }.getType();

            datalist = gson.fromJson(json, type);

            if(datalist == null){
                datalist = new ArrayList<>();
            }
            System.out.println(datalist.toString());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_catalogue, container, false);

            date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            String date_string = sdf.format(date);

            TextView dateDisplay = view.findViewById(R.id.dateDisplay);
            dateDisplay.setText(date_string);

            RecyclerView rv = view.findViewById(R.id.workoutRv);
            rv.setHasFixedSize(true);
            catalogueAdapter adapter = new catalogueAdapter(datalist);
            LinearLayoutManager layout = new LinearLayoutManager(getContext());

            rv.setLayoutManager(layout);
            rv.setAdapter(adapter);

            Button addActivity = view.findViewById(R.id.addActivity);
            addActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addActivityView = new Intent(getActivity(), selectExercise.class );
                    startActivity((addActivityView));
                    ((Activity) getActivity()).overridePendingTransition(0,0);
                }
            });

            return view;
    }

    @Override
    public void onItemClick(int position) {

    }
}