package sg.edu.np.mad.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FirebaseAuth mAuth;
    EditText username,password;
    View view;
    Button register, login;
    ValueEventListener help;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        username = (EditText) view.findViewById(R.id.usernameInput);
        password = (EditText) view.findViewById(R.id.passwordInput);


        login = (Button) view.findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(username.getText().toString(),password.getText().toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.profile_login, container, false);
        view = inflater.inflate(R.layout.profile_login, container, false);
        username = (EditText) view.findViewById(R.id.usernameInput);
        password = (EditText) view.findViewById(R.id.passwordInput);

        login = (Button) view.findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin(username.getText().toString(),password.getText().toString());
            }
        });

        register = (Button) view.findViewById(R.id.registerIntentBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerPage = new Intent(getActivity(), registerNewUser.class);
                startActivity(registerPage);
            }
        });
        return view;
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
                    Toast.makeText(getContext(), "Login Successful",Toast.LENGTH_LONG).show();
                    Log.i("Login","yourmom");
                    startActivity(new Intent(getActivity(),MainActivity.class));
                } else {
                    Toast.makeText(getContext(), "Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}