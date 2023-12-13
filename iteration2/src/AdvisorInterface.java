import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class AdvisorInterface implements Schedule {

    private Scanner scanner;
    private Session session;
    private NotificationsInterface notificationsInt;
    private LoginInterface loginInt;
    private AdvisorCourseRegistrationInterface advCourseRegInt;

    public AdvisorInterface(Session session, LoginInterface loginInt) {
        this.session = session;
        this.loginInt = loginInt;
        advCourseRegInt = new AdvisorCourseRegistrationInterface(session, this);
    }

    // the terminal inteface for advisor
    public void advMenu() {

        scanner = new Scanner(System.in);
        System.out.println(Colors.RED + "\n--------------------Advisor Menu--------------------\n" + Colors.RESET);
        System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   View Weekly Schedule");
        System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   View Given Courses");
        System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   Go to Course Registration System");
        System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Logout");
        System.out.println(Colors.YELLOW + "x" + Colors.RESET + ".   Exit");
        System.out.println("\nWhat do you want to do?\n");

        char caseToken = scanner.next().charAt(0);

        switch (caseToken) {
            case '1': // view weekly schedule
                showWeeklySchedule(calculateWeeklySchedule());
                break;
            case '2': // view given courses
                showGivenCourses();
                break;
            case '3': // go to course registration system
                advCourseRegInt.advRegMenu();
                break;
            case '*': // logout
                loginInt.logout();
                break;
            case 'x': // exit
                loginInt.exit();
                break;
            default: // invalid input
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                advMenu();
                break;
        }
    }

    // it calculates the weekly schedule of advisor and stores it
    @Override

    public ArrayList<Course> calculateWeeklySchedule() {
        ArrayList<Course> weeklySchedule = ((Lecturer) session.getUser()).getGivenCourses();
        return weeklySchedule;
    }

    // it prints the weekly schedule of advisor
    @Override
    public void showWeeklySchedule(ArrayList<Course> courses) {

        System.out.println(Colors.RED + "\n--------------------Weekly Schedule--------------------\n" + Colors.RESET);
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n%n", "", "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday");
        
        
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
           
            CourseSession courseSession = course.getCourseSession();
            
            ArrayList<String> sessionStart = new ArrayList<String>(); // bu bir class olmalı ve buradan direkt olarak almalıyım
            sessionStart.add("08:30");
            sessionStart.add("09:30");
            sessionStart.add("10:30");
            sessionStart.add("11:30");
            sessionStart.add("13:00");
            sessionStart.add("14:00");
            sessionStart.add("15:00");
            sessionStart.add("16:00");
            sessionStart.add("17:00");
            sessionStart.add("18:00");
            sessionStart.add("19:00");
            sessionStart.add("20:00");
            sessionStart.add("21:00");

            ArrayList<String> days = new ArrayList<String>();
            days.add("Pazartesi");
            days.add("Salı");
            days.add("Çarşamba");
            days.add("Perşembe");
            days.add("Cuma");
            days.add("Cumartesi");
            days.add("Pazar");



            ArrayList<String> courseDay = courseSession.getCourseDay();
            ArrayList<String> courseStartTime = courseSession.getCourseStartTime();
            ArrayList<String> courseEndTime = courseSession.getCourseEndTime();
            ArrayList<String> coursePlace = courseSession.getCoursePlace();
    
            // System.out.printf("%s%s%s%s", courseDay, courseStartTime, courseEndTime,
            // coursePlace);
            // for (int j = 0; j < courseDay.size(); j++) {

            // }

            // System.out.println(courseSession.getCourseStartTime().get(i) + " - " +
            // courseSession.getCourseEndTime().get(i) + " " +
            // courseSession.getCoursePlace().get(i));
            // for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            // System.out.printf("%-20s", courseSession.getCourseDay().get(j));
            // }
            for(int j=0; j<courseSession.getCourseDay().size(); j++){
                for(int k = 0; k < sessionStart.size(); k++){
                    if(courseSession.getCourseStartTime().get(j).equals(sessionStart.get(k))){
                    
                        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n","", printMonday(course,courseSession),printTuesday(course,courseSession),printWednesday(course,courseSession),printThursday(course,courseSession),printFriday(course,courseSession),printSaturday(course,courseSession),printSunday(course,courseSession));
                         System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n",sessionStart.get(k), printMonday(course,courseSession),printTuesday(course,courseSession),printWednesday(course,courseSession),printThursday(course,courseSession),printFriday(course,courseSession),printSaturday(course,courseSession),printSunday(course,courseSession));
                    
                    
                    }
                }
            // System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n", courseSession.getCourseStartTime(), printMonday(courseSession, course),
            //         printTuesday(courseSession, course, i), printWednesday(courseSession, course, i),
            //         printThursday(courseSession, course, i), printFriday(courseSession, course, i),
            //         printSaturday(courseSession, course, i), printSunday(courseSession, course, i));


            // System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s%n",
            // courseSession.getCourseStartTime().get(i) + " - " +
            // courseSession.getCourseEndTime().get(i), "", "",
            // "", "", "", "", "");

            System.out.println("\n");

        }
        
    }
    System.out.println(Colors.YELLOW + "0" + Colors.RESET + ". Back");
        System.out.println("\nWhich course do you want to see?\n");
        int choice = scanner.nextInt();
    }

    //
    public void showGivenCourses() {

    }

    private String printMonday(Course course, CourseSession courseSession ) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Pazartesi")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";

    }

    private String printTuesday( Course course,CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Salı")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }

    private String printWednesday(Course course, CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Çarşamba")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }

    private String printThursday(Course course, CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Perşembe")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }

    private String printFriday(Course course, CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Cuma")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }

    private String printSaturday(Course course, CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Cumartesi")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }

    private String printSunday(Course course, CourseSession courseSession) {
        for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
            if (courseSession.getCourseDay().get(j).equals("Pazar")) {
                return course.getCourseName();
            } else {
                return "";
            }
        }
        return "";
    }


    
    

}