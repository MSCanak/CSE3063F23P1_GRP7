import java.util.ArrayList;

public class Lab extends Course{
    
    private String labCourseId;

    public Lab(String courseName,  String courseID, int credit, String type, int semester){
        super(courseName, courseID, credit, type, semester);
    }

    public Lab(String courseName, String courseID, int credit, String type, int semester, double grade){
        super(courseName, courseID, credit, type, semester);
    }
    
    public Lab(String courseName, String courseID, CourseSession courseSession){
        super(courseName, courseID, courseSession);
    }

    public String getLabCourseId(){
        return labCourseId;
    }
    
    public void setCourseId(String courseId){
        this.labCourseId = courseId;
    }   
}
