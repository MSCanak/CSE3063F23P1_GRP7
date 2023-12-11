import java.util.ArrayList;
import java.util.Scanner;

public class AdvisorInterface implements Schedule, NotificationsMenu{
    
    private Scanner scanner;
    private Session session;
    private NotificationsInterface notificationsInt;
    private LoginInterface loginInt;
    private AdvisorCourseRegistrationInterface advCourseRegInt;


    public AdvisorInterface(Session session, LoginInterface loginInt){
        this.session = session;
        this.loginInt = loginInt;
        advCourseRegInt = new AdvisorCourseRegistrationInterface(session, this);
    }

    //the terminal inteface for advisor
    public void advMenu(){

        scanner = new Scanner(System.in);
        System.out.print("1) Course registiration System\n*) Logout\nx) Exit\n");
        char caseToken = scanner.next(). charAt(0);
       
        
        switch(caseToken){
            case '1': //for course Registration system
                advCourseRegInt.advRegMenu();
                break;
            case '*': // logout
                loginInt.logout();
                break;
            case 'x': //exit
                loginInt.exit();
                break;
            default :
                System.out.println("Please choose an action");
                advCourseRegInt.advRegMenu();
        }
    }
    //it calculates the weekly schedule of advisor and stores it 
    public void calculateWeeklySchedule(){}
    //it prints the weekly schedule of advisor
    public void showWeeklySchedule(){}
    //
    public void showGivenCourses(){}
}