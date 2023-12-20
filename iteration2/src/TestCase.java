import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;


public class TestCase {
    private Student student;
    private Advisor advisor;
    private Course course;
    private CourseSession courseSession;
    private Lecturer lecturer;
    private Message message;
    private Notification notification;
    
    
    @Before
    public void setUp() {
        advisor = new Advisor("Murat", "Ganiz", "murat@mail.com", "055", "150101", "password", "Eng", "CSE", "DR.");
        student = new Student("Ensar", "Yozgat", "ensar@mail.com", "051", "150120000", "pass","Eng", "CSE", 5, advisor);

        advisor.setStudent(student);

        course = new Course("Cse101", "101", 3, "Mandatory", 1);
        courseSession = new CourseSession(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
        
        lecturer = new Lecturer("Betul", "boz", "boz@mail.com", "0555", "102", "passw", "Eng", "CSE", "DR.");
    
        message = new Message("senderID", "receiverID", "description", "subject");
        
        notification = new Notification("receiverID", "description", "senderID", false, 1);
    }


    // Student tests
    @Test
    public void testStudentName() {
        assertEquals("Ensar", student.getName());
    }
    @Test
    public void testStudentSurname() {
        assertEquals("Yozgat", student.getSurname());
    }
    @Test
    public void testStudentID() {
        assertEquals("150120000", student.getID());
    }
    @Test
    public void testAdvisor() {
        assertEquals(advisor, student.getAdvisor());
    }
    
    // Advisor tests
    @Test
    public void testAdvisorName() {
        assertEquals("Murat", advisor.getName());
    }
    @Test
    public void testAdvisorSurname() {
        assertEquals("Ganiz", advisor.getSurname());
    }
    @Test
    public void testAdvisorID() {
        assertEquals("150101", advisor.getID());
    }
    @Test
    public void testAdvisorStudent() {
        ArrayList <Student> students = advisor.getStudents();
        assertEquals(student, students.get(0));
    }
   
    //Course tests
    @Test
    public void testCourseID() {
        assertEquals("101", course.getCourseID());
    }
    @Test
    public void testGetMandotorCourse() {
        ArrayList <Course> courses = new ArrayList<Course>();
        Course course2 = new Course("Cse102", "102", 3, "Mandatory", 1);
        courses.add(course2);
        course.setMandatoryPrerequisite(courses);
        assertEquals(course2, course.getMandatoryPrerequisite().get(0));
    }
    @Test
    public void testCourseStudent() {
        course.setCourseStudents(15);
        assertEquals(15, course.getCourseStudents());
    }

    //CourseSession tests
    @Test
    public void testCourseSessionDay() {
        ArrayList <String> days = new ArrayList<String>();
        days.add("Monday");
        days.add("Wednesday");
        courseSession.setCourseDay(days);
        assertEquals("Monday", courseSession.getCourseDay().get(0));
    }
    @Test
    public void testCourseSessionStartTime() {
        ArrayList <String> startTimes = new ArrayList<String>();
        startTimes.add("09:00");
        startTimes.add("11:00");
        courseSession.setCourseStartTime(startTimes);
        assertEquals("09:00", courseSession.getCourseStartTime().get(0));
    }
    @Test
    public void testCourseSessionEndTime() {
        ArrayList <String> endTimes = new ArrayList<String>();
        endTimes.add("10:00");
        endTimes.add("12:00");
        courseSession.setCourseEndTime(endTimes);
        assertEquals("10:00", courseSession.getCourseEndTime().get(0));
    }
    @Test
    public void testCourseSessionPlace() {
        ArrayList <String> places = new ArrayList<String>();
        places.add("A1");
        places.add("A2");
        courseSession.setCoursePlace(places);
        assertEquals("A1", courseSession.getCoursePlace().get(0));
    }

    //Lecturer tests
    @Test
    public void testLecturerName() {
        assertEquals("Betul", lecturer.getName());
    }
    @Test
    public void testLecturerID() {
        assertEquals("102", lecturer.getID());
    }
    @Test
    public void testLecturerGivenCourses() {
        ArrayList <Course> courses = new ArrayList<Course>();
        Course course2 = new Course("Cse102", "102", 3, "Mandatory", 1);
        courses.add(course2);
        lecturer.setGivenCourses(courses);
        assertEquals(course2, lecturer.getGivenCourses().get(0));
    }
    @Test
    public void testLecturerSetCourses() {
        Course course3 = new Course("Cse103", "103", 3, "Mandatory", 1);
        lecturer.setCourse(course3);
        assertEquals(course3, lecturer.getGivenCourses().get(0));
    }

    //Message tests
    @Test
    public void testMessageSenderID() {
        assertEquals("senderID", message.getSenderID());
    }
    @Test
    public void testMessageReceiverID() {
        assertEquals("receiverID", message.getReceiverID());
    }
    @Test
    public void testMessageDescription() {
        assertEquals("description", message.getDescription());
    }
    @Test
    public void testMessageSubject() {
        assertEquals("subject", message.getSubject());
    }

    //Notification tests
    @Test
    public void testNotificationReceiverID() {
        assertEquals("receiverID", notification.getReceiver());
    }
    @Test
    public void testNotificationDescription() {
        assertEquals("description", notification.getDescription());
    }
    @Test
    public void testNotificationSenderID() {
        assertEquals("senderID", notification.getSenderID());
    }
    @Test
    public void testNotificationIsRead() {
        assertEquals(false, notification.getIsRead());
        notification.setIsRead(true);
        assertEquals(true, notification.getIsRead());
    }
    @Test
    public void testNotificationID() {
        assertEquals(1, notification.getNotificationID());
    }
    


}
