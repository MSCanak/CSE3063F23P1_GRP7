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
    private Session session;
    private Scanner scanner;
    private ArrayList<String> selectionCourses;
    private ArrayList<String> courses;


    private HashMap<String, String> courseToLecture;
    private HashMap<String, String> courseToLab;


    private MessagesInterface messagesInterface;
    Object requestsObj;
    JSONArray requestJson;
    Object coursObject;
    JSONArray courseJson;

    public AdvisorCourseRegistrationInterface(Session session, AdvisorInterface advisorInt) {
        this.session = session;
        this.scanner = new Scanner(System.in);
        this.selectionCourses = new ArrayList<String>();
        this.courses = new ArrayList<String>();
        this.courseToLecture = new HashMap<String, String>();
        this.courseToLab = new HashMap<String, String>();

        try {
            requestsObj = new JSONParser().parse(new FileReader("./jsons/RegistrationRequests.json"));
            requestJson = (JSONArray) requestsObj;
            coursObject = new JSONParser().parse(new FileReader("./jsons/courses.json"));
            courseJson = (JSONArray) coursObject;

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void advRegMenu() {
        System.out.println(Colors.RED + "\n----------Advisor Course Registration Menu----------\n" + Colors.RESET);
        while (true) {
            System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   Show Students");
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   Approve/Deny Courses");
            System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Finalize Registration");
            System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Go Back to Advisor Menu");
            System.out.println("What do you want to do?\n");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    showStudents();
                    break;
                case 2:
                    approveCoursesMenu();
                    // return;
                    break;
                case 3:
                    finalizeRegistrationMenu();
                    break;
                case 4:
                    return;
                default:
                    System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                    advRegMenu();
                    break;
            }
        }

    }

    private void showStudents() {
        // System.out.println("//////////////////////////////////");
        Advisor advisor = (Advisor) session.getUser();

        System.out.println(Colors.RED + "\n----------Students----------\n" + Colors.RESET);
        int numberOfStudents = 1;

        // System.out.println(advisor.getStudents().size());
        for (Student student : advisor.getStudents()) {
            System.out.println(
                    numberOfStudents + " : " + student.getName() + " " + student.getSurname() + " " + student.getID());
            numberOfStudents++;

        }
        System.out.println("----------------------------------");
        // System.out.println("//////////////////////////////////");

        return;

    }

    private void approveCoursesMenu() {
        while (true) {
            Advisor advisor = (Advisor) session.getUser();

            showStudents();
            System.out.println("Select student for approval or press 0 for back to menu: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                return;
            }
            
            Student student = advisor.getStudents().get(choice - 1);
            System.out.println("----------------------------------");
            System.out.println("Student: " + student.getName() + " " + student.getSurname() + " " + student.getID());
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
                    for(Object lectureObj : selectedLectures) {
                        String lecture = (String) lectureObj;
                        if(lecture.contains(course)) {
                            courseToLecture.put(course, lecture);
                        }
                    }

                    for(Object labObj : selectedLabs) {
                        String lab = (String) labObj;
                        if(lab.contains(course)) {
                            courseToLab.put(course, lab);
                        }
                    }
                    courses.add(course);
                    numberOfCourses++;
                }

                //print lectures and labs
                for (String course : courses) {
                    System.out.println(numberOfCourses - 1 + " : " + course + " " + courseToLecture.get(course) + " " + courseToLab.get(course));
                }

            }
        }

        while (true) {
            System.out.println("Please select a course to approve or press 0 to exit");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else if (choice > 0 && choice <= courses.size()) {
                selectionCourses.add(courses.get(choice - 1));
                // courses.remove(choice-1);
                System.out.println("Course Approved");
            } else if (choice == '#') {
                selectionCourses = courses;
                System.out.println("All courses approved");

            } else {
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);

            }
        }

        saveApprovel(studentID);

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



                for (String course : selectionCourses) {
                    approvedCoursesJsonArray.add(course);
                    if(courseToLab.get(course) != null) {
                        approvedLabJsonArray.add(courseToLab.get(course));
                    }
                    if(courseToLecture.get(course) != null) {
                        approvedLectureJsonArray.add(courseToLecture.get(course));
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
        int choice = scanner.nextInt();
        Advisor advisor = (Advisor) session.getUser();

        Student curStudent = advisor.getStudents().get(choice - 1);

        for (Object requestObj : requestJson) {
            ArrayList<JSONObject> finalizedArrayList = new ArrayList<JSONObject>();

            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("StudentID");
            if (!curStudent.getID().equals(requestID)) {
                continue;
            }
            JSONArray approvedCoursesJsonArray = (JSONArray) request.get("ApprovedCourses");
            if (approvedCoursesJsonArray.isEmpty()) {
                continue;
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
                

                for(Object courseObj : approvedCoursesJsonArray) {
                    String course = (String) courseObj;
                    takenCourses.add(course);
                    if(courseToLab.get(course) != null) {
                        takenCourses.add(courseToLab.get(course));
                    }
                    if(courseToLecture.get(course) != null) {
                        takenCourses.add(courseToLecture.get(course));
                    }
                }

                JSONObject newCourses = new JSONObject();

                newCourses.put("Courses", finalizedArrayList);

                semArray.add(newCourses);

                PrintWriter studentPw = new PrintWriter("./jsons/student/" + filename);
                studentPw.write(student.toJSONString());

                studentPw.flush();
                studentPw.close();

                requestJson.remove(request);
                PrintWriter requestPw = new PrintWriter("./jsons/RegistrationRequests.json");
                requestPw.write(requestJson.toJSONString());
                requestPw.flush();
                requestPw.close();
                if(requestJson.isEmpty()) {
                    break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

}