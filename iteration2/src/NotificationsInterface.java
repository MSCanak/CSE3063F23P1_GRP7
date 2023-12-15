import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class NotificationsInterface {
    private ArrayList<Notification> notifications = new ArrayList<>();
    private Scanner input = new Scanner(System.in);
    private Person person;

    public NotificationsInterface(Session session) {
        this.person = session.getUser();

    }

    // shows the notifications
    private void showNotifications() {
        // indexer is for showing notifications with numbers or not
        calculateNotifications();

        if (notifications.isEmpty()) {
            System.out.println(Colors.YELLOW + "There are no notifications available at the moment!\n" + Colors.RESET);
            return;
        }

        int i = 1;
        char isReadSign = 'o';
        String BG;
        String blank = " ";
        for (Notification notification : notifications) {

            String isRead = notification.getIsRead() ? "Read" : "Unread";
            String notificationType = notification.getNotificationType();
            String senderID = notification.getSenderID();
            String timeSent = notification.getTimeSent().toString();
            String description = notification.getDescription();

            if (isRead.equals("Read")) {
                BG = Colors.BLACK_BACKGROUND;
            } else {
                BG = Colors.GRAY_BACKGROUND;
            }

            System.out.println("--------------------");
            System.out.printf("%n%s %3s %-15s%10s %n %3s %-s %n %3s %-3s%-10s %s%n", BG, blank, senderID, timeSent, i,
                    notificationType, blank, isReadSign, description, Colors.RESET);
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
                    String notificationType = (String) notificationJSON.get("notificationType");
                    String senderID = (String) notificationJSON.get("senderID");

                    Notification newNotification = new Notification(receiverID, description, notificationType,
                            senderID);
                    notifications.add(newNotification);
                }
            }

        } catch (Exception e) {
            System.out.println(Colors.YELLOW + "Error: " + Colors.RESET + e);
        }
    }

    // shows the notifications menu and let user take action in notifications
    public void notificationsMenu() {

        System.out
                .println(Colors.RED + "\n--------------------Notifications Menu--------------------\n" + Colors.RESET);

        // notifications
        showNotifications();

        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   Mark as Read");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   Delete Notification");
        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".   Go back to the Main Menu");
        // we dont knw where did it go back?
        System.out.println("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?\n");

        char choice = input.next().charAt(0);

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
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                notificationsMenu();
                break;
        }

    }

    private void markAsRead() {
        // notifications with numbers

        while (true) {
            showNotifications();
            System.out.println("Which notification do you want to mark as read?");
            System.out.println("Select a notification number to mark as read or select x to mark all as read.");
            System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Notifications Menu.\n");
            System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Notifications Menu.\n");

            char choice = input.next().charAt(0);

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
                    notifications.get(choice - 1).setIsRead(true);
                    break;
            }

        }

    }

    private void deleteNotification() {
        // notifications with numbers
        while (true) {
            showNotifications();
            System.out.println("Which notification do you want to delete?");
            System.out.println("Select a notification number to delete or select x to delete all.");
            System.out.println("(For example -> " + Colors.YELLOW + "1-2-3" + Colors.RESET + " or" + Colors.YELLOW
                    + "  x" + Colors.RESET + " )");
            System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Notifications Menu.\n");
            char choice = input.next().charAt(0);

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
                    notifications.remove(choice - 1);
                    // jsondan silme işlemi eklenecek
                    break;
            }

        }
    }

}
