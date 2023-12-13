import java.time.LocalDateTime;

public class Session {
    // Attributes
    private Person user; // user of the session
    private LocalDateTime startingTime; // start time of the session

    // Constructors
    public Session(Person user) {
        this.user = user;
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
    
    public void setStartingTime(LocalDateTime startingTime) {
        this.startingTime = startingTime;
    }
}
