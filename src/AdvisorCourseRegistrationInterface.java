import java.util.Scanner;

public class AdvisorCourseRegistrationInterface {
    private Advisor advisor;
    private AdvisorInterface advisorInt;
    private Scanner scanner;

    public AdvisorCourseRegistrationInterface(Advisor advisor, AdvisorInterface advisorInt) {
        this.advisor = advisor;
        this.advisorInt = advisorInt;
        this.scanner = new Scanner(System.in);
    }

    public void advRegMenu() {
        System.out.println("Welcome to the Advisor Course Registration Interface");
        System.out.println("Please select an option:");
        System.out.println("1. Show Students");
        System.out.println("2. Approve/Deny Courses");
        System.out.println("3. Finalize Registration");
        System.out.println("4. Go Back to Main Menu");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                showStudents();
                break;
            case 2:
                approveCoursesMenu();
                break;
            case 3:
                finalizeRegistrationMenu();
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                advRegMenu();
                break;
        }

    }

    private void showStudents() {
        for (Student student : advisor.getStudents()) {
            System.out.println(student.getName() + " " + student.getSurname() + " " + student.getID());
        }
    }
    private void showStudentsRequested() {}
    private void approveCoursesMenu() {}
    private void showStudentsCourses() {}
    private void saveApprovel() {}
    private void showStudentsApproved() {}
    private void finalizeRegistrationMenu() {}

}