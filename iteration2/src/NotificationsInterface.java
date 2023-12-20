import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NotificationsInterface {
    Colors Colors = new Colors();
    private ArrayList<Notification> notifications = new ArrayList<>();
    private Scanner input = new Scanner(System.in);
    private Person person;

    public NotificationsInterface(Session session) {
        this.person = session.getUser();
        notifications.clear();
        calculateNotifications();

    }

    // shows the notifications
    private void showNotifications() {
        // indexer is for showing notifications with numbers or not

        if (notifications.isEmpty()) {
            System.out.println(
                    Colors.getYELLOW() + "There are no notifications available at the moment!" + Colors.getRESET());
            System.out.println(
                    "You will be " + Colors.getGREEN() + "directed" + Colors.getRESET() + " to the Main Menu!");
            return;
        }

        int i = 1;
        String Color;

        for (Notification notification : notifications) {

            String isRead = notification.getIsRead() ? "Read" : "Unread";
            String senderID = notification.getSenderID();
            String timeSent = notification.getTimeSent().toString();
            String description = notification.getDescription();
            String timeDate;
            String time;
            String isNew;

            if (isRead.equals("Read")) {
                Color = Colors.getBLUE();
                isNew = "";
            } else {
                Color = Colors.getGREEN();
                isNew = "New";
            }

            // System.out.println("--------------------");
            // System.out.printf("%n%s",BG);

            // System.out.printf("o %-15s%n\t%-15s%n\t%30s", senderID, description,
            // timeSent);
            System.out.println(i + "  " + Color + isNew + " Notification " + Colors.getRESET() + "\t" + timeSent);
            System.out.println("\tDescription: ");
            System.out.println(Colors.getBLUE() + "\t  o  " + Colors.getRESET() + Colors.getITALIC() + description
                    + Colors.getRESET());
            System.out.print(Colors.getRESET());
            System.out.println("");

            i++;
        }
    }

    // calculates the notifications by reading json file
    private void calculateNotifications() {
        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            for (Object notification : notificationJSONObject) {
                JSONObject notificationJSON = (JSONObject) notification;
                if (notificationJSON.get("receiverID").equals(person.getID())) {

                    String receiverID = (String) notificationJSON.get("receiverID");
                    String description = (String) notificationJSON.get("description");
                    String senderID = (String) notificationJSON.get("senderID");
                    boolean isRead = (boolean) notificationJSON.get("isRead");
                    long notificationID = (long) notificationJSON.get("notificationID");

                    Notification newNotification = new Notification(receiverID, description, senderID, isRead,
                            notificationID);
                    notifications.add(newNotification);
                }
            }

        } catch (Exception e) {
            System.out.println(Colors.getYELLOW() + "Error: " + Colors.getRESET() + e);
        }
    }

    // shows the notifications menu and let user take action in notifications
    public void notificationsMenu() {

        // showNotifications();
        // if (notifications.isEmpty()) {
        // return;
        // }

        while (true) {
            System.out
                    .println(Colors.getRED() + Colors.getBOLD() + "\n>> Notifications Menu\n" + Colors.getRESET());

            // notifications
            showNotifications();
            if (notifications.isEmpty()) {
                return;
            }

            System.out.println(Colors.getYELLOW() + "1" + Colors.getRESET() + ".   Mark as Read");
            System.out.println(Colors.getYELLOW() + "2" + Colors.getRESET() + ".   Delete Notification");
            System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".   Go back to the Main Menu");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            char choice = input.next().charAt(0);
            System.out.print(Colors.getRESET());
            switch (choice) {

                // mark as read
                case '1':
                    markAsRead();
                    break;

                // delete notification
                case '2':
                    deleteNotification();
                    break;

                // go back to student menu
                case '0':
                    return;

                // invalid input
                default:
                    System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                    break;
            }
        }

    }

    private void markAsRead() {
        // notifications with numbers

        while (true) {
            System.out.println(Colors.getRED() + Colors.getBOLD() + "\n>>> Mark as Read Menu\n" + Colors.getRESET()
                    + Colors.getRESET());
            showNotifications();

            System.out.println("Select a notification " + Colors.getBLUE() + "number" + Colors.getRESET()
                    + " to mark as read or select " + Colors.getBLUE() + "x" + Colors.getRESET()
                    + " to mark all as read.");
            System.out.println(
                    "(For example -> " + Colors.getYELLOW() + "1-2-3" + Colors.getRESET() + " or" + Colors.getYELLOW()
                            + "  x" + Colors.getRESET() + " )");
            System.out.println("Which notification do you want to mark as read? \n");
            System.out
                    .println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Notifications Menu.\n");

            System.out.print(Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            char choice = input.next().charAt(0);
            System.out.print(Colors.getRESET());
            switch (choice) {

                // go back to notifications menu
                case '0':
                    return;

                // mark all as read
                case 'x':
                    for (Notification notification : notifications) {
                        notification.setIsRead(true);
                    }
                    break;

                // mark as read
                default:
                    notifications.get(choice - 49).setIsRead(true);
                    break;
            }

        }

    }

    private void deleteNotification() {
        // notifications with numbers
        while (true) {
            System.out.println(Colors.getRED() + Colors.getBOLD() + "\n>>> Delete Notification Menu\n"
                    + Colors.getRESET() + Colors.getRESET());
            showNotifications();
            if (notifications.isEmpty()) {
                return;
            }
            System.out.println("Select a notification number to delete or select x to delete all.");

            System.out.println(
                    "(For example -> " + Colors.getYELLOW() + "1-2-3" + Colors.getRESET() + " or" + Colors.getYELLOW()
                            + "  x" + Colors.getRESET() + " )");
            System.out.println("Which notification do you want to delete? \n");
            System.out
                    .println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Notifications Menu.\n");
            System.out.print(Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            char choice = input.next().charAt(0);
            System.out.print(Colors.getRESET());

            switch (choice) {

                // go back to notifications menu
                case '0':
                    return;

                // delete all
                case 'x':
                    notifications.clear();
                    break;

                // delete
                default:
                    // delete from json
                    deleteInJSON(notifications.get(choice - 49).getNotificationID());
                    notifications.remove(choice - 49);
                    break;
            }

        }
    }

    private void deleteInJSON(long notificationID) {
        try {
            Object notificationObject = new JSONParser().parse(new FileReader("./jsons/notifications.json"));
            JSONArray notificationJSONObject = (JSONArray) notificationObject;

            for (Object notification : notificationJSONObject) {
                JSONObject notificationJSON = (JSONObject) notification;
                if (notificationJSON.get("notificationID").equals(notificationID)) {
                    notificationJSONObject.remove(notification);
                    PrintWriter pw = new PrintWriter("./jsons/notifications.json");
                    pw.write(notificationJSONObject.toJSONString());

                    pw.flush();
                    pw.close();
                }
            }

        } catch (Exception e) {
            System.out.println(Colors.getYELLOW() + "Error: " + Colors.getRESET() + e);
        }
    }

}
