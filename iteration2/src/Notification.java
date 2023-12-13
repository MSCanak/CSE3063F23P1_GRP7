import java.io.FileReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class Notification {
    
    private String receiverID;
    private boolean isRead;
    private String description;
    private LocalDateTime timeSent;
    private String notificationType;
    private String senderID;

    // Constructor
    public Notification(String receiverID, String description) {
        this.receiverID = receiverID;
        this.description = description;
        this.isRead = false;
        this.timeSent = LocalDateTime.now();
    }

    public Notification(String receiverID, boolean isRead, String description, String timeSent, String notificationType, String senderID) {
        this.receiverID = receiverID;
        this.isRead = isRead;
        this.description = description;
        this.timeSent = LocalDateTime.parse(timeSent);
        this.notificationType = notificationType;
        this.senderID = senderID;
    }

    public boolean sendNotification(String notificationType, String senderID) {
        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            JSONObject newNotification = new JSONObject();
            newNotification.put("receiverID", receiverID);
            newNotification.put("isRead", isRead);
            newNotification.put("description", description);
            newNotification.put("timeSent", timeSent.toString());
            newNotification.put("notificationType", notificationType);
            newNotification.put("senderID", senderID);

            notificationJSONObject.add(newNotification);

            PrintWriter pw = new PrintWriter("./jsons/notifications.json");
            pw.write(notificationJSONObject.toJSONString()); 
          
            pw.flush(); 
            pw.close();
            return true;

        }
        catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    // Getters and Setters
    public String getReceiver() {
        return receiverID;
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

    public String getNotificationType() {
        return notificationType;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;

        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            for(Object notification : notificationJSONObject) {
                JSONObject notificationJSON = (JSONObject) notification;
                if(notificationJSON.get("receiverID").equals(receiverID) && notificationJSON.get("description").equals(description)) {
                    notificationJSON.put("isRead", isRead);
                    PrintWriter pw = new PrintWriter("./jsons/notifications.json");
                    pw.write(notificationJSONObject.toJSONString()); 
                
                    pw.flush(); 
                    pw.close();
                }
            }
            
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
