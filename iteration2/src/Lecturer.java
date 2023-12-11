import java.util.ArrayList;
public class Lecturer extends Person{

    private ArrayList<Course> givenCourses;
    private String academicTitle;

    public Lecturer(String name, String surname, String email, String phoneNumber, String ID, String password, String faculty, String department, ArrayList<Course> givenCourses, String academicTitle ) {
        super(name, surname, email, phoneNumber, ID, password, faculty, department);
        this.givenCourses = givenCourses;
        this.academicTitle = academicTitle;
    }
    public Lecturer(String name, String surname, String email, String phoneNumber, String ID, String password, String faculty, String department, String academicTitle ) {
        super(name, surname, email, phoneNumber, ID, password, faculty, department);
        this.academicTitle = academicTitle;
    }

    public ArrayList<Course> getGivenCourses() {
        return givenCourses;
    }
    public void setGivenCourses(ArrayList<Course> givenCourses) {
        this.givenCourses = givenCourses;
    }
    public String getAcademicTitle() {
        return academicTitle;
    }   
    public void setAcademicTitle(String academicTitle) {
        this.academicTitle = academicTitle;
    }
    public void setCourse(Course course){
        this.givenCourses.add(course);
    }
}
