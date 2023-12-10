import java.time.LocalDateTime;

public class Session {
    // Attributes
    private Person user; // user of the session
    private LocalDateTime startingTime; // start time of the session

    // Constructors
    public Session(Lecturer lecturer) {
        this.user = lecturer;
        this.startingTime = LocalDateTime.now();
    }

    public Session(Student student) {
        this.user = student;
        this.startingTime = LocalDateTime.now();
    }

    public Session(Advisor advisor) {
        this.user = advisor;
        this.startingTime = LocalDateTime.now();
    }

    // Methods
    public LocalDateTime getStartingTime() {
        return startingTime;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }   
}
