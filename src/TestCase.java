import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.HashMap;

public class TestCase {
    private Student student;
    private Advisor advisor;
    private Transcript transcript;
    private Course course;

    
    
    
    @Before
    public void setUp() {
        advisor = new Advisor("Murat", "Ganiz", null, null, null, null, "12000", "1234");
        student = new Student("Ensar", "NNNN", null, null, null, null, null, null, 5, advisor);
        transcript = new Transcript(student);
        
        HashMap<Integer, Double> grades = new HashMap<Integer, Double>();
        grades.put(1, 3.0);
        grades.put(0, 2.0);

        transcript.setGano(grades);
        transcript.setYano(grades);

        student.setTranscript(transcript);
        advisor.setStudent(student);

        course = new Course("Algo", "CSE", 3, null, 1, 5);
        
    }
    
    // Student tests
    @Test
    public void getSemester() {
        assertEquals(5, student.getSemester());
    }

    @Test
    public void getAdvisor() {
        assertEquals(advisor, student.getAdvisor());
    }

    @Test
    public void getTranscript() {
        assertEquals(transcript, student.getTranscript());
    }

    // Advisor tests
    @Test
    public void getStudents() {
        assertEquals(student, advisor.getStudents().get(0));
    }

    @Test
    public void getName() {
        assertEquals("Murat", advisor.getName());
    }

    @Test
    public void getPassword() {
        assertEquals("1234", advisor.getPassword());
    }

    @Test
    public void getID() {
        assertEquals("12000", advisor.getID());
    }

    // Transcript tests
    @Test
    public void getGano() {
        assertEquals(3.0, transcript.getGano().get(1), 0.0);
    }

    @Test
    public void getYano() {
        assertEquals(2.0, transcript.getYano().get(0), 0.0);
    }

    @Test
    public void getStudentFromTranscript() {
        assertEquals(student, transcript.getStudent());
    }


    // Course tests
    @Test
    public void getCourseName() {
        assertEquals("Algo", course.getCourseName());
    }

    @Test
    public void getCourseID() {
        assertEquals("CSE", course.getCourseID());
    }

    @Test
    public void getCredit() {
        assertEquals(3, course.getCredit());
    }

    @Test
    public void getSemesterFromCourse() {
        assertEquals(1, course.getSemester());
    }

    @Test
    public void getGrade() {
        assertEquals(5, course.getGrade(), 0.0);
    }
}
