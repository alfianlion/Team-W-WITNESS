package sg.edu.np.mad.WittnessFittness;

public class dataUser {
    private String id;

    //Properties (from Input)
    private String name;
    private String gender;
    private String major;
    private long dateOfRegistration;

    //Constructor (default)
    public dataUser() {
    }

    //Constructor
    public dataUser(String name, String gender, String major, long dateOfRegistration) {
        this.name = name;
        this.gender = gender;
        this.major = major;
        this.dateOfRegistration = dateOfRegistration;
    }

    //Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getMajor() {
        return major;
    }

    public long getDateOfRegistration() { return dateOfRegistration; }

}
