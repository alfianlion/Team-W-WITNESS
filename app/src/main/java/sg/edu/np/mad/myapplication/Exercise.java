package sg.edu.np.mad.myapplication;

import java.sql.Time;
import java.util.Date;

abstract public class Exercise {

    String Title;
    Integer TimeTaken;
    Date DateDone;
    String UserID;
    String Type;


    //Constructor
    public Exercise(){
    }

    public Exercise(String title,Integer time,Date date,String id,String type){
        Title = title;
        TimeTaken = time;
        DateDone = date;
        UserID = id;
        Type = type;
    }

    //Title
    public void setTitle(String title){
        Title = title;
    }
    public String getTitle(){
        return Title;
    }

    //TimeTaken
    public void setTimeTaken(Integer time) {
        TimeTaken = time;
    }
    public Integer getTimeTaken(){
        return TimeTaken;
    }

    //DateDone
    public void setDateDone(Date date){
        DateDone = date;
    }
    public Date getDateDone(){
        return DateDone;
    }

    //UserID
    public void setUserID(String id){
        UserID = id;
    }
    public String getUserID(){
        return UserID;
    }

    //Type
    public void setType(String type){
        Type = type;
    }
    public String getType(){
        return Type;
    }

    //ToString (for testing)

    @Override
    public String toString() {
        return "Exercise{" +
                "Title='" + Title + '\'' +
                ", TimeTaken=" + TimeTaken +
                ", DateDone=" + DateDone +
                ", UserID='" + UserID + '\'' +
                ", Type='" + Type + '\'' +
                '}';
    }
}
