import java.util.ArrayList;

class Advisor extends Person {

    private ArrayList<Student> students = new ArrayList<Student>();

    public Advisor(String name, String surname, String ID, String password, String email, String phoneNumber, String faculty, String department, ArrayList<Student> students){
        super(name, surname, ID, password, email, phoneNumber, faculty, department);
        this.students = students;
    }

    public ArrayList<Student> getStudents(){ 
        return students;
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }
}