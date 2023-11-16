import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        showStudentInf();
        stuRegMenu();
    }

    public void stuRegMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("0")) {
            System.out.println("1. Selected courses");
            System.out.println("2. Available courses");
            System.out.println("0. Go back to main menu");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    selectedCoursesMenu();
                    break;
                case "2":
                    availableCoursesMenu();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void showStudentInf() {
        System.out.println("Student ID - Name and Surname: " + student.getID() + " - " + student.getName() + " "
                + student.getSurname());
        System.out.print("Advisor: " + student.getAdvisor().getName() + " " + student.getAdvisor().getSurname());
        System.out.println("Advisor ID: " + student.getAdvisor().getID());
        System.out.println("Semester: " + student.getSemester());

        // continue when enter is pressed
        System.out.println("\nPress enter to continue");
        scanner.nextLine();
    }

    private void selectedCoursesMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("0")) {
            System.out.println("1. Show selected courses");
            System.out.println("2. Delete selected courses");
            System.out.println("3. Send registration request");
            System.out.println("0. Go back to main menu");
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
                case "0":
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
        while (choice.equals("0")) {
            System.out.println("1. Delete all selected courses");
            System.out.println("2. Delete a selected course");
            System.out.println("0. Go back to main menu");
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
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void sendRegRequest() {

    }

    private void availableCoursesMenu() {
        scanner = new Scanner(System.in);
        var choice = "";
        while (choice.equals("0")) {
            System.out.println("1. Show available courses");
            System.out.println("2. Save available courses");
            System.out.println("0. Go back to main menu");
            choice = scanner.next();
            switch (choice) {
                case "1":
                    calculateAvailableCourses();
                    showAvailableCourses();
                    System.out.print("Select courses you want to add (for example -> 1 2 3): ");
                    var courseIDs = scanner.nextLine().split(" ");
                    break;
                case "2":
                    saveAvailableCourses();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static Course findCourseByID(ArrayList<Course> courses, String courseID) {
        for (var course : courses) {
            if (course.getCourseID().equals(courseID)) {
                return course;
            }
        }
        return null; // returns when course not found
    }

    private void calculateAvailableCourses() throws Exception {
        var allCourses = new ArrayList<Course>();
        var targetSemester = student.getSemester();

        // Read courses from JSON file
        try {
            var coursesJson = "jsons/courses.json";
            var parser = new JSONParser();
            var obj = parser.parse(new FileReader(coursesJson));
            var coursesArray = (JSONArray) obj;

            // creating all courses and add them to availableCourses
            for (var courseObj : coursesArray) {
                var courseJson = (JSONObject) courseObj;
                var courseName = (String) courseJson.get("CourseName");
                var semester = ((Long) courseJson.get("Semester")).intValue();
                var courseID = ((String) courseJson.get("CourseID")).trim();
                var credit = ((Long) courseJson.get("Credit")).intValue();
                var isElective = !((String) courseJson.get("CourseType")).equals("M");

                var course = new Course(courseName, courseID, credit, isElective, semester);
                allCourses.add(course);
            }

            // add optional and mandatory prerequisite course to each course by finding
            // their courseIDs
            for (var courseObj : coursesArray) {
                var courseJson = (JSONObject) courseObj;
                var courseID = ((String) courseJson.get("CourseID")).trim();
                var course = allCourses.stream().filter(c -> c.getCourseID().equals(courseID)).findFirst().get();

                var mandatoryPrerequisites = (JSONArray) courseJson.get("MandatoryPrerequisites");
                var optionalPrerequisites = (JSONArray) courseJson.get("OptionalPrerequisites");

                var mandatoryPrerequisiteList = new ArrayList<Course>();
                var optionalPrerequisiteList = new ArrayList<Course>();

                for (var mandatoryPrerequisite : mandatoryPrerequisites) {
                    var mandatoryPrerequisiteCourseID = ((String) mandatoryPrerequisite).trim();
                    var mandatoryPrerequisiteCourse = findCourseByID(allCourses, mandatoryPrerequisiteCourseID);

                    if (mandatoryPrerequisiteCourse != null) {
                        mandatoryPrerequisiteList.add(mandatoryPrerequisiteCourse);
                    } else {
                        System.err.println(
                                "Mandatory prerequisite course with ID " + mandatoryPrerequisiteCourseID
                                        + " not found.");
                    }
                }

                for (var optionalPrerequisite : optionalPrerequisites) {
                    var optionalPrerequisiteCourseID = ((String) optionalPrerequisite).trim();
                    var optionalPrerequisiteCourse = findCourseByID(allCourses, optionalPrerequisiteCourseID);

                    if (optionalPrerequisiteCourse != null) {
                        optionalPrerequisiteList.add(optionalPrerequisiteCourse);
                    } else {
                        System.err.println(
                                "Optional prerequisite course with ID " + optionalPrerequisiteCourseID + " not found.");
                    }
                }

                course.setMandatoryPrerequisite(mandatoryPrerequisiteList);
                course.setOptionalPrerequisite(optionalPrerequisiteList);
            }

            // filter by target semester and add to availableCourses

            for (var course : allCourses) {
                if (course.getSemester() == targetSemester)
                    availableCourses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // student information reading
        try {
            var stuId = student.getID();
            var studentJson = "jsons/student/" + stuId + ".json";
            var studentObj = new JSONParser().parse(new FileReader(studentJson));
            var studentJSONObject = (JSONObject) studentObj;
            var transcript = (JSONObject) studentJSONObject.get("Transcript");
            var allSemesterArray = (JSONArray) transcript.get("Semester");

            for (var semester : allSemesterArray) {
                var currentSemester = (JSONObject) semester;
                var currentCourseArray = (JSONArray) currentSemester.get("Courses");

                for (var course : currentCourseArray) {
                    var currentCourse = (JSONObject) course;
                    var courseID = ((String) currentCourse.get("CourseID")).trim();
                    var courseGrade = currentCourse.get("Grade");
                    // convert to float or double
                    if (courseGrade instanceof Long) {
                        courseGrade = ((Long) courseGrade).floatValue();
                    } else if (courseGrade instanceof Double) {
                        courseGrade = ((Double) courseGrade).floatValue();
                    }
                    var courseObj = findCourseByID(allCourses, courseID);
                    // if failed add to failedCourses
                    if (courseObj != null) {
                        if ((Float) courseGrade <= 1) {
                            availableCourses.add(courseObj);
                            for (var availableCourse : availableCourses) {
                                var mandatoryPrerequisitesOfAvailableCourse = availableCourse
                                        .getMandatoryPrerequisite();

                                for (var mandatoryPrerequisite : mandatoryPrerequisitesOfAvailableCourse) {
                                    if (mandatoryPrerequisite.getCourseID().equals(courseID)) {
                                        availableCourses.remove(availableCourse);
                                    }
                                }
                            }
                        } else if ((Float) courseGrade >= 1) {
                            for (var availableCourse : availableCourses) {
                                var optionalPrerequisitesOfAvailableCourse = availableCourse.getOptionalPrerequisite();

                                for (var mandatoryPrerequisite : optionalPrerequisitesOfAvailableCourse) {
                                    if (mandatoryPrerequisite.getCourseID().equals(courseID)) {
                                        availableCourses.add(courseObj);
                                    }
                                }
                            }
                        }
                    } else {
                        System.err.println("Course with ID " + courseID + " not found.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAvailableCourses() {
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

    }

}
