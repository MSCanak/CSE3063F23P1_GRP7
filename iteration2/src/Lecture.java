import java.util.ArrayList;

public class Lecture extends Course {

    String lectureCourseId;

    public Lecture(String courseName, String courseID, int credit, String type, int semester) {
        super(courseName, courseID, credit, type, semester);
    }

    public Lecture(String courseName, String courseID, int credit, String type, int semester, double grade) {
        super(courseName, courseID, credit, type, semester, grade);
    }

    public Lecture(String lectureCourseId, String courseName, String courseID, String type, int credit,
            int theoric, int practice, int quota, ArrayList<Course> optionalPrerequisite,
            ArrayList<Course> mandatoryPrerequisite, ArrayList<Student> courseStudent, Lecturer lecturer) {
        super(courseName, courseID, type, credit, theoric, practice, quota, optionalPrerequisite, mandatoryPrerequisite,
                courseStudent, lecturer);
        this.lectureCourseId = lectureCourseId;
    }

    public String getLectureCourseId() {
        return lectureCourseId;
    }

    public void setLectureCourseId(String lectureCourseId) {
        this.lectureCourseId = lectureCourseId;
    }
}
