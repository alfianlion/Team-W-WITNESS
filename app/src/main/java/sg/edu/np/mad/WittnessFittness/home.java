package sg.edu.np.mad.WittnessFittness;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    View view;
    public home() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        Button weatherBtn = view.findViewById(R.id.toWeather);
        weatherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toWeather(v);
            }
        });
        return view;
    }

    private void toWeather(View v) {
        Intent weatherPage = new Intent(v.getContext(), weatherChecker.class);
        startActivity(weatherPage);
    }
}