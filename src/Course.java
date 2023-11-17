
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
    private String type;
    private int semester;
    private double grade;

    private ArrayList<Course> optionalPrerequisite;
    private ArrayList<Course> mandatoryPrerequisite;

    public Course(String courseName, String courseID, int credit, String type, int semester) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.credit = credit;
        this.type = type;
        this.semester = semester;
    }

    public Course(String courseName, String courseID, int credit, String type, int semester, double grade) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.credit = credit;
        this.type = type;
        this.semester = semester;
        this.grade = grade;
    }

    public Course(String courseName, String courseID, int credit, String type, int semester,
            ArrayList<Course> optionalPrerequisite, ArrayList<Course> mandatoryPrerequisite) {
        this.courseName = courseName;
        this.courseID = courseID;
        this.credit = credit;
        this.type = type;
        this.semester = semester;
        this.optionalPrerequisite = optionalPrerequisite;
        this.mandatoryPrerequisite = mandatoryPrerequisite;

    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }


}
