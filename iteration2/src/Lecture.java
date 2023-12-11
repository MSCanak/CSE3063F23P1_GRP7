public class Lecture extends Course {

    String lectureCourseId;

    public Lecture(String courseName, String courseID, int credit, String type, int semester) {
        super(courseName, courseID, credit, type, semester);
    }

    public Lecture(String courseName, String courseID, int credit, String type, int semester, double grade) {
        super(courseName, courseID, credit, type, semester, grade);
    }

    public Lecture(String courseName, String courseID, CourseSession courseSession) {
        super(courseName, courseID, courseSession);
    }

    public String getLectureCourseId() {
        return lectureCourseId;
    }

    public void setLectureCourseId(String lectureCourseId) {
        this.lectureCourseId = lectureCourseId;
    }
}
