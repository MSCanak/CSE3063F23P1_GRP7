import java.util.Scanner;
public class StudentInterface {

    // attributes
    private Student student;
    private LoginInterface loginInterface;

    // constructor
    public StudentInterface(Student person, LoginInterface loginInterface) {
        this.student = person;
        this.loginInterface = loginInterface;
       
    }

    public void stuMenu() {

        // prompting
        System.out.println("What do you want to do?");
        System.out.println("Enter '1' to view transcript");
        System.out.println("Enter '2' to view courses");
        System.out.println("Enter '3' to go to Course Registration System");
        System.out.println("Enter '*' to logout");
        System.out.println("Enter 'x' to exit");

        Scanner input = new Scanner(System.in);
        char choice  = input.next().charAt(0);
        

        switch (choice) {
        
        // viewing transcript
        case '1':
            student.getTranscript().viewTranscript();
            break;

        // show courses
        case '2':
            break;

        // going to course registration system
        case '3':
        StudentCourseRegistrationInterface studentCourseRegistrationInterface = new StudentCourseRegistrationInterface(student, loginInterface);
        studentCourseRegistrationInterface.stuRegMenu();
            break;
        
        // logging out
        case '*':
        loginInterface.logout();
            break;

        // exiting
        case 'x':
        loginInterface.exit();
            break;
        
        // invalid input
        default:
        System.out.println("Invalid input.Please try again.");
        stuMenu();
            break;
        }
        input.close();
    }       
}

