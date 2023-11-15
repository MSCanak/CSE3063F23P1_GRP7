import java.util.ArrayList;
import java.util.Scanner;

public class StudentCourseRegistrationInterface {
    private ArrayList<Course> availableCourses;
    private ArrayList<Course> selectedCourses;
    private Student student;
    private StudentInterface studentInt;
    private Scanner scanner;

    public StudentCourseRegistrationInterface(Student student, StudentInterface studentInt) {
        this.student = student;
        this.studentInt = studentInt;
        availableCourses = new ArrayList<Course>();
        selectedCourses = new ArrayList<Course>();
        stuRegMenu();
    }

    public void stuRegMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("5")) {
            System.out.println("1. Show student information");
            System.out.println("2. Selected courses");
            System.out.println("3. Available courses");
            System.out.println("4. Show syllabus");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    showStudentInf();
                    break;
                case "2":
                    selectedCoursesMenu();
                    break;
                case "3":
                    availableCoursesMenu();
                    break;
                case "4":
                    showSyllabus();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showStudentInf() {
        System.out.println("Name: " + student.getName());
        System.out.println("Surname: " + student.getSurname());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone number: " + student.getPhoneNumber());
        System.out.println("ID: " + student.getID());
        System.out.println("Password: " + student.getPassword());
        System.out.println("Faculty: " + student.getFaculty());
        System.out.println("Department: " + student.getDepartment());
        System.out.println("Semester: " + student.getSemester());
        System.out.println("Registration Request: " + student.getRegistrationRequest()); // ??
        System.out.println("Advisor: " + student.getAdvisor().getName() + " " + student.getAdvisor().getSurname());
        // continue when enter is pressed
        System.out.println("\nPress enter to continue");
        scanner.nextLine();
    }

    private void selectedCoursesMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("4")) {
            System.out.println("1. Show selected courses");
            System.out.println("2. Delete selected courses");
            System.out.println("3. Send registration request");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    showSelectedCourses();
                    break;
                case "2":
                    deleteSelectedCourses();
                    break;
                case "3":
                    sendRegRequest();
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showSelectedCourses() {
        for (var course : selectedCourses) {
            System.out.println("Course ID: " + course.getCourseID());
            System.out.println("Course name: " + course.getCourseName());
            System.out.println("Course credits: " + course.getCredit());
            System.out.println("Course is elective: " + course.isElective());
        }
        // continue when enter is pressed
        System.out.println("\nPress enter to continue");
        scanner.nextLine();
    }

    private void deleteSelectedCourses() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("3")) {
            System.out.println("1. Delete all selected courses");
            System.out.println("2. Delete a selected course");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    selectedCourses.clear();
                    break;
                case "2":
                    System.out.print("Enter the ID of the course you want to delete: ");
                    var courseID = scanner.next();
                    for (var course : selectedCourses) {
                        if (course.getCourseID().equals(courseID)) {
                            selectedCourses.remove(course);
                            break;
                        }
                    }
                    break;
                case "3":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void sendRegRequest() {
        student.setRegistrationRequest(true); // ??
        studentInt.sendRegRequest(); // ??
    }

    private void availableCoursesMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("5")) {
            System.out.println("1. Show available courses");
            System.out.println("2. Save available courses");
            System.out.println("3. Calculate available courses");
            System.out.println("4. Show syllabus");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    showAvailableCourses();
                    break;
                case "2":
                    saveAvailableCourses();
                    break;
                case "3":
                    calculateAvailableCourses();
                    break;
                case "4":
                    showSyllabus();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void calculateAvailableCourses() {
        // calculate available courses and save them to availableCourses
    }

    private void showAvailableCourses() {
        if (availableCourses.isEmpty()) {
            System.out.println("No available courses");
            return;
        }

        for (var course : availableCourses) {
            System.out.println("Course ID: " + course.getCourseID());
            System.out.println("Course name: " + course.getCourseName());
            System.out.println("Course credits: " + course.getCredit());
            System.out.println("Course is elective: " + course.isElective());
        }
        // continue when enter is pressed
        System.out.println("\nPress enter to continue");
        scanner.nextLine();
    }

    private void saveAvailableCourses() {
        // save available courses to a current student_id.json file inside of
        // iteration1/jsons/
    }

    private void showSyllabus() {
        // show syllabus of the selected semester
    }

}
