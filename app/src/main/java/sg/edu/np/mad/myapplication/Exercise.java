package sg.edu.np.mad.myapplication;

import java.sql.Time;
import java.util.Date;

abstract public class Exercise {

    String Title;
    Integer TimeTaken;
    Date DateDone;
    Integer UserID;
    String Type;

    //Constructor
    public Exercise(String title,Integer time,Date date,Integer id,String type){
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
    public void setUserID(Integer id){
        UserID = id;
    }
    public Integer getUserID(){
        return UserID;
    }

    //Type
    public void setType(String type){
        Type = type;
    }
    public String getType(){
        return Type;
    }
}
