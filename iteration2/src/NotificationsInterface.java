import java.util.ArrayList;
import java.util.Scanner;

public class NotificationsInterface {
    // shows the notifications
    private void showNotifications(){

    }
    // calculates the notifications by reading json file
    private void calculateNotifications(){
        
    }
    // shows the notifications menu and let user take action in notifications
    public void notificationsMenu(){
        
        System.out.println(Colors.RED + "\n--------------------Notifications Menu--------------------\n" + Colors.RESET);
        
        //notifications
        showNotifications();

        
        System.out.println("\nWhat do you want to do?\n");
        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   Mark as Read");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   Delete Notification");
        System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Go back to the Student Menu");

        Scanner input = new Scanner(System.in);
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
            case '*':
                return;

            // invalid input
            default:
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                notificationsMenu();
                break;
        }

        
    }
    private void markAsRead(){
          //notifications with numbers
                
                for(int i = 0; i < 10; i++){
                    System.out.println(i + ". Notification " + i);
                }
                System.out.println("Which notification do you want to mark as read?");
                System.out.println("Select a notification number to mark as read or select x to mark all as read.");
                System.out.println("(For example -> 1-2-3 or  x )");
                System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Notifications Menu.");

    }
    private void deleteNotification(){
            //notifications with numbers

                System.out.println("Which notification do you want to delete?");
                System.out.println("Select a notification number to delete or select x to delete all.");
                System.out.println("(For example -> 1-2-3 or  x )");
                System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Notifications Menu.");
    }

}   
