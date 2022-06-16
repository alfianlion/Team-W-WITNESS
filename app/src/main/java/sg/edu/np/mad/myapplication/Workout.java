package sg.edu.np.mad.myapplication;

import java.util.Date;

public class Workout {
    private String workOutTitle;
    private int workOutId, numOfSets, numOfReps, timeTaken;
    private Date dateDone;

    //Constructor
    public Workout() {
    }

    public Workout(int workOutId, String workOutTitle, int numOfReps, int numOfSets, int timeTaken, Date dateDone) {
        this.workOutTitle = workOutTitle;
        this.numOfSets = numOfSets;
        this.numOfReps = numOfReps;
        this.timeTaken = timeTaken;
        this.workOutId = workOutId;
        this.dateDone = dateDone;
    }

    //Getters & Setters


    public String getWorkOutTitle() {
        return workOutTitle;
    }

    public void setWorkOutTitle(String workOutTitle) {
        this.workOutTitle = workOutTitle;
    }

    public int getNumOfSets() {
        return numOfSets;
    }

    public void setNumOfSets(int numOfSets) {
        this.numOfSets = numOfSets;
    }

    public int getNumOfReps() {
        return numOfReps;
    }

    public void setNumOfReps(int numOfReps) {
        this.numOfReps = numOfReps;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getWorkOutId() {
        return workOutId;
    }

    public void setWorkOutId(int workOutId) {
        this.workOutId = workOutId;
    }

    public Date getDateDone() {
        return dateDone;
    }

    public void setDateDone(Date dateDone) {
        this.dateDone = dateDone;
    }
}

