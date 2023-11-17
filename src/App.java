import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        // Create Login object and login
        Json json = new Json();

        Person person = json.searchPerson("150101");
        System.out.println(person.getID());

        Person student = json.searchPerson("150120000");
        System.out.println(student.getID());

        ArrayList<Student> students = new ArrayList<Student>();
        students.add((Student)student);
        person.setStudents(students);

        // AdvisorCourseRegistrationInterface advRegInt = new AdvisorCourseRegistrationInterface((Advisor) person);
        // advRegInt.advRegMenu();

        StudentCourseRegistrationInterface stuRegInt = new StudentCourseRegistrationInterface((Student) student);
        stuRegInt.stuRegMenu();
    }
    
}