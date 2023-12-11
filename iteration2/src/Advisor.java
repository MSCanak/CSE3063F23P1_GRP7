import java.util.ArrayList;

class Advisor extends Lecturer {

    private ArrayList<Student> students = new ArrayList<Student>();

    public Advisor(String name, String surname, String email, String phoneNumber, String ID, String password, String faculty, String department, ArrayList<Course> givenCourses, String academicTitle) {
        super(name, surname, email, phoneNumber, ID, password, faculty, department, givenCourses, academicTitle);
    }


    public ArrayList<Student> getStudents(){ 
        return students;
    }

    public void setStudent(Student student){
        this.students.add(student);
    }
}