public class Lecture extends Course {

    String lectureId;

    public Lecture(String courseName, String courseID, int credit, String type, int semester) {
        super(courseName, courseID, credit, type, semester);
    }

    public Lecture(String courseName, String courseID, int credit, String type, int semester, double grade) {
        super(courseName, courseID, credit, type, semester, grade);
    }

    public Lecture(String courseName, String courseID, CourseSession courseSession) {
        super(courseName, courseID, courseSession);
    }

    public Lecture(String courseName, String courseID, CourseSession courseSession, Lecturer lecturer) {
        super(courseName, courseID, courseSession, lecturer);
    }

    public String getLectureId() {
        return lectureId;
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }
}
