package sg.edu.np.mad.myapplication;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String name,password,email;
    public ArrayList<Exercise> eList;

    public User(){}

    public User(String name,String password,String email, ArrayList<Exercise> list){
        this.name = name;
        this.password = password;
        this.email = email;
        this.eList = list;
    }

//    public User(String name, String password, String email) {
//        this.name = name;
//        this.password = password;
//        this.email = email;
//        this.eList = eList;
//    }
}
