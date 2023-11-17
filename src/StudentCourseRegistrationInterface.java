import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        // this.studentInt = studentInt;
        availableCourses = new ArrayList<Course>();
        selectedCourses = new ArrayList<Course>();
        scanner = new Scanner(System.in);
        // showStudentInf();
        // stuRegMenu();
    }

    public void stuRegMenu() {
        while (true) {
            System.out.println("1. Selected courses");
            System.out.println("2. Available courses");
            System.out.println("0. Go back to main menu");

            var choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    selectedCoursesMenu();
                    break;
                case 2:
                    availableCoursesMenu();
                    break;
                case 0:
                    return;
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
    }

    private void selectedCoursesMenu() {
        while (true) {
            System.out.println("1. Show selected courses");
            System.out.println("2. Delete selected courses");
            System.out.println("3. Send registration request");
            System.out.println("0. Go back to main menu");
            var choice = scanner.nextInt();
            if (selectedCourses.size() == 0) {
                System.out.println("No selected courses");
                break;
            }
            switch (choice) {
                case 1:
                    showSelectedCourses();
                    break;
                case 2:
                    deleteSelectedCourses();
                    break;
                case 3:
                    sendRegRequest();
                    break;
                case 0:
                    return;
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
    }

    private void deleteSelectedCourses() {
        showSelectedCourses();
        while (true) {
            System.out.println("1. Delete all selected courses");
            System.out.println("2. Delete a selected course");
            System.out.println("0. Go back to main menu");
            var choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    selectedCourses.clear();
                    return;
                case 2:
                    System.out.print("Enter the number of the course you want to delete (for example -> 1-2-3): ");
                    var selectedIndexes = scanner.next().split("-");
                    System.out.print("Do you want to delete selected courses? (y/n): ");
                    var saveChoice = scanner.next();
                    if (saveChoice.toLowerCase().equals("y")) {
                        deleteSelectedCourses(selectedIndexes);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private void deleteSelectedCourses(String[] selectedIndexes) {
        // copy selected courses to a new list
        var selectedCoursesCopy = new ArrayList<Course>();
        for (var course : selectedCourses) {
            selectedCoursesCopy.add(course);
        }
        // remove selected courses from selected courses
        for (var selectedIndex : selectedIndexes) {
            var index = Integer.parseInt(selectedIndex);
            selectedCourses.remove(selectedCoursesCopy.get(index - 1));
        }
    }

    private void sendRegRequest() {
        // Convert the selected courses to JSON
        var selectedCoursesJsonArray = new JSONArray();
        for (var course : selectedCourses) {
            var courseJson = new JSONObject();
            courseJson.put("CourseID", course.getCourseID());
            selectedCoursesJsonArray.add(courseJson);
        }

        // Create the final JSON object
        var registrationJson = new JSONObject();
        registrationJson.put("StudentID", student.getID());
        registrationJson.put("SelectedCourses", selectedCoursesJsonArray);

        // Write the selected courses JSON to the file
        try (var fileWriter = new FileWriter("jsons/RegistrationRequests.json")) {
            fileWriter.write(registrationJson.toJSONString());
            System.out.println("Registration request written to RegistrationRequests.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void availableCoursesMenu() {
        while (true) {
            System.out.println("1. Show available courses");
            System.out.println("0. Go back to main menu");
            var choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    calculateAvailableCourses();
                    showAvailableCourses();
                    System.out.print("Select courses you want to add (for example -> 1-2-3): ");
                    var input = scanner.next();
                    var selectedIndexes = input.split("-");
                    System.out.println();
                    System.out.print("Do you want to save selected courses? (y/n): ");
                    var saveChoice = scanner.next();
                    if (saveChoice.toLowerCase().equals("y")) {
                        saveAvailableCourses(selectedIndexes);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private Course findCourseByID(ArrayList<Course> courses, String courseID) {
        for (var course : courses) {
            if (course.getCourseID().equals(courseID)) {
                return course;
            }
        }
        return null; // returns when course not found
    }

    private void calculateAvailableCourses() {
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

            // clear availableCourses
            availableCourses.clear();

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
                    var availableCoursesCopy = new ArrayList<Course>();
                    for (var availableCourse : availableCourses) {
                        availableCoursesCopy.add(availableCourse);
                    }

                    if (courseObj != null) {
                        if ((Float) courseGrade <= 1) {
                            // check if that course is in availableCourses already
                            var isExists = false;
                            for (var availableCourse : availableCoursesCopy) {
                                if (availableCourse.getCourseID().equals(courseID)) {
                                    isExists = true;
                                    break;
                                }
                            }
                            if (!isExists) {
                                availableCourses.add(courseObj);
                            }

                            for (var availableCourse : availableCoursesCopy) {
                                var mandatoryPrerequisitesOfAvailableCourse = availableCourse
                                        .getMandatoryPrerequisite();

                                for (var mandatoryPrerequisite : mandatoryPrerequisitesOfAvailableCourse) {
                                    if (mandatoryPrerequisite.getCourseID().equals(courseID)) {
                                        availableCourses.remove(availableCourse);
                                    }
                                }
                            }
                        } else if ((Float) courseGrade >= 1) {
                            for (var availableCourse : availableCoursesCopy) {
                                if (availableCourse.getCourseID().equals(courseID)) {
                                    continue;
                                }
                            }
                            for (var availableCourse : availableCoursesCopy) {
                                var optionalPrerequisitesOfAvailableCourse = availableCourse.getOptionalPrerequisite();

                                for (var optionalPrerequisite : optionalPrerequisitesOfAvailableCourse) {
                                    if (optionalPrerequisite.getCourseID().equals(courseID)) {

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
            // remove from availableCourses if that course already in selectedCourses
            var availableCoursesCopy = new ArrayList<Course>();
            for (var availableCourse : availableCourses) {
                availableCoursesCopy.add(availableCourse);
            }
            for (var availableCourse : availableCourses) {
                for (var selectedCourse : selectedCourses) {
                    if (selectedCourse.getCourseID().equals(availableCourse.getCourseID())) {
                        availableCoursesCopy.remove(availableCourse);
                    }
                }
            }
            availableCourses = availableCoursesCopy;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAvailableCourses() {
        int courseNumber = 1;
        System.out.printf("%n%-8s%-13s%-70s%-8s%-15s%n", "Number", "CourseID", "CourseName", "Credit", "CourseType");
        System.out.printf(
                "--------------------------------------------------------------------------------------------------------------%n");

        for (var course : availableCourses) {

            System.out.printf("%-8s%-13s%-70s%-8s%-15s%n", courseNumber++, course.getCourseID(), course.getCourseName(),
                    course.getCredit(),
                    course.isElective() ? "Elective" : "Mandatory");

        }
        System.out.println();
    }

    private void saveAvailableCourses(String[] selectedIndexes) {
        for (var selectedIndex : selectedIndexes) {
            var index = Integer.parseInt(selectedIndex);
            selectedCourses.add(availableCourses.get(index - 1));
        }
    }
}
