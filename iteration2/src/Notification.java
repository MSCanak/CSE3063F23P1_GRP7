import java.time.LocalDate;
import java.time.LocalDateTime;

public class Notification {
    
    private Person receiver;
    private boolean isRead;
    private String description;
    private LocalDateTime timeSent;

    // Constructor
    public Notification(Person receiver, String description) {
        this.receiver = receiver;
        this.description = description;
        this.isRead = false;
        this.timeSent = LocalDateTime.now();
    }

    // Getters and Setters
    public Person getReceiver() {
        return receiver;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public Person setReceiver(Person receiver) {
        this.receiver = receiver;
        return receiver;
    }

    public boolean setIsRead(boolean isRead) {
        this.isRead = isRead;
        return isRead;
    }

    public String setDescription(String description) {
        this.description = description;
        return description;
    }
}
