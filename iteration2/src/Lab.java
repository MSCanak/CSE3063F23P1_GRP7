public class Lab extends Course{

    
    private String labCourseId;

    public Lab(String labCourseId, String courseName, String courseID, int credit, String type, int semester, double grade, ArrayList<Course> optionalPrerequisite, ArrayList<Course> mandatoryPrerequisite){
        super(courseName, courseID, credit, type, semester, grade, optionalPrerequisite, mandatoryPrerequisite);
        this.labCourseId = labCourseId;
    }

    public Lab(String labCourseId, String courseName, String courseID, int credit, String type, int semester){
        super(courseName, courseID, credit, type, semester);
        this.labCourseId = labCourseId;
    }
    
    public Lab(String labCourseId, String courseName, String courseID, int credit, String type, int semester, double grade){
        super(courseName, courseID, credit, type, semester, grade);
        this.labCourseId = labCourseId;
    }

    public String getCourseId(){
        return labCourseId;
    }
    
    public void setCourseId(int courseId){
        this.labCourseId = courseId;
    }
    
}
