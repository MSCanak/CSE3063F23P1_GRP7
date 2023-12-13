public class Lab extends Course{
    
    private String labID;

    public Lab(String courseName,  String labID, int credit, String type, int semester){
        super(courseName, labID, credit, type, semester);
        var lastIndex = labID.indexOf(".");
        var courseID = labID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.labID = labID;
    }

    public Lab(String courseName, String labID, int credit, String type, int semester, double grade){
        super(courseName, labID, credit, type, semester);
        var lastIndex = labID.indexOf(".");
        var courseID = labID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.labID = labID;
    }
    
    public Lab(String courseName, String labID, CourseSession courseSession){
        super(courseName, labID, courseSession);
        var lastIndex = labID.indexOf(".");
        var courseID = labID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.labID = labID;
    }

    public Lab(String courseName, String labID, CourseSession courseSession, Lecturer lecturer){
        super(courseName, labID, courseSession,lecturer);
        var lastIndex = labID.indexOf(".");
        var courseID = labID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.labID = labID;
    }

    public Lab(String courseName, String labID, int quota, CourseSession courseSession){
        super(courseName, labID, quota, courseSession);
        var lastIndex = labID.indexOf(".");
        var courseID = labID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.labID = labID;
    }

    public String getLabID(){
        return labID;
    }
    
    public void setLabID(String courseId){
        this.labID = courseId;
    }   
}
