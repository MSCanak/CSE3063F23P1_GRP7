import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AdvisorCourseRegistrationInterface {
    Colors Colors = new Colors();
    private Session session;
    private Scanner scanner;

    private ArrayList<String> selectionCourses;
    private HashMap<String, String> idToSelectionCourses;

    private ArrayList<String> courses;
    private HashMap<String, ArrayList<String>> idToCourses;

    private HashMap<String, String> courseToLectures;
    private HashMap<String, HashMap<String, String>> idToCourseLectures;

    private HashMap<String, String> courseToLabs;
    private HashMap<String, HashMap<String, String>> idToCourseLabs;

    private MessagesInterface messagesInterface;
    private AdvisorInterface advisorInt;
    Object requestsObj;
    JSONArray requestJson;
    Object coursObject;
    JSONArray courseJson;

    public AdvisorCourseRegistrationInterface(Session session, AdvisorInterface advisorInt) {
        this.session = session;
        this.scanner = new Scanner(System.in);

        this.selectionCourses = new ArrayList<String>();
        this.idToSelectionCourses = new HashMap<String, String>();

        this.courses = new ArrayList<String>();
        this.idToCourses = new HashMap<String, ArrayList<String>>();

        this.courseToLectures = new HashMap<String, String>();
        this.idToCourseLectures = new HashMap<String, HashMap<String, String>>();

        this.courseToLabs = new HashMap<String, String>();
        this.idToCourseLabs = new HashMap<String, HashMap<String, String>>();

        try {
            requestsObj = new JSONParser().parse(new FileReader("./jsons/RegistrationRequests.json"));
            requestJson = (JSONArray) requestsObj;
            coursObject = new JSONParser().parse(new FileReader("./jsons/courses.json"));
            courseJson = (JSONArray) coursObject;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void advRegMenu() {

        while (true) {
            System.out.println(Colors.getBOLD() + Colors.getRED() + "\nAdvisor Course Registration Menu\n"
                    + Colors.getRESET() + Colors.getRESET());
            System.out.println(Colors.getYELLOW() + "1" + Colors.getRESET() + ".   Show Students");
            System.out.println(Colors.getYELLOW() + "2" + Colors.getRESET() + ".   Approve/Deny Courses");
            System.out.println(Colors.getYELLOW() + "3" + Colors.getRESET() + ".   Finalize Registration");
            System.out.println(Colors.getYELLOW() + "4" + Colors.getRESET() + ".   Messages Menu");
            System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".   Go Back to Advisor Menu");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            char choice = scanner.next().charAt(0);
            System.out.print(Colors.getRESET());

            switch (choice) {
                case '1':
                    showStudents();
                    showStudentsQuestionPart();
                    break;
                case '2':
                    approveCoursesMenu();
                    // return;
                    break;
                case '3':
                    finalizeRegistrationMenu();
                    break;
                case '4':
                    messagesInterface = new MessagesInterface(session);
                    messagesInterface.messagesMenu();
                    break;
                case '0':
                    return; // bu kısımda sıkıntılar var
                default:
                    System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                    advRegMenu();
                    break;
            }
        }

    }

    private void showStudents() {
        Advisor advisor = (Advisor) session.getUser();

        System.out.println(Colors.getBOLD() +
                Colors.getRED() + "\nStudents\n" + Colors.getRESET() + Colors.getRESET());
        int numberOfStudents = 1;
        System.out.println("------------------------------------------------------------");

        System.out.printf("| %6s | %-11s | %-33s |%n", "Number", "Student ID", "Student Name");
        for (Student student : advisor.getStudents()) {
            System.out.println("------------------------------------------------------------");
            System.out.printf("| %-6s | %-11s | %-33s |%n", numberOfStudents, student.getID(),
                    student.getName() + " " + student.getSurname());
            numberOfStudents++;

        }
        System.out.println("------------------------------------------------------------\n");

    }

    private void showStudentsQuestionPart() {
        System.out
                .println(Colors.getYELLOW() + "0" + Colors.getRESET()
                        + ".  Go back to the Advisor Course Registration Menu.\n");
        System.out.print(Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");

        System.out.print(Colors.getBLUE());
        char choice1 = scanner.next().charAt(0);
        System.out.print(Colors.getRESET());

        if (choice1 == '0') {
            return;
        }
    }

    private void approveCoursesMenu() {
        while (true) {
            Advisor advisor = (Advisor) session.getUser();

            showStudents();

            System.out.println(
                    Colors.getYELLOW() + "0" + Colors.getRESET()
                            + ".  Go back to the Advisor Course Registration Menu.\n");
            System.out.print(Colors.getBLUE() + "--> " + Colors.getRESET() + "Select student for approval: ");
            System.out.print(Colors.getBLUE());
            int choice = scanner.nextInt();
            System.out.print(Colors.getRESET());
            if (choice == 0) {
                return;
            }

            Student student = advisor.getStudents().get(choice - 1);
            System.out.println();
            System.out.println(Colors.getGREEN() + "Student ID: " + student.getID() + "\n" + "Student Name: "
                    + student.getName() + " " + student.getSurname() + Colors.getRESET() + "\n");
            String studentID = student.getID();
            showStudentsRequested(studentID);
        }

    }

    private void showStudentsRequested(String studentID) {
        int numberOfCourses = 1;

        for (Object requestObj : requestJson) {
            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("StudentID");
            if (studentID.equals(requestID)) {
                JSONArray selectedCourses = (JSONArray) request.get("SelectedCourses");
                JSONArray selectedLectures = (JSONArray) request.get("SelectedLectures");
                JSONArray selectedLabs = (JSONArray) request.get("SelectedLabs");

                for (Object courseObj : selectedCourses) {
                    String course = (String) courseObj;
                    for (Object lectureObj : selectedLectures) {
                        String lecture = (String) lectureObj;
                        if (lecture.contains(course)) {
                            courseToLectures.put(course, lecture);
                        }
                    }

                    for (Object labObj : selectedLabs) {
                        String lab = (String) labObj;
                        if (lab.contains(course)) {
                            courseToLabs.put(course, lab);
                        }
                    }
                    courses.add(course);

                    numberOfCourses++;
                }

                // mapping student id to courses
                idToCourses.put(studentID, courses);
                idToCourseLectures.put(studentID, courseToLectures);
                idToCourseLabs.put(studentID, courseToLabs);

                // resetting for new student
                courses = new ArrayList<String>();
                courseToLectures = new HashMap<String, String>();
                courseToLabs = new HashMap<String, String>();

                // print lectures and labs
                for (int i = 0; i < numberOfCourses - 1; i++) {
                    System.out
                            .println(Colors.getYELLOW() + (i + 1) + Colors.getRESET() + ". "
                                    + idToCourses.get(studentID).get(i));
                    if (idToCourseLectures.get(studentID).get(idToCourses.get(studentID).get(i)) != null) {
                        System.out.println(Colors.getYELLOW() + "Lecture: " + Colors.getRESET()
                                + idToCourseLectures.get(studentID).get(idToCourses.get(studentID).get(i)));

                    }
                    if (idToCourseLabs.get(studentID).get(idToCourses.get(studentID).get(i)) != null) {
                        System.out.println(Colors.getYELLOW() + "Lab: " + Colors.getRESET()
                                + idToCourseLabs.get(studentID).get(idToCourses.get(studentID).get(i)));

                    }
                    System.out.println("----------------------------------");
                }

            }
        }
        approveCourse(studentID);

    }

    private void approveCourse(String studentID) {
        if (idToCourses.isEmpty()) {
            System.out
                    .println(Colors.getYELLOW() + "No courses to approve! Please select different student!"
                            + Colors.getRESET());
            return;
        }

        System.out.println(
                Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Advisor Course Registration Menu.");
        System.out.print(Colors.getBLUE() + "\n--> " + Colors.getRESET() + "Select course to approve: ");

        while (true) {
            System.out.print(Colors.getBLUE());
            int choice = scanner.nextInt();
            System.out.print(Colors.getRESET());
            if (choice == 0) {
                break;
            }

            String course = idToCourses.get(studentID).get(choice - 1);
            selectionCourses.add(course);
            System.out.println(Colors.getGREEN() + course + " is approved!" + Colors.getRESET());
            System.out.println(
                    Colors.getBLUE() + " If your selection is finished, you can press 0. " + Colors.getRESET());
            System.out.print(Colors.getRED() + " OR " + Colors.getBLUE() + "\n--> " + Colors.getRESET()
                    + "Select course to approve: ");
        }
        saveApprovel(studentID);
        // send notification to student
        String receiverID = studentID;
        String description = "Advisor has approved your courses!";
        String senderID = session.getUser().getID();
        Notification notification = new Notification(receiverID, description, senderID);
        notification.sendNotification(senderID);

    }

    private void saveApprovel(String studentID) {
        for (Object requestObj : requestJson) {
            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("StudentID");
            if (studentID.equals(requestID)) {
                JSONArray approvedCoursesJsonArray = (JSONArray) request.get("ApprovedCourses");
                JSONArray selectedCourses = (JSONArray) request.get("SelectedCourses");

                JSONArray approvedLectureJsonArray = (JSONArray) request.get("ApprovedLectures");
                JSONArray selectedLectures = (JSONArray) request.get("SelectedLectures");

                JSONArray approvedLabJsonArray = (JSONArray) request.get("ApprovedLabs");
                JSONArray selectedLabs = (JSONArray) request.get("SelectedLabs");

                HashMap<String, String> courseToLabsTemp = idToCourseLabs.get(studentID);
                HashMap<String, String> courseToLectureTemp = idToCourseLectures.get(studentID);

                for (String course : selectionCourses) {
                    approvedCoursesJsonArray.add(course);
                    if (courseToLabsTemp.get(course) != null) {
                        approvedLabJsonArray.add(courseToLabsTemp.get(course));
                    }
                    if (courseToLectureTemp.get(course) != null) {
                        approvedLectureJsonArray.add(courseToLectureTemp.get(course));
                    }
                }
                selectedCourses.clear();
                selectedLectures.clear();
                selectedLabs.clear();

            }
        }

        try {
            PrintWriter pw = new PrintWriter("./jsons/RegistrationRequests.json");
            pw.write(requestJson.toJSONString());

            pw.flush();
            pw.close();

        } catch (Exception e) {
        }

    }

    private void finalizeRegistrationMenu() {
        showStudents();
        System.out.println(
                Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Advisor Course Registration Menu.\n");
        System.out.print(Colors.getBLUE() + "--> " + Colors.getRESET() + "Select student for finalization: ");
        System.out.print(Colors.getBLUE());
        int choice = scanner.nextInt();
        System.out.print(Colors.getRESET());
        Advisor advisor = (Advisor) session.getUser();

        Student curStudent = advisor.getStudents().get(choice - 1);
        Object forDelete = new Object();
        for (Object requestObj : requestJson) {
            forDelete = requestObj;
            ArrayList<JSONObject> finalizedArrayList = new ArrayList<JSONObject>();

            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("StudentID");
            if (!curStudent.getID().equals(requestID)) {
                continue;
            }
            JSONArray approvedCoursesJsonArray = (JSONArray) request.get("ApprovedCourses");
            if (approvedCoursesJsonArray.isEmpty()) {
                System.out.println(Colors.getYELLOW() + "No courses to finalize! Please select different student!"
                        + Colors.getRESET());
                return;
            }
            for (Object courseObj : approvedCoursesJsonArray) {
                String course = (String) courseObj;
                for (Object courseListObject : courseJson) {
                    JSONObject courseList = (JSONObject) courseListObject;
                    String courseID = (String) courseList.get("CourseID");
                    if (course.equals(courseID)) {
                        String newCourseID = (String) courseList.get("CourseID");
                        String newCourseName = (String) courseList.get("CourseName");
                        String newCourseType = (String) courseList.get("Type");
                        Long newCourseCredit = (Long) (courseList.get("Credit"));
                        Long newCourseSemester = (Long) courseList.get("Semester");
                        Double newCourseGrade = 0.0;

                        JSONObject newCourse = new JSONObject();
                        newCourse.put("CourseID", newCourseID);
                        newCourse.put("CourseName", newCourseName);
                        newCourse.put("Type", newCourseType);
                        newCourse.put("Credit", newCourseCredit);
                        newCourse.put("Semester", newCourseSemester);
                        newCourse.put("Grade", newCourseGrade);

                        finalizedArrayList.add(newCourse);

                    }
                }

            }

            String filename = requestID + ".json";
            try {
                Object studentObj = new JSONParser().parse(new FileReader("./jsons/student/" + filename));
                JSONObject student = (JSONObject) studentObj;
                JSONObject transcriptObj = (JSONObject) student.get("Transcript");
                JSONArray semArray = (JSONArray) transcriptObj.get("Semester");

                JSONArray takenCourses = (JSONArray) student.get("TakenCourses");
                String studentID = (String) student.get("ID");
                HashMap<String, String> courseToLabsTemp = idToCourseLabs.get(studentID);
                HashMap<String, String> courseToLectureTemp = idToCourseLectures.get(studentID);

                for (Object courseObj : approvedCoursesJsonArray) {
                    String course = (String) courseObj;
                    takenCourses.add(course);
                    if (courseToLabsTemp == null) {
                        continue;
                    }
                    if (courseToLabsTemp.get(course) != null) {
                        takenCourses.add(courseToLabsTemp.get(course));
                    }
                    if (courseToLectureTemp.get(course) != null) {
                        takenCourses.add(courseToLectureTemp.get(course));
                    }
                }

                JSONObject newCourses = new JSONObject();

                newCourses.put("Courses", finalizedArrayList);

                semArray.add(newCourses);

                PrintWriter studentPw = new PrintWriter("./jsons/student/" + filename);
                studentPw.write(student.toJSONString());

                studentPw.flush();
                studentPw.close();

                // send notification to student
                String receiverID = studentID;
                String description = "Advisor has finalized your courses!";
                String senderID = advisor.getID();
                Notification notification = new Notification(receiverID, description, senderID);
                notification.sendNotification(senderID);

                // statement ekle bu isimli öğrencinin dersleri finilization edildi diye advisor
                // course reg menuye dönüyorsunuz tarzı aynı logindeki gibi bir senaryo olabilir
                System.out.println(Colors.getGREEN() + "Courses of student " + studentID + " are finalized!" + Colors.getRESET());

            } catch (Exception e) {
                System.out.println(e);
            }

        }
        try {
            requestJson.remove(forDelete);
            PrintWriter requestPw = new PrintWriter("./jsons/RegistrationRequests.json");
            requestPw.write(requestJson.toJSONString());
            requestPw.flush();
            requestPw.close();
        } catch (Exception e) {
        }
    }

}