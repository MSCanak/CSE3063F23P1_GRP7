import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class AdvisorInterface implements Schedule {

    private Scanner scanner;
    private Session session;
    private NotificationsInterface notificationsInt;
    private LoginInterface loginInt;
    private AdvisorCourseRegistrationInterface advCourseRegInt;

    public AdvisorInterface(Session session, LoginInterface loginInt) {
        this.session = session;
        this.loginInt = loginInt;
        advCourseRegInt = new AdvisorCourseRegistrationInterface(session, this);
    }

    // the terminal inteface for advisor
    public void advMenu() {

        scanner = new Scanner(System.in);
        System.out.println(Colors.RED + "\n--------------------Advisor Menu--------------------\n" + Colors.RESET);
        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   View Weekly Schedule");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   View Given Courses");
        System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Go to Course Registration System");
        System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Logout");
        System.out.println(Colors.YELLOW + "x" + Colors.RESET + ".   Exit");
        System.out.println("\nWhat do you want to do?\n");

        char caseToken = scanner.next().charAt(0);

        switch (caseToken) {
            case '1': // view weekly schedule
                showWeeklySchedule(calculateWeeklySchedule());
                break;
            case '2': // view given courses
                showGivenCourses();
                break;
            case '3': // go to course registration system
                advCourseRegInt.advRegMenu();
                break;
            case '*': // logout
                loginInt.logout();
                break;
            case 'x': // exit
                loginInt.exit();
                break;
            default: // invalid input
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                advMenu();
                break;
        }
    }

    // it calculates the weekly schedule of advisor and stores it
    @Override
    
    public ArrayList<Course> calculateWeeklySchedule() {
        ArrayList<Course> weeklySchedule = new ArrayList<Course>();
        ArrayList<Course> givenCourses = ((Lecturer) session.getUser()).getGivenCourses();
        
        for (int i = 0; i < givenCourses.size(); i++) {
            Course course = givenCourses.get(i);
            weeklySchedule.add(course);
        }
        
        //course id
        //course class
        return weeklySchedule;

    }

    // it prints the weekly schedule of advisor
    @Override
    public void showWeeklySchedule(ArrayList<Course> courses) {
        // // course arraylist döndürecek
        
        System.out.println(Colors.RED + "\n--------------------Weekly Schedule--------------------\n" + Colors.RESET);
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.println(Colors.YELLOW + (i + 1) + Colors.RESET + ". " + course.getCourseID() + " "
                    + course.getCourseName());
        }
        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ". Back");
        System.out.println("\nWhich course do you want to see?\n");
        int choice = scanner.nextInt();

    }

    //
    public void showGivenCourses() {

    }
}