package sg.edu.np.mad.WittnessFittness;

import java.util.ArrayList;

public class User {
    public String name,password,email;
    public ArrayList<Exercise> eList;
    public ArrayList<CalendarEvent> calList;

    public User(){}

    public User(String name,String password,String email, ArrayList<Exercise> list, ArrayList<CalendarEvent> calList){
        this.name = name;
        this.password = password;
        this.email = email;
        this.eList = list;
        this.calList = calList;
    }

}
