import java.util.Scanner;

public class LoginInterface {
    
    // Attributes
    private Person person;
    private Scanner scanner = new Scanner(System.in);
    private String ID;
    private String password;

    // Methods

    // login method to get the user in
    public void login() {
        System.out.println("Welcome to Marmara Course Registration System!");
        System.out.println("Please enter your ID and password to login.");
        // get user login info
        getLoginInf();
        if(userExists()) {
            System.out.println("You have successfully logged in.");

        }
        else {
            System.out.println("Invalid ID or password!");
            System.out.println("Please try again.");
            login();
        }
    }

    public void logout() {
        System.out.println("Do you want to logout and exit? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equals("y")) {
            System.out.println("You have successfully logged out and exited.");
            System.out.println("Thank you for using Marmara Course Registration System.");
            person = null;
            exit();
        }
        else if (answer.equals("n")) {
            System.out.println("You will be redirected to the login menu.");
            person = null;
            login();
        }
        else {
            System.out.println("Invalid input!");
            System.out.println("Please try again.");
            logout();
        }
        

    }

    public void exit() {
        System.exit(0);
    }

    private void getLoginInf() {
        // get id
        System.out.print("ID: ");
        ID = scanner.nextLine();
        // if id is 9 digits, it is a student
        if (ID.length() == 9) {
            System.out.println("Password: ");
            password = scanner.nextLine();
        }
        // if id is 6 digits, it is an advisor
        else if (ID.length() == 6) {
            System.out.println("Password: ");
            password = scanner.nextLine();
        }
        // else error
        else {
            System.out.println("Invalid ID!");
            System.out.println("Please try again.");
            login();
        }
    }

    private boolean userExists() {
        if (ID.length() == 9) {
            
        }
        else if (ID.length() == 6) {
            
        }
        return false;
    }

    private void userConstructor() {
        if (ID.length() == 9) {
        }
        else if (ID.length() == 6) {
        }
    }
}