import java.util.ArrayList;

public class Lab extends Course{
    
    private String labId;

    public Lab(String courseName,  String courseID, int credit, String type, int semester){
        super(courseName, courseID, credit, type, semester);
    }

    public Lab(String courseName, String courseID, int credit, String type, int semester, double grade){
        super(courseName, courseID, credit, type, semester);
    }
    
    public Lab(String courseName, String courseID, CourseSession courseSession){
        super(courseName, courseID, courseSession);
    }

    public Lab(String courseName, String courseID, CourseSession courseSession, Lecturer lecturer){
        super(courseName, courseID, courseSession,lecturer);
    }

    public String getLabId(){
        return labId;
    }
    
    public void setLabId(String courseId){
        this.labId = courseId;
    }   
}
