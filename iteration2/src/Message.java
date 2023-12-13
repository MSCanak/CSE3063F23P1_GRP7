import java.time.LocalDateTime;

public class Message {
    private String senderID; // Person who sent the message
    private String receiverID; // Person who received the message

    private String description; // Message content
    private String subject; // Message subject

    private LocalDateTime timeSent; // Time the message was sent

    // Constructor
    public Message(String senderID, String receiverID, String description, String subject) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.description = description;
        this.subject = subject;
        this.timeSent = LocalDateTime.now();
    }

    // Getters and Setters
    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
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

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

}
