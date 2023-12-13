import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StudentInterface implements Schedule{

    // attributes
    private Session session;
    private NotificationsInterface notificationInterface;
    private LoginInterface loginInterface;
    private StudentCourseRegistrationInterface studentCourseRegistrationInterface;
    private Scanner input;

    // constructor
    public StudentInterface(Session session, LoginInterface loginInterface) {
        this.session = session;
        this.loginInterface = loginInterface;

    }

    public void stuMenu() {

        // prompting
        System.out.println(
                Colors.RED + "\n--------------------Student Menu--------------------\n" + Colors.RESET);
        System.out.println("What do you want to do?\n");
        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   View Transcript");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   View Curriculum");
        System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Go to Course Registration System");
        System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Logout");
        System.out.println(Colors.YELLOW + "x" + Colors.RESET + ".   Exit");

        input = new Scanner(System.in);
        char choice = input.next().charAt(0);

        switch (choice) {

            // viewing transcript
            case '1':
                ((Student) session.getUser()).getTranscript().viewTranscript();
                boolean a = true;
                while (a) {
                    System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Student Menu.");
                    char backChoice = input.next().charAt(0);
                    if (backChoice == '0') {
                        stuMenu();
                    } else {
                        System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                    }
                }

                // show courses
            case '2':
                try {
                    showCurriculum();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                } catch (ParseException e) {
                }
                break;

            // going to course registration system
            case '3':
                studentCourseRegistrationInterface = new StudentCourseRegistrationInterface(session, this);
                studentCourseRegistrationInterface.stuRegMenu();
                break;

            // logging out
            case '*':
                loginInterface.logout();
                break;

            // exiting
            case 'x':
                System.out.println(
                        Colors.RED
                                + "\n<<<--------Thank you for using Marmara Course Registration System-------->>>\n"
                                + Colors.RESET);
                loginInterface.exit();
                break;

            // invalid input
            default:
                System.out.println(Colors.YELLOW + "Invalid input.Please try again." + Colors.RESET);
                stuMenu();
                break;
        }
    }

    private void showCurriculum() throws FileNotFoundException, IOException, ParseException {
        Object coursesJSONobj;
        JSONArray courseJSONarr;
        coursesJSONobj = new JSONParser().parse(new FileReader("./jsons/courses.json"));

        courseJSONarr = (JSONArray) coursesJSONobj;

        int previousSemester = -1;
        for (Object courseObj : courseJSONarr) {
            JSONObject course = (JSONObject) courseObj;

            String CourseID = (String) course.get("CourseID");
            String CourseName = (String) course.get("CourseName");
            String CourseType = (String) course.get("CourseType");
            long Credit = (long) course.get("Credit");
            long Semester = (long) course.get("Semester");
            JSONArray OptionalPrerequisites = (JSONArray) course.get("OptionalPrerequisites");
            JSONArray MandatoryPrerequisites = (JSONArray) course.get("MandatoryPrerequisites");

            if (CourseType.equals("M")) {
                CourseType = "Mandatory";
            } else if (CourseType.equals("E")) {
                CourseType = "Elective";
            }

            String optionalPrerequisitesString = OptionalPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");
            String mandatoryPrerequisitesString = MandatoryPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");

            if (previousSemester != Semester) {
                System.out.printf(
                        "%n-----------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
                System.out.printf("%sSemester %s%s", Colors.RED, Semester, Colors.RESET);
                System.out.printf("\t%s%-10s%-45s%-15s%-8s%-35s%-35s%s%n%n", Colors.YELLOW,
                        "CourseID", "CourseName", "CourseType", "Credit",
                        "OptionalPrerequisites", "MandatoryPrerequisites", Colors.RESET);
                previousSemester = (int) Semester;
            }

            System.out.printf("\t\t%-10s%-45s%-15s%-8s%-35s%-35s%n",
                    CourseID, CourseName, CourseType, Credit,
                    optionalPrerequisitesString, mandatoryPrerequisitesString);

        }
        boolean a = true;
        while (a) {
            System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Student Menu.");
            input = new Scanner(System.in);
            char backChoice = input.next().charAt(0);
            if (backChoice == '0') {
                stuMenu();
            } else {
                System.out.println(Colors.YELLOW + "Invalid input. Please try again." + Colors.RESET);
            }
        }
    }

    private void notificationMenu(){
        System.out.println(
                Colors.RED + "\n--------------------Notification Menu--------------------\n" + Colors.RESET);
        System.out.println("What do you want to do?\n");
        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   view notifications");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   delete notifications");
        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".   go back to Student Menu");

    }
    
    public void showWeeklySchedule() {

    }

    public void showNotificatons() {

    }

    public ArrayList<Course> calculateWeeklySchedule() {
        ArrayList<Course> currentTakenCourses = ((Student) (session.getUser())).getCurrentTakenCourses();
        return currentTakenCourses;
    }

    @Override
    public void showWeeklySchedule(ArrayList<Course> courses) {
        
    }


}
