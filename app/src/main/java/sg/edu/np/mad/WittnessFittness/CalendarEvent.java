package sg.edu.np.mad.WittnessFittness;

public class CalendarEvent {
    private String id;

    //Properties (from Input)
    private String name;
    private String type;
    private String timeAllocated;
    private long dateOfEvent;


    //Constructor (default)
    public CalendarEvent() {
    }

    //Constructor
    public CalendarEvent(String name, String type, String timeAllocated, long dateOfEvent) {
        this.name = name;
        this.type = type;
        this.timeAllocated = timeAllocated;
        this.dateOfEvent = dateOfEvent;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTimeAllocated() {
        return timeAllocated;
    }

    public long getDateOfEvent() {
        return dateOfEvent;
    }
}
