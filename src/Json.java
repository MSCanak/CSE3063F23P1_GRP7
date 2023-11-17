import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator; 
import java.util.Map; 
  
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 

public class Json {

    Object studentsObj;
    Object advisorsObj;

    JSONArray studentsJson;
    JSONArray advisorsJson;

    public Json() throws Exception {
        studentsObj = new JSONParser().parse(new FileReader("./jsons/students.json")); 
        advisorsObj = new JSONParser().parse(new FileReader("./jsons/advisors.json")); 

        studentsJson = (JSONArray) studentsObj;
        advisorsJson = (JSONArray) advisorsObj; 
    }

    public Person searchPerson(String id) {
        
        Person personLast = null;
        if(id.length() == 9) {

            for (Object personObj : studentsJson) {
                JSONObject person = (JSONObject) personObj;
                String personID = (String) person.get("Id");
                if(id.equals(personID)) {
                    personLast = createStudent(id);
                }
            }

        } 
        else if(id.length() == 6) {
            for (Object personObj : advisorsJson) {
                JSONObject person = (JSONObject) personObj;
                String personID = (String) person.get("Id");
                if(id.equals(personID)) {
                    personLast = createAdvisor(person);
                }
            }

            
        } 
        else {
            System.out.println("bos");
        }
        return personLast;

    }

    private Student createStudent(String id) {
        String filename = id + ".json";
        Object studentObj = null;
        try {
            studentObj = new JSONParser().parse(new FileReader("./jsons/student/"+ filename));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        JSONObject student = (JSONObject) studentObj;

        
        String name = (String) student.get("Name");
        String surname = (String) student.get("Surname");
        String email = (String) student.get("Email");
        String phoneNumber = "1000";
        String department = (String) student.get("Department");
        String advisorId = (String) student.get("AdvisorId");
        int semester = 3;
        String faculty = (String) student.get("Faculty");
        String password = (String) student.get("Password");
        Advisor advisor = (Advisor)searchPerson(advisorId);

        Student studentObject = new Student(name, surname, email, phoneNumber, id, password, faculty, department, semester, advisor);
        return studentObject;
    }

    private Advisor createAdvisor(JSONObject person) {
        String name = (String) person.get("name");
        String surname = (String) person.get("surname");
        String email = (String) person.get("email");
        String phoneNumber = "1000";
        String department = (String) person.get("department");
        String faculty = (String) person.get("faculty");
        String password = (String) person.get("password");
        String id = (String) person.get("Id");
        Advisor advisorObject = new Advisor(name, surname, email,phoneNumber, id, password,   faculty, department);
        return advisorObject;
    }
}