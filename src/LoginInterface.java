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

    // Methods

    // login method to get the user in
    public void login() {
        while (true){
            System.out.println("Welcome to Marmara Course Registration System.");
            System.out.println("Please enter your ID and password to login.");
            // get user login info
            String ID = getUserID();
            String password = getUserPassword();
            // check user type
            if (getUserType(ID).equalsIgnoreCase("student")) {
                // check if user exists
                if (userExists("students", ID)) {
                    if (checkUserLoginInfo("students", ID, password)) {
                        // create student object
                        person = createStudent(ID, null);
                        System.out.println("Welcome " + person.getName() + " " + person.getSurname() + "!");
                        System.out.println("You have successfully logged in.");
                        // direct student to student menu
                        System.out.println("You will be directed to the main menu.");
                        StudentInterface studentInterface = new StudentInterface((Student) person, this);
                        studentInterface.stuMenu();
                    }
                }
                else {
                    System.out.println("Invalid ID");
                    System.out.println("Please try again.");
                    continue;
                }
            } else if (getUserType(ID).equalsIgnoreCase("advisor")) {
                // check if user exists
                if (userExists("advisors", ID)) {
                    if (checkUserLoginInfo("advisors", ID, password)) {
                        // create advisor object
                        person = createAdvisor(ID, "null");
                        System.out.println("Welcome " + person.getName() + " " + person.getSurname() + "!");
                        System.out.println("You have successfully logged in.");
                        System.out.println("You will be directed to the main menu.");
                        // direct advisor to advisor menu
                        AdvisorInterface advisorInterface = new AdvisorInterface((Advisor) person, this);
                        advisorInterface.advMenu();
                    }
                } else {
                    System.out.println("Invalid ID");
                    System.out.println("Please try again.");
                    continue;
                }
            } else {
                System.out.println("Invalid ID or password!");
                System.out.println("Please try again.");
                continue;
            }
        }
        
    }

    public void logout() {
        while (true){
            // if user wants to logout and exit
            System.out.println("Do you want to logout and exit? (y/n)");
            String answer = scanner.nextLine();
            if (answer.equals("y")) {
                System.out.println("You have successfully logged out and exited.");
                System.out.println("Thank you for using Marmara Course Registration System.");
                // set person to null to recreate it when logging in again
                person = null;
                exit();
            } else if (answer.equals("n")) {
                System.out.println("You will be redirected to the login menu.");
                // set person to null to recreate it when logging in again
                person = null;
                login();
            } else {
                System.out.println("Invalid input!");
                System.out.println("Please try again.");
                continue;
            }
        }
    }

    // shuw down the program
    public void exit() {
        System.exit(0);
    }

    private String getUserID() {
        // get id
        while (true) {
            System.out.print("ID: ");
            String ID = scanner.nextLine();
            // check id format
            if (!idFormatChecker(ID)) {
                System.out.println("Invalid ID!");
                System.out.println("Please try again.");
            } else {
                return ID;
            }
        }

    }

    private String getUserPassword() {
        // get password
        while (true) {
            System.out.print("Password: ");
            String password = scanner.nextLine();
            if (!passwordFormatChecker(password)) {
                System.out.println("Invalid password!");
                System.out.println("Please try again.");
            } else {
                return password;
            }
        }
    }

    // returns true if user with the given id exists
    private boolean userExists(String fileName, String ID) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("./jsons/" + fileName + ".json");
            Object obj = parser.parse(reader);
            JSONArray userList = (JSONArray) obj;

            for (Object userObject : userList) {
                JSONObject user = (JSONObject) userObject;
                if (user.get("Id").equals(ID)) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // returns true if user with the given id and password exists
    private boolean checkUserLoginInfo(String fileName, String ID, String password) {
        while (true) {
            JSONParser parser = new JSONParser();
            try {
                FileReader reader = new FileReader("./jsons/" + fileName + ".json");
                Object obj = parser.parse(reader);
                JSONArray userList = (JSONArray) obj;

                for (Object userObject : userList) {
                    JSONObject user = (JSONObject) userObject;
                    if (user.get("Id").equals(ID) && user.get("Password").equals(password)) {
                        return true;
                    } 
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            System.out.println("Invalid ID or password!");
            System.out.println("Please try again.");
            return false;
        }

    }

    // creates student object
    private Student createStudent(String ID, Advisor advisor) {
        Object studentObj = null;
        try {
            studentObj = new JSONParser().parse(new FileReader("./jsons/student/" + ID + ".json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject student = (JSONObject) studentObj;

        // user attributes in json
        String name = (String) student.get("Name");
        String surname = (String) student.get("Surname");
        String email = (String) student.get("Mail");
        String phoneNumber = (String) student.get("PhoneNumber");
        String department = (String) student.get("Department");
        String advisorId = (String) student.get("AdvisorId");
        long semester = (long) student.get("Semester");
        String faculty = (String) student.get("Faculty");
        String studentId = (String) student.get("Id");
        String password = (String) student.get("Password");

        // if there is no advisor, create one
        if(advisor == null) {
           advisor = createAdvisor(advisorId, studentId);
        }

        Student stu = new Student(name, surname, email, phoneNumber, studentId, password, faculty, department, (int) semester, advisor);

        return stu;
    }

    // creates advisor object
    private Advisor createAdvisor(String advisorID, String studentId) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("./jsons/advisors.json");
            Object obj = parser.parse(reader);
            JSONArray advisorList = (JSONArray) obj;
    
            for (Object advisorObj : advisorList) {
                JSONObject advisor = (JSONObject) advisorObj;
                if (advisor.get("Id").equals(advisorID)) {
                    // user attributes in json
                    String name = (String) advisor.get("Name");
                    String surname = (String) advisor.get("Surname");
                    String email = (String) advisor.get("Email");
                    String phoneNumber = (String) advisor.get("PhoneNumber");
                    String faculty = (String) advisor.get("Faculty");
                    String department = (String) advisor.get("Department");
                    String password = (String) advisor.get("Password");
                    JSONArray studentList = (JSONArray) advisor.get("Students");
                    Advisor adv = new Advisor(name, surname, email, phoneNumber, faculty, department, advisorID, password);

                    for (Object studentObj : studentList) {
                        JSONObject student = (JSONObject) studentObj;
                        String studentId2 = (String) student.get("Id");
                        // if the students exists in the advisor's student list, skip it
                        if(studentId.equals(studentId2)) {
                            continue;
                        }
                        adv.setStudent(createStudent(studentId2, adv));
                    }  
                    
                    return adv;

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // return null if no advisor with the given ID is found
    }

    // returns false if id format is wrong
    private boolean idFormatChecker(String ID) {
        if (ID.isEmpty()) {
            return false;
        }
        for (int i = 0; i < ID.length(); i++) {
            if (ID.charAt(i) < '0' || ID.charAt(i) > '9') {
                return false;
            }
        }
        if (ID.length() != 9 && ID.length() != 6) {
            return false;
        }
        return true;
    }

    // returns false if password format is wrong
    private boolean passwordFormatChecker(String password) {
        if (password.isEmpty()) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        return true;
    }

    // returns user type
    private String getUserType(String ID) {
        if (ID.length() == 9) {
            return "student";
        } else if (ID.length() == 6) {
            return "advisor";
        } else {
            return null;
        }
    }
}