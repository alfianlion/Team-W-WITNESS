package sg.edu.np.mad.WittnessFittness;

abstract public class Exercise {

    String Title;
    Integer TimeTaken;
    String DateDone;
    String UserID;
    String Type;

    public Exercise(){}

    //Constructor
    public Exercise(String title,Integer time,String date,String id,String type){
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
    public void setDateDone(String date){
        DateDone = date;
    }
    public String getDateDone(){
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

}
