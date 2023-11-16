
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Course {

    // initialize variables

    private String courseName, courseID;
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

        Object coursesJSONobj;
        JSONArray courseJSONarr;
        coursesJSONobj = new JSONParser().parse(new FileReader("./jsons/courses.json"));

        courseJSONarr = (JSONArray) coursesJSONobj;

        int previousSemester = -1;
        for (Object courseObj : courseJSONarr) {
            JSONObject course = (JSONObject) courseObj;

            String CourseID = (String) course.get("CourseID");
            String CourseName = (String) course.get("CourseName");
            String CourseType = (String) course.get("CourseType");
            long Credit = (long) course.get("Credit");
            long Semester = (long) course.get("Semester");
            JSONArray OptionalPrerequisites = (JSONArray) course.get("OptionalPrerequisites");
            JSONArray MandatoryPrerequisites = (JSONArray) course.get("MandatoryPrerequisites");

            if (CourseType.equals("M")) {
                CourseType = "Mandatory";
            } else if (CourseType.equals("E")) {
                CourseType = "Elective";
            }

            String optionalPrerequisitesString = OptionalPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");
            String mandatoryPrerequisitesString = MandatoryPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");

            if (previousSemester != Semester) {
                System.out.printf(
                        "-----------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
                System.out.printf("Semester %s", Semester);
                System.out.printf("\t%-10s%-45s%-15s%-8s%-35s%-35s%n%n",
                        "CourseID", "CourseName", "CourseType", "Credit",
                        "OptionalPrerequisites", "MandatoryPrerequisites");
                previousSemester = (int) Semester;
            }

            System.out.printf("\t\t%-10s%-45s%-15s%-8s%-35s%-35s%n",
                    CourseID, CourseName, CourseType, Credit,
                    optionalPrerequisitesString, mandatoryPrerequisitesString);

        }

    }

}
