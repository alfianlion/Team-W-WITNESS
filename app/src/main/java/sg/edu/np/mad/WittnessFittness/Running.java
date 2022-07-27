package sg.edu.np.mad.WittnessFittness;

public class Running extends Exercise{

    double Distance;

    //Distance
    public void setDistance(double dist){
        Distance = dist;
    }
    public double getDistance(){
        return Distance;
    }

    public Running(String title, Integer time, String date, String id, double dist, String type){
        super(title,time,date,id,type);
        Distance = dist;
    }

    public Running() {
        super();
    }

    @Override
    public String toString() {
        return "Running{" +
                "Title='" + Title + '\'' +
                ", TimeTaken=" + TimeTaken +
                ", DateDone=" + DateDone +
                ", UserID='" + UserID + '\'' +
                ", Type='" + Type + '\'' +
                ", Distance=" + Distance +
                '}';
    }
}