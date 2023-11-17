import java.util.ArrayList;

class Advisor extends Person {

    private ArrayList<Student> students = new ArrayList<Student>();

    public Advisor(String name, String surname, String email, String phoneNumber, String faculty, String department, String ID, String password){
        super(name, surname, ID, password, email, phoneNumber, faculty, department);
        this.students = students;
    }

    public ArrayList<Student> getStudents(){ 
        return students;
    }

    public void setStudent(Student student){
        this.students.add(student);
    }
}