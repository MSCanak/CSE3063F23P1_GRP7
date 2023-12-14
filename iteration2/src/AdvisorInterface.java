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

        ArrayList<String> sessionStart = new ArrayList<String>(); // bu bir class olmalı ve buradan direkt olarak
                                                                  // almalıyım
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

        ArrayList<String> sessionEnd = new ArrayList<String>(); // bu bir class olmalı ve buradan direkt olarak
                                                                // almalıyım
        sessionEnd.add("09:20");
        sessionEnd.add("10:20");
        sessionEnd.add("11:20");
        sessionEnd.add("12:20");
        sessionEnd.add("13:50");
        sessionEnd.add("14:50");
        sessionEnd.add("15:50");
        sessionEnd.add("16:50");
        sessionEnd.add("17:50");
        sessionEnd.add("18:50");
        sessionEnd.add("19:50");
        sessionEnd.add("20:50");
        sessionEnd.add("21:50");

        ArrayList<String> days = new ArrayList<String>();
        days.add("Pazartesi");
        days.add("Salı");
        days.add("Çarşamba");
        days.add("Perşembe");
        days.add("Cuma");
        days.add("Cumartesi");
        days.add("Pazar");

        ArrayList<String> mondayCoursesID = new ArrayList<String>();
        ArrayList<String> tuesdayCoursesID = new ArrayList<String>();
        ArrayList<String> wednesdayCoursesID = new ArrayList<String>();
        ArrayList<String> thursdayCoursesID = new ArrayList<String>();
        ArrayList<String> fridayCoursesID = new ArrayList<String>();
        ArrayList<String> saturdayCoursesID = new ArrayList<String>();
        ArrayList<String> sundayCoursesID = new ArrayList<String>();

        ArrayList<String> mondayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> tuesdayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> wednesdayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> thursdayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> fridayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> saturdayCoursesStartTime = new ArrayList<String>();
        ArrayList<String> sundayCoursesStartTime = new ArrayList<String>();

        ArrayList<String> mondayCoursesPlace = new ArrayList<String>();
        ArrayList<String> tuesdayCoursesPlace = new ArrayList<String>();
        ArrayList<String> wednesdayCoursesPlace = new ArrayList<String>();
        ArrayList<String> thursdayCoursesPlace = new ArrayList<String>();
        ArrayList<String> fridayCoursesPlace = new ArrayList<String>();
        ArrayList<String> saturdayCoursesPlace = new ArrayList<String>();
        ArrayList<String> sundayCoursesPlace = new ArrayList<String>();

        ArrayList<String> mondayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> tuesdayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> wednesdayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> thursdayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> fridayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> saturdayCoursesEndTime = new ArrayList<String>();
        ArrayList<String> sundayCoursesEndTime = new ArrayList<String>();

        System.out.println(Colors.RED + "\n---------------------------------------------------------------Weekly Schedule---------------------------------------------------------------\n" + Colors.RESET);
         System.out.println(
                        "-------------------------------------------------------------------------------------------------------------------------------------------------");
              
        System.out.printf("|  %-13s |  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|%n" ,"","Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday");

        Course course;
        CourseSession courseSession;

        for (int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            courseSession = course.getCourseSession();

            for (int j = 0; j < courseSession.getCourseDay().size(); j++) {
                if (courseSession.getCourseDay().get(j).equals("Pazartesi")) {
                    if (course instanceof Lecture) {
                        mondayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        mondayCoursesID.add(((Lab) course).getLabID());  
                    }
                    mondayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    mondayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    mondayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Salı")) {
                    if (course instanceof Lecture) {
                        tuesdayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        tuesdayCoursesID.add(((Lab) course).getLabID());  
                    }
                    
                    tuesdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    tuesdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    tuesdayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Çarşamba")) {
                    if (course instanceof Lecture) {
                        wednesdayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        wednesdayCoursesID.add(((Lab) course).getLabID());  
                    }
                    wednesdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    wednesdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    wednesdayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Perşembe")) {
                    if (course instanceof Lecture) {
                        thursdayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        thursdayCoursesID.add(((Lab) course).getLabID());  
                    }
                    thursdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    thursdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    thursdayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Cuma")) {
                    if (course instanceof Lecture) {
                        fridayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        fridayCoursesID.add(((Lab) course).getLabID());  
                    }

                    fridayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    fridayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    fridayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Cumartesi")) {
                    if (course instanceof Lecture) {
                        saturdayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        saturdayCoursesID.add(((Lab) course).getLabID());  
                    }
                    saturdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    saturdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    saturdayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Pazar")) {
                    if (course instanceof Lecture) {
                        sundayCoursesID.add(((Lecture) course).getLectureID());
                    }
                    else {
                        sundayCoursesID.add(((Lab) course).getLabID());  
                    }
                    sundayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    sundayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));
                    sundayCoursesEndTime.add(courseSession.getCourseEndTime().get(j));

                }
            }

        }

        for (int k = 0; k < sessionStart.size(); k++) {

            String mondayCourses = printMondayCourses(mondayCoursesID, mondayCoursesStartTime, sessionStart, k);
            String tuesdayCourses = printTuesdayCourses(tuesdayCoursesID, tuesdayCoursesStartTime, sessionStart, k);
            String wednesdayCourses = printWednesdayCourses(wednesdayCoursesID, wednesdayCoursesStartTime, sessionStart,
                    k);
            String thursdayCourses = printThursdayCourses(thursdayCoursesID, thursdayCoursesStartTime, sessionStart, k);
            String fridayCourses = printFridayCourses(fridayCoursesID, fridayCoursesStartTime, sessionStart, k);
            String saturdayCourses = printSaturdayCourses(saturdayCoursesID, saturdayCoursesStartTime, sessionStart, k);
            String sundayCourses = printSundayCourses(sundayCoursesID, sundayCoursesStartTime, sessionStart, k);

            String mondayCoursePlace = printMondayCoursePlace(mondayCoursesPlace, mondayCoursesStartTime, sessionStart,
                    k);
            String tuesdayCoursePlace = printTuesdayCoursePlace(tuesdayCoursesPlace, tuesdayCoursesStartTime,
                    sessionStart, k);
            String wednesdayCoursePlace = printWednesdayCoursePlace(wednesdayCoursesPlace, wednesdayCoursesStartTime,
                    sessionStart, k);
            String thursdayCoursePlace = printThursdayCoursePlace(thursdayCoursesPlace, thursdayCoursesStartTime,

                    sessionStart, k);
            String fridayCoursePlace = printFridayCoursePlace(fridayCoursesPlace, fridayCoursesStartTime, sessionStart,
                    k);
            String saturdayCoursePlace = printSaturdayCoursePlace(saturdayCoursesPlace, saturdayCoursesStartTime,
                    sessionStart, k);
            String sundayCoursePlace = printSundayCoursePlace(sundayCoursesPlace, sundayCoursesStartTime, sessionStart,
                    k);

            if (mondayCourses != "" || tuesdayCourses != "" || wednesdayCourses != "" || thursdayCourses != ""
                    || fridayCourses != "" || saturdayCourses != "" || sundayCourses != "") {
                System.out.println(
                        "-------------------------------------------------------------------------------------------------------------------------------------------------");
              System.out.printf("|  %-13s |  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|%n",
                        "", mondayCourses,
                        tuesdayCourses, wednesdayCourses, thursdayCourses, fridayCourses, saturdayCourses,
                        sundayCourses);
                System.out.printf("|  %-13s |  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|  %-15s|%n", sessionStart.get(k) + " - " + sessionEnd.get(k),
                        mondayCoursePlace, tuesdayCoursePlace, wednesdayCoursePlace, thursdayCoursePlace,
                        fridayCoursePlace, saturdayCoursePlace, sundayCoursePlace);

                    

            }
            
        }
        System.out.println(
                        "-------------------------------------------------------------------------------------------------------------------------------------------------");
                   


        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ". Back");
        System.out.println("\nWhich course do you want to see?\n");
        int choice = scanner.nextInt();
    }

    //
    public void showGivenCourses() {

    }

    // bunların hepsini ıd yapmayı unutma
    private String printMondayCourses(ArrayList<String> mondayCousesID, ArrayList<String> mondayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String mondayCourses = "";
        for (int i = 0; i < mondayCousesID.size(); i++) {
            if (mondayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                mondayCourses = mondayCousesID.get(i);
            }
        }
        return mondayCourses;

    }

    private String printTuesdayCourses(ArrayList<String> tuesdayCousesID, ArrayList<String> tuesdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String tuesdayCourses = "";
        for (int i = 0; i < tuesdayCousesID.size(); i++) {
            if (tuesdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                tuesdayCourses = tuesdayCousesID.get(i);
            }
        }
        return tuesdayCourses;

    }

    private String printWednesdayCourses(ArrayList<String> wednesdayCousesID,
            ArrayList<String> wednesdayCoursesStartTime, ArrayList<String> sessionStart, int k) {
        String wednesdayCourses = "";
        for (int i = 0; i < wednesdayCousesID.size(); i++) {
            if (wednesdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                wednesdayCourses = wednesdayCousesID.get(i);
            }
        }
        return wednesdayCourses;

    }

    private String printThursdayCourses(ArrayList<String> thursdayCousesID, ArrayList<String> thursdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String thursdayCourses = "";
        for (int i = 0; i < thursdayCousesID.size(); i++) {
            if (thursdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                thursdayCourses = thursdayCousesID.get(i);
            }
        }
        return thursdayCourses;

    }

    private String printFridayCourses(ArrayList<String> fridayCousesID, ArrayList<String> fridayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String fridayCourses = "";
        for (int i = 0; i < fridayCousesID.size(); i++) {
            if (fridayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                fridayCourses = fridayCousesID.get(i);
            }
        }
        return fridayCourses;

    }

    private String printSaturdayCourses(ArrayList<String> saturdayCousesID, ArrayList<String> saturdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String saturdayCourses = "";
        for (int i = 0; i < saturdayCousesID.size(); i++) {
            if (saturdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                saturdayCourses = saturdayCousesID.get(i);
            }
        }
        return saturdayCourses;

    }

    private String printSundayCourses(ArrayList<String> sundayCousesID, ArrayList<String> sundayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String sundayCourses = "";
        for (int i = 0; i < sundayCousesID.size(); i++) {
            if (sundayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                sundayCourses = sundayCousesID.get(i);
            }
        }
        return sundayCourses;

    }

    private String printMondayCoursePlace(ArrayList<String> mondayCousesPlace, ArrayList<String> mondayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String mondayCoursePlace = "";
        for (int i = 0; i < mondayCousesPlace.size(); i++) {
            if (mondayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                mondayCoursePlace = mondayCousesPlace.get(i);
            }
        }
        return mondayCoursePlace;

    }

    private String printTuesdayCoursePlace(ArrayList<String> tuesdayCousesPlace,
            ArrayList<String> tuesdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String tuesdayCoursePlace = "";
        for (int i = 0; i < tuesdayCousesPlace.size(); i++) {
            if (tuesdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                tuesdayCoursePlace = tuesdayCousesPlace.get(i);
            }
        }
        return tuesdayCoursePlace;

    }

    private String printWednesdayCoursePlace(ArrayList<String> wednesdayCousesPlace,
            ArrayList<String> wednesdayCoursesStartTime, ArrayList<String> sessionStart, int k) {
        String wednesdayCoursePlace = "";
        for (int i = 0; i < wednesdayCousesPlace.size(); i++) {
            if (wednesdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                wednesdayCoursePlace = wednesdayCousesPlace.get(i);
            }
        }
        return wednesdayCoursePlace;

    }

    private String printThursdayCoursePlace(ArrayList<String> thursdayCousesPlace,
            ArrayList<String> thursdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String thursdayCoursePlace = "";
        for (int i = 0; i < thursdayCousesPlace.size(); i++) {
            if (thursdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                thursdayCoursePlace = thursdayCousesPlace.get(i);
            }
        }
        return thursdayCoursePlace;

    }

    private String printFridayCoursePlace(ArrayList<String> fridayCousesPlace, ArrayList<String> fridayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String fridayCoursePlace = "";
        for (int i = 0; i < fridayCousesPlace.size(); i++) {
            if (fridayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                fridayCoursePlace = fridayCousesPlace.get(i);
            }
        }
        return fridayCoursePlace;

    }

    private String printSaturdayCoursePlace(ArrayList<String> saturdayCousesPlace,
            ArrayList<String> saturdayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String saturdayCoursePlace = "";
        for (int i = 0; i < saturdayCousesPlace.size(); i++) {
            if (saturdayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                saturdayCoursePlace += saturdayCousesPlace.get(i);
            }
        }
        return saturdayCoursePlace;

    }

    private String printSundayCoursePlace(ArrayList<String> sundayCousesPlace, ArrayList<String> sundayCoursesStartTime,
            ArrayList<String> sessionStart, int k) {
        String sundayCoursePlace = "";
        for (int i = 0; i < sundayCousesPlace.size(); i++) {
            if (sundayCoursesStartTime.get(i).equals(sessionStart.get(k))) {
                sundayCoursePlace += sundayCousesPlace.get(i);
            }
        }
        return sundayCoursePlace;

    }

}