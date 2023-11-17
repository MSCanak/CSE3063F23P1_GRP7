import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AdvisorCourseRegistrationInterface {
    private Advisor advisor;
    private AdvisorInterface advisorInt;
    private Scanner scanner;
    private ArrayList<String> selectionCourses;
    private ArrayList<String> courses;
    Object requestsObj;
    JSONArray requestJson;
    Object coursObject;
    JSONArray courseJson;

    public AdvisorCourseRegistrationInterface(Advisor advisor, AdvisorInterface advisorInt) {
        this.advisor = advisor;
        this.advisorInt = advisorInt;
        this.scanner = new Scanner(System.in);
        this.selectionCourses = new ArrayList<String>();
        this.courses = new ArrayList<String>();
        try {
            requestsObj = new JSONParser().parse(new FileReader("./jsons/registrationRequests.json"));
            requestJson = (JSONArray) requestsObj;
            coursObject = new JSONParser().parse(new FileReader("./jsons/courses.json"));
            courseJson = (JSONArray) coursObject;
        } 
        catch (Exception e) {
            // TODO: handle exception
        }
       
    }

    public void advRegMenu() {
        System.out.println("Welcome to the Advisor Course Registration");
        while(true) {
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
                // return;
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

    }

    private void showStudents() {
        System.out.println("//////////////////////////////////");
        int numberOfStudents = 1;

        // System.out.println(advisor.getStudents().size());
        for (Student student : advisor.getStudents()) {
            System.out.println(numberOfStudents + " : " + student.getName() + " " + student.getSurname() + " " + student.getID());
            
        }
        System.out.println("//////////////////////////////////");


        return;
    
    }

    private void approveCoursesMenu() {
        while(true) {

            showStudents();
            System.out.println("Select student for approval or press 0 for back to menu: ");
            int choice = scanner.nextInt();

            if(choice == 0) {
                return;
            }

            Student student = advisor.getStudents().get(choice-1);
            System.out.println("//////////////////////////////////");
            System.out.println("Student: " + student.getName() + " " + student.getSurname() + " " + student.getID());
            String studentID = student.getID();
            showStudentsRequested(studentID);
        }
        
    }

    private void showStudentsRequested(String studentID) {
        int numberOfCourses = 1;
       
        for (Object requestObj : requestJson) {
            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("Id");
            if(studentID.equals(requestID)) {
                JSONArray selectedCourses = (JSONArray) request.get("SelectedCourses");
                
                for(Object courseObj : selectedCourses) {
                    String course = (String) courseObj;
                    courses.add(course);
                    System.out.println(numberOfCourses + " : "+ course);
                    numberOfCourses++;
                }
        
            }
        }
        
        while(true) {
            System.out.println("Please select a course to approve or press 0 to exit");
            int choice = scanner.nextInt();
            if(choice == 0) {
                break;
            }
            else if(choice > 0 && choice <= courses.size()) {
                selectionCourses.add(courses.get(choice-1));
                // courses.remove(choice-1);
                System.out.println("Course Approved");
            }
            else if(choice == '#') {
                selectionCourses = courses;
                System.out.println("All courses approved");
                
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        saveApprovel(studentID);
        
    }
    
    // private void showStudentsCourses() {

    // }
    private void saveApprovel(String studentID) {
        for (Object requestObj : requestJson) {
            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("Id");
            if(studentID.equals(requestID)) {
                JSONArray approvedCoursesJsonArray= (JSONArray) request.get("ApprovedCourses");
                JSONArray selectedCourses = (JSONArray) request.get("SelectedCourses");

                selectedCourses.clear();
                for(String course : selectionCourses) {
                    approvedCoursesJsonArray.add(course);
                }
            }
        }

        try {
            PrintWriter pw = new PrintWriter("./jsons/RegistrationRequests.json"); 
            pw.write(requestJson.toJSONString()); 
          
            pw.flush(); 
            pw.close(); 
            
        } 
        catch (Exception e) {
        }
        
    }
    // private void showStudentsApproved(String studentID) {
        
    // }
    private void finalizeRegistrationMenu() {
        showStudents();
        int choice = scanner.nextInt();
        
        Student curStudent = advisor.getStudents().get(choice-1);

        for (Object requestObj : requestJson) {
            ArrayList<JSONObject> finalizedArrayList = new ArrayList<JSONObject>();

            JSONObject request = (JSONObject) requestObj;
            String requestID = (String) request.get("Id");
            if(!curStudent.getID().equals(requestID)) {
                continue;
            }
            JSONArray approvedCoursesJsonArray= (JSONArray) request.get("ApprovedCourses");
            if(approvedCoursesJsonArray.isEmpty()) {
                continue;
            }
            for(Object courseObj : approvedCoursesJsonArray) {
                String course = (String) courseObj;
                for(Object courseListObject : courseJson) {
                    JSONObject courseList = (JSONObject) courseListObject;
                    String courseID = (String) courseList.get("CourseID");
                    if(course.equals(courseID)) {
                        finalizedArrayList.add(courseList);
                    }
                }
                    
            }

            String filename = requestID + ".json";
            try {
                Object studentObj = new JSONParser().parse(new FileReader("./jsons/student/"+ filename));
                JSONObject student = (JSONObject) studentObj;
                JSONObject transcriptObj = (JSONObject) student.get("Transcript");
                JSONArray semArray = (JSONArray) transcriptObj.get("Semester");

                JSONArray newCourses = new JSONArray();
                newCourses.add(finalizedArrayList);
                semArray.add(newCourses);
                PrintWriter pw = new PrintWriter("./jsons/student/"+ filename); 
                pw.write(student.toJSONString()); 
          
                pw.flush(); 
                pw.close();
                

            } catch (Exception e) {

            }
            
            
        }
    }

}