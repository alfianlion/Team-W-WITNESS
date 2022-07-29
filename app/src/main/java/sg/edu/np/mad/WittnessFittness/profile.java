package sg.edu.np.mad.WittnessFittness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    public profile() {
        // Required empty public constructor
    }

    private View view;
    private Button logout;
    private Date date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        SharedPreferences session = getActivity().getSharedPreferences("userPreference", Context.MODE_PRIVATE);

        //2. Retrieve respective user name (to locate the item in the node)
        String name = session.getString("name","");

        TextView username = view.findViewById(R.id.username);
        username.setText("Hello " + name);

        TextView dateDisplay = view.findViewById(R.id.currentDate);
        date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String date_string = sdf.format(date);
        dateDisplay.setText(date_string);

        logout = view.findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(v);
            }
        });
        return view;
    }

    private void logOut(View v){
        Intent logout = new Intent(v.getContext(),MainActivity.class);
        startActivity(logout);
    }
}