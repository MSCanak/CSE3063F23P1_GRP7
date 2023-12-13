public class Lecture extends Course {

    private String lectureID;

    public Lecture(String courseName, String lectureID, int credit, String type, int semester) {
        super(courseName, lectureID, credit, type, semester);
        var lastIndex = lectureID.indexOf(".");
        var courseID = lectureID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.lectureID = lectureID;
    }

    public Lecture(String courseName, String lectureID, int credit, String type, int semester, double grade) {
        super(courseName, lectureID, credit, type, semester, grade);
        var lastIndex = lectureID.indexOf(".");
        var courseID = lectureID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.lectureID = lectureID;
    }

    public Lecture(String courseName, String lectureID, CourseSession courseSession) {
        super(courseName, lectureID, courseSession);
        var lastIndex = lectureID.indexOf(".");
        var courseID = lectureID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.lectureID = lectureID;
    }

    public Lecture(String courseName, String lectureID, CourseSession courseSession, Lecturer lecturer) {
        super(courseName, lectureID, courseSession, lecturer);
        var lastIndex = lectureID.indexOf(".");
        var courseID = lectureID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.lectureID = lectureID;
    }

    public Lecture(String courseName, String lectureID, int quota, CourseSession courseSession) {
        super(courseName, lectureID, quota, courseSession);
        var lastIndex = lectureID.indexOf(".");
        var courseID = lectureID.substring(0, lastIndex);
        this.setCourseID(courseID);
        this.lectureID = lectureID;
    }

    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureId) {
        this.lectureID = lectureId;
    }
}
