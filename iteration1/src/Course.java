import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 


public class Course {
    
    // initialize variables

    private String courseName,courseID;
    private int credit;
    private boolean isElective;
    private ArrayList<Course> optionalPrerequisite;
    private ArrayList<Course> mandatoryPrerequisite;
    
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isElective() {
        return isElective;
    }
    public void setElective(boolean isElective) {
        this.isElective = isElective;
    }
    public ArrayList<Course> getOptionalPrerequisite() {
        return optionalPrerequisite;
    }
    public void setOptionalPrerequisite(ArrayList<Course> optionalPrerequisite) {
        this.optionalPrerequisite = optionalPrerequisite;
    }
    public ArrayList<Course> getMandatoryPrerequisite() {
        return mandatoryPrerequisite;
    }
    public void setMandatoryPrerequisite(ArrayList<Course> mandatoryPrerequisite) {
        this.mandatoryPrerequisite = mandatoryPrerequisite;
    }

    public void showCourses() throws Exception {
        
        System.out.printf("%-10s%-35s%-15s%-8s%-10s%-25s%-25s%n",
                "CourseID", "CourseName", "CourseType", "Credit", "Semester",
                "OptionalPrerequisites", "MandatoryPrerequisites"); //aralarına | koyup tablo gibi yapabilirim

        Object coursesJSONobj;
        JSONArray courseJSONarr;
        coursesJSONobj = new JSONParser().parse(new FileReader("./jsons/courses.json")); 

        courseJSONarr = (JSONArray) coursesJSONobj;

        for (Object courseObj: courseJSONarr) {
            JSONObject course = (JSONObject) courseObj;

            String CourseID = (String) course.get("CourseID"); 
            String CourseName = (String) course.get("CourseName");
            String CourseType = (String) course.get("CourseType");
            Integer Credit = (int) course.get("Credit");
            Integer Semester = (int) course.get("Semester");

            System.out.printf("Semester %d%n", Semester);
            System.out.printf("\t%1s%10s%1s%35s%1s%15s%1s%8s%1s%10s%1s%25s%1s%25s%n", "|",
                    CourseID,"|", CourseName, "|", CourseType,"|" ,Credit,"|",
                    "OptionalPrerequisites","|", "MandatoryPrerequisites");

    }

}
}

