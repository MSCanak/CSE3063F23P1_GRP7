import java.util.ArrayList;
import java.io.FileWriter;
import org.json.simple.JSONObject;

class Advisor extends Person {

    private ArrayList<Student> students = new ArrayList<Student>();

    JSONObject jsonObject = new JSONObject();

    public Advisor(String name, String surname, String ID, String password, String email, String phoneNumber, String faculty, String department/*, ArrayList<Student> student*/){
        super(name, surname, ID, password, email, phoneNumber, faculty, department);
        this.students = students;

        jsonObject.put("Name" + name);
        jsonObject.put("Surname" + surname);

        FileWriter file = new FileWriter("./advisor.json");
        file.write(jsonObject.toJSONString());
        file.close();
    }

    public ArrayList<Student> getStudents(){
        return students;
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }
}