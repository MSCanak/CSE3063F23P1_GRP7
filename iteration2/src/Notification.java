import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class Notification {

    private String receiverID;
    private boolean isRead;
    private String description;
    private LocalDateTime timeSent;
    private String senderID;
    private long notificationID; 

    // Constructor
    public Notification(String receiverID, String description, String senderID, boolean isRead, long notificationID) {
        this.receiverID = receiverID;
        this.description = description;
        this.senderID = senderID;
        this.isRead = isRead;
        this.timeSent = LocalDateTime.now();
        this.notificationID = notificationID;
    }

    public Notification(String receiverID, String description, String senderID) {
        this.receiverID = receiverID;
        this.description = description;
        this.senderID = senderID;
        this.isRead = false;
        this.timeSent = LocalDateTime.now();
        this.notificationID = notificationIdentifier();
    }

    private long notificationIdentifier() {
        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            return notificationJSONObject.size() + 1;

        } catch (Exception e) {
            System.out.println("Error: " + e);
            return 0;
        }
    }

    public boolean sendNotification(String senderID) {
        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            JSONObject newNotification = new JSONObject();
            newNotification.put("receiverID", receiverID);
            newNotification.put("isRead", isRead);
            newNotification.put("description", description);
            newNotification.put("timeSent", timeSent.toString());
            newNotification.put("senderID", senderID);
            newNotification.put("notificationID", notificationID);

            notificationJSONObject.add(newNotification);

            PrintWriter pw = new PrintWriter("./jsons/notifications.json");
            pw.write(notificationJSONObject.toJSONString());

            pw.flush();
            pw.close();
            return true;

        } catch (Exception e) {
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

    public String getSenderID() {
        return senderID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public long getNotificationID() {
        return notificationID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;

        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            for (Object notification : notificationJSONObject) {
                JSONObject notificationJSON = (JSONObject) notification;
                if (notificationJSON.get("notificationID").equals(notificationID)) {
                    notificationJSON.put("isRead", isRead);
                    PrintWriter pw = new PrintWriter("./jsons/notifications.json");
                    pw.write(notificationJSONObject.toJSONString());

                    pw.flush();
                    pw.close();
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNotificationID(long notificationID) {
        this.notificationID = notificationID;
    }
}
