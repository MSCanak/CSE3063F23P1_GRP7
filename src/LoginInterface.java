import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

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
            // create user object
            userConstructor();
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
        // check id format
        if (!idFormatChecker()) {
            System.out.println("Invalid ID!");
            System.out.println("Please try again.");
            login();
        }
        // get user type and password
        if (getUserType().equalsIgnoreCase("student") || getUserType().equalsIgnoreCase("advisor")) {
            // get password
            System.out.print("Password: ");
            password = scanner.nextLine();
        }
        else {
            System.out.println("Invalid ID!");
            System.out.println("Please try again.");
            login();    
        }
    }

    private boolean userExists() {
    JSONParser parser = new JSONParser();
    try {
        FileReader reader = new FileReader("students.json");
        Object obj = parser.parse(reader);
        JSONArray studentList = (JSONArray) obj;

        for (Object studentObj : studentList) {
            JSONObject student = (JSONObject) studentObj;
            if (student.get("Id").equals(ID) && student.get("Password").equals(password)) {
                return true;
            }
        }
    } catch (IOException | ParseException e) {
        e.printStackTrace();
    }
    return false;
}
    // creates user object
    private void userConstructor() {
        if(getUserType().equalsIgnoreCase("student")){
            // create student object
        }
        else if (getUserType().equalsIgnoreCase("advisor")) {
            // create advisor object
        }
        else {
            System.out.println("Invalid ID!");
            System.out.println("Please try again.");
            login();
        }
    }
    // returns false if id format is wrong
    private boolean idFormatChecker() {
        if (ID.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ID.length(); i++) {
            if (ID.charAt(i) < '0' || ID.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }
    // returns user type
    private String getUserType() {
        if (ID.length() == 9) {
            return "student";
        }
        else if (ID.length() == 6) {
            return "advisor";
        }
        else {
            return null;
        }
    }
}