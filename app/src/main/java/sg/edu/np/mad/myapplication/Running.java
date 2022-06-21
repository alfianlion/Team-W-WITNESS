package sg.edu.np.mad.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class Running extends Exercise{

    double Distance;

    //Distance
    public void setDistance(double dist){
        Distance = dist;
    }
    public double getDistance(){
        return Distance;
    }

    public Running(String title, Integer time, Date date, String id, double dist, String type){
        super(title,time,date,id,type);
        Distance = dist;
    }

}