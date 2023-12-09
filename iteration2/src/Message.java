import java.time.LocalDateTime;

public class Message {
    private Person sender; // Person who sent the message
    private Person receiver; // Person who received the message

    private String description; // Message content
    private String subject; // Message subject

    private LocalDateTime timeSent; // Time the message was sent

    // Constructor
    public Message(Person sender, Person receiver, String description, String subject) {
        this.sender = sender;
        this.receiver = receiver;
        this.description = description;
        this.subject = subject;
        this.timeSent = LocalDateTime.now();
    }

    // Getters and Setters
    public Person getSender() {
        return sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getTimeSent() {
        return timeSent;
    }

    public Person setSender(Person sender) {
        this.sender = sender;
        return sender;
    }

    public Person setReceiver(Person receiver) {
        this.receiver = receiver;
        return receiver;
    }

    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public String setSubject(String subject) {
        this.subject = subject;
        return subject;
    }

}
