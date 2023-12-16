import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

public class LoginInterface {

    // Attributes
    private Session session;
    private Scanner scanner = new Scanner(System.in);

    // Methods

    // login method to get the user in
    public void login() {
        while (true) {
            System.out.println(Colors.RED
                    + "\n--------------------Marmara Course Registration System--------------------\n" + Colors.RESET);
            System.out.println("Please enter your ID and password to login!\n");
            // get user login info
            String ID = getUserID();
            String password = getUserPassword();
            // check user type
            if (getUserType(ID).equalsIgnoreCase("student")) {
                // check if user exists
                if (userExists("students", ID)) {
                    if (checkUserLoginInfo("students", ID, password)) {
                        // create student object
                        session = new Session(createStudent(ID, null));
                        System.out.println("\nWelcome " + session.getUser().getName() + " "
                                + session.getUser().getSurname() + "!");
                        System.out.println("You have " + Colors.GREEN + "successfully" + Colors.RESET + " logged in.");
                        // direct student to student menu
                        System.out.println("You will be directed to the Student Menu!\n");
                        StudentInterface studentInterface = new StudentInterface(session, this);
                        studentInterface.stuMenu();
                    } else {
                        System.out.println(Colors.YELLOW + "Invalid ID or password! Please try again." + Colors.RESET);
                        continue;
                    }
                } else {
                    System.out.println(Colors.YELLOW + "Invalid ID! Please try again." + Colors.RESET);
                    continue;
                }
            } else if (getUserType(ID).equalsIgnoreCase("lecturer")) {
                // check if user exists
                if (userExists("lecturers", ID)) {
                    if (checkUserLoginInfo("advisors", ID, password)) {
                        // create advisor object
                        session = new Session(createAdvisor(ID, "null"));
                        System.out.println("\nWelcome " + session.getUser().getName() + " "
                                + session.getUser().getSurname() + "!");
                        System.out.println("You have " + Colors.GREEN + "successfully" + Colors.RESET + " logged in.");
                        System.out.println("You will be directed to the Advisor Menu!\n");
                        // direct advisor to advisor menu
                        AdvisorInterface advisorInterface = new AdvisorInterface(session, this);
                        advisorInterface.advMenu();
                    } else if (checkUserLoginInfo("lecturers", ID, password)) {
                        // create lecturer object
                        session = new Session(createLecturer(ID));
                        System.out.println("\nWelcome " + session.getUser().getName() + " "
                                + session.getUser().getSurname() + "!");
                        System.out.println("You have " + Colors.GREEN + "successfully" + Colors.RESET + " logged in.");
                        System.out.println("You will be directed to the Lecturer Menu!\n");
                        // direct lecturer to lecturer menu
                        AdvisorInterface advisorInterface = new AdvisorInterface(session, this);
                        advisorInterface.advMenu();
                    } else {
                        System.out.println(Colors.YELLOW + "Invalid ID or password! Please try again." + Colors.RESET);
                        continue;
                    }
                } else {
                    System.out.println(Colors.YELLOW + "Invalid ID! Please try again." + Colors.RESET);
                    continue;
                }
            } else {
                System.out.println(Colors.YELLOW + "Invalid ID or password! Please try again." + Colors.RESET);
                continue;
            }
        }

    }

    public void logout() {
        while (true) {
            // if user wants to logout and exit
            System.out.println("Do you want to logout and exit? (" + Colors.YELLOW + "y/n" + Colors.RESET + ")");
            String answer = scanner.nextLine();
            if (answer.equals("y")) {
                System.out.println(
                        "You have " + Colors.GREEN + "successfully" + Colors.RESET + " logged out and exited.");
                System.out.println(
                        Colors.RED
                                + "\n<<<--------Thank you for using Marmara Course Registration System-------->>>\n"
                                + Colors.RESET);
                // set person to null to recreate it when logging in again
                session.setUser(null);
                exit();
            } else if (answer.equals("n")) {
                System.out.println(Colors.YELLOW + "You will be redirected to the login menu." + Colors.RESET);
                // set person to null to recreate it when logging in again
                session.setUser(null);
                login();
            } else {
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
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
            System.out.print(Colors.YELLOW + "ID: " + Colors.RESET);
            String ID = scanner.nextLine();
            // check id format
            if (!idFormatChecker(ID)) {
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
            } else {
                return ID;
            }
        }

    }

    private String getUserPassword() {
        // get password
        while (true) {
            System.out.print(Colors.YELLOW + "Password: " + Colors.RESET);
            String password = scanner.nextLine();
            if (!passwordFormatChecker(password)) {
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
            } else {
                return password;
            }
        }
    }

    // returns true if user with the given id exists
    private boolean userExists(String fileName, String ID) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("jsons/" + fileName + ".json");
            Object obj = parser.parse(reader);
            JSONArray userList = (JSONArray) obj;

            for (Object userObject : userList) {
                JSONObject user = (JSONObject) userObject;
                if (user.get("ID").equals(ID)) {
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
                FileReader reader = new FileReader("jsons/" + fileName + ".json");
                Object obj = parser.parse(reader);
                JSONArray userList = (JSONArray) obj;

                for (Object userObject : userList) {
                    JSONObject user = (JSONObject) userObject;
                    if (user.get("ID").equals(ID) && user.get("Password").equals(password)) {
                        return true;
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return false;
        }

    }

    // creates student object
    private Student createStudent(String ID, Advisor advisor) {
        Object studentObj = null;
        try {
            studentObj = new JSONParser().parse(new FileReader("jsons/student/" + ID + ".json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject student = (JSONObject) studentObj;

        // user attributes in json
        String name = (String) student.get("Name");
        String surname = (String) student.get("Surname");
        String email = (String) student.get("EMail");
        String phoneNumber = (String) student.get("PhoneNumber");
        String department = (String) student.get("Department");
        String advisorID = (String) student.get("AdvisorID");
        long semester = (long) student.get("Semester");
        String faculty = (String) student.get("Faculty");
        String studentID = (String) student.get("ID");
        String password = (String) student.get("Password");
        JSONArray takenCoursesArray = (JSONArray) student.get("TakenCourses");

        // if there is no advisor, create one
        if (advisor == null) {
            advisor = createAdvisor(advisorID, studentID);
        }

        Student stu = new Student(name, surname, email, phoneNumber, studentID, password, faculty, department,
                (int) semester, advisor);

        // if student has no taken courses, skip this part
        if (takenCoursesArray.isEmpty()) {
            // do nothing
        } else {
            // Create a course object with the given course id's
            for (Object courseObj : takenCoursesArray) {
                JSONObject course = (JSONObject) courseObj;
                String courseID = (String) course.get("ID");
                // add the course to student's taken courses list
                stu.setCurrentTakenCourses(createCourse(courseID));
            }
        }

        return stu;
    }

    // creates advisor object
    private Advisor createAdvisor(String advisorID, String studentId) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("jsons/advisors.json");
            Object obj = parser.parse(reader);
            JSONArray advisorList = (JSONArray) obj;

            for (Object advisorObj : advisorList) {
                JSONObject advisor = (JSONObject) advisorObj;
                if (advisor.get("ID").equals(advisorID)) {
                    // user attributes in json
                    String name = (String) advisor.get("Name");
                    String surname = (String) advisor.get("Surname");
                    String email = (String) advisor.get("EMail");
                    String phoneNumber = (String) advisor.get("PhoneNumber");
                    String faculty = (String) advisor.get("Faculty");
                    String department = (String) advisor.get("Department");
                    String password = (String) advisor.get("Password");
                    String academicTitle = (String) advisor.get("AcademicTitle");
                    JSONArray studentList = (JSONArray) advisor.get("Students");
                    JSONArray givenCourses = (JSONArray) advisor.get("Courses");
                    Advisor adv = new Advisor(name, surname, email, phoneNumber, advisorID, password, faculty,
                            department, academicTitle);
                    // add students to advisor's student list
                    for (Object studentObj : studentList) {
                        JSONObject student = (JSONObject) studentObj;
                        String studentId2 = (String) student.get("ID");
                        // if the students exists in the advisor's student list, skip it
                        if (studentId.equals(studentId2)) {
                            continue;
                        }
                        adv.setStudent(createStudent(studentId2, adv));
                    }
                    // add courses to advisor's course list
                    for (Object courseObj : givenCourses) {
                        String courseCode = (String) courseObj;
                        adv.setCourse(createCourse(courseCode));
                    }
                    return adv;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // return null if no advisor with the given ID is found
    }

    // create lecturer object
    private Lecturer createLecturer(String lecturerID) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("jsons/lecturers.json");
            Object obj = parser.parse(reader);
            JSONArray lecturerList = (JSONArray) obj;

            for (Object lecturerObj : lecturerList) {
                JSONObject lecturer = (JSONObject) lecturerObj;
                if (lecturer.get("ID").equals(lecturerID)) {
                    // user attributes in json
                    String name = (String) lecturer.get("Name");
                    String surname = (String) lecturer.get("Surname");
                    String email = (String) lecturer.get("EMail");
                    String phoneNumber = (String) lecturer.get("PhoneNumber");
                    String faculty = (String) lecturer.get("Faculty");
                    String department = (String) lecturer.get("Department");
                    String password = (String) lecturer.get("Password");
                    JSONArray givenCourses = (JSONArray) lecturer.get("Courses");
                    String academicTitle = (String) lecturer.get("AcademicTitle");
                    Lecturer lec = new Lecturer(name, surname, email, phoneNumber, lecturerID, password, faculty,
                            department, academicTitle);

                    // add courses to lecturer's course list
                    for (Object courseObj : givenCourses) {
                        String courseCode = (String) courseObj;

                        lec.setCourse(createCourse(courseCode));
                    }

                    return lec;

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // return null if no lecturer with the given ID is found
    }

    // create course object
    private Course createCourse(String courseCode) {
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader("jsons/CoursesOffered.json");
            Object obj = parser.parse(reader);
            JSONArray coursesList = (JSONArray) obj;

            for (Object courseObj : coursesList) {
                JSONObject course = (JSONObject) courseObj;
                if (course.get("CourseID").equals(courseCode)) {
                    // course attributes in json
                    String courseName = (String) course.get("CourseName");
                    String courseDayTimeLocation = (String) course.get("CourseDayTimeLocation");
                    CourseSession courseSession = createCourseSession(courseDayTimeLocation);
                    // decide whether it is lecture or lab
                    int dotCount = 0;
                    for (int i = 0; i < courseCode.length(); i++) {
                        if (courseCode.charAt(i) == '.') {
                            dotCount++;
                        }
                    }
                    if (dotCount == 1) {
                        // create lecture
                        Lecture lec = new Lecture(courseName, courseCode, courseSession);
                        return lec;
                    } else if (dotCount == 2) {
                        // create lab
                        Lab lab = new Lab(courseName, courseCode, courseSession);
                        return lab;
                    } else if (dotCount == 0) {
                        // create lab
                        Course cou = new Course(courseName, courseCode, courseSession);
                        return cou;
                    } else {
                        System.out.println(Colors.CYAN + "Invalid course code!" + Colors.RESET);
                        continue;
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null; // return null if no course with the given code is found
    }

    // create course session object
    private CourseSession createCourseSession(String courseDayTimeLocation) {
        // parse the string and create course session object
        String[] parts = courseDayTimeLocation.split(" ");
        ArrayList<String> courseDay = new ArrayList<>();
        ArrayList<String> courseStartTime = new ArrayList<>();
        ArrayList<String> courseEndTime = new ArrayList<>();
        ArrayList<String> coursePlace = new ArrayList<>();
        // each object of the list is a different course session
        for (int i = 0; i < parts.length; i += 5) {
            courseDay.add(parts[i]);
            courseStartTime.add(parts[i + 1]);
            courseEndTime.add(parts[i + 3]);
            coursePlace.add(parts[i + 4]);
        }

        return new CourseSession(courseDay, courseStartTime, courseEndTime, coursePlace);
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
            return "lecturer";
        } else {
            return null;
        }
    }
}