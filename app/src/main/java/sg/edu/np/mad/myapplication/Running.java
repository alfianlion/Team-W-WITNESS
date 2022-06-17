package sg.edu.np.mad.myapplication;

import java.util.Date;

public class Running {
    private String workOutTitle;
    private int workOutId, timeTaken;
    private double distanceTravelled;
    private Date dateDone;

    //Constructor
    public Running() {
    }

    public Running(int workOutId, String workOutTitle, double distanceTravelled, int timeTaken, Date dateDone) {
        this.workOutTitle = workOutTitle;
        this.workOutId = workOutId;
        this.timeTaken = timeTaken;
        this.distanceTravelled = distanceTravelled;
        this.dateDone = dateDone;
    }

    //Getters and Setters

    public String getWorkOutTitle() {
        return workOutTitle;
    }

    public void setWorkOutTitle(String workOutTitle) {
        this.workOutTitle = workOutTitle;
    }

    public int getWorkOutId() {
        return workOutId;
    }

    public void setWorkOutId(int workOutId) {
        this.workOutId = workOutId;
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public Date getDateDone() {
        return dateDone;
    }

    public void setDateDone(Date dateDone) {
        this.dateDone = dateDone;
    }
}