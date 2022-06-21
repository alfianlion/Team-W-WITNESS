package sg.edu.np.mad.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class Workout extends Exercise{

    int NumOfSets;
    int NumOfReps;


    //Reps
    public void setNumOfReps(int numOfReps) {
        NumOfReps = numOfReps;
    }
    public int getNumOfReps(){
        return NumOfReps;
    }

    //Sets
    public void setNumOfSets(int numOfSets){
        NumOfSets = numOfSets;
    }
    public int getNumOfSets(){
        return NumOfSets;
    }

    //Constructor

    public Workout(){super();}

    public Workout(String title, Integer time, String date, String id, int reps, int sets, String type){
        super(title,time,date,id,type);
        NumOfSets = sets;
        NumOfReps = reps;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "Title='" + Title + '\'' +
                ", TimeTaken=" + TimeTaken +
                ", DateDone=" + DateDone +
                ", UserID='" + UserID + '\'' +
                ", Type='" + Type + '\'' +
                ", NumOfSets=" + NumOfSets +
                ", NumOfReps=" + NumOfReps +
                '}';
    }
}