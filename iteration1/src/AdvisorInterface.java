import java.util.Scanner;

public class AdvisorInterface{
    
    private Advisor advisor;
    private Scanner scanner;

    private LoginInterface loginInt;
    private AdvisorCourseRegistrationInterface advCourseRegInt;


    public AdvisorInterface(Advisor advisor, LoginInterface loginInt){
        this.advisor = advisor;
        this.loginInt = loginInt;   
    }
    
    public void advMenu(){

        scanner = new Scanner(System.in);
        System.out.print("1) Course registiration System\n2) Logout\n3) Exit\n");
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
}