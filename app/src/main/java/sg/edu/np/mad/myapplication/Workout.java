package sg.edu.np.mad.myapplication;

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

    public Workout(String title, Integer time, Date date, String id, int reps, int sets, String type){
        super(title,time,date,id,type);
        NumOfSets = sets;
        NumOfReps = reps;
    }
}