import java.util.ArrayList;

public class Course {
    
    // initialize variables

    private String courseName,courseID;
    private int credit;
    private boolean isTheoretical, isElective;
    private CourseSession Session;
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
    public boolean isTheoretical() {
        return isTheoretical;
    }
    public void setTheoretical(boolean isTheoretical) {
        this.isTheoretical = isTheoretical;
    }
    public boolean isElective() {
        return isElective;
    }
    public void setElective(boolean isElective) {
        this.isElective = isElective;
    }
    public CourseSession getSession() {
        return Session;
    }
    public void setSession(CourseSession session) {
        Session = session;
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

}
