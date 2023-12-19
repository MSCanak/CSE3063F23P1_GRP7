
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

        while (true) {
            scanner = new Scanner(System.in);
            System.out.println(Colors.RED + "\n--------------------Advisor Menu--------------------\n" + Colors.RESET);
            System.out.println(Colors.YELLOW + "1" + Colors.RESET + ".   View Notifications");
            System.out.println(Colors.YELLOW + "2" + Colors.RESET + ".   View Weekly Schedule");
            System.out.println(Colors.YELLOW + "3" + Colors.RESET + ".   View Given Courses");
            System.out.println(Colors.YELLOW + "4" + Colors.RESET + ".   Course Registration System");
            System.out.println(Colors.YELLOW + "*" + Colors.RESET + ".   Logout");
            System.out.println(Colors.YELLOW + "x" + Colors.RESET + ".   Exit");

            System.out.print("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ");

            System.out.print(Colors.BLUE);
            char caseToken = scanner.next().charAt(0);
            System.out.print(Colors.RESET);

            switch (caseToken) {
                case '1': // view notifications
                    notificationsInt = new NotificationsInterface(session);
                    notificationsInt.notificationsMenu();
                    break;
                case '2': // view weekly schedule
                    showWeeklySchedule(calculateWeeklySchedule());
                    break;
                case '3': // view given courses
                    showGivenCourses(calculateWeeklySchedule());
                    break;
                case '4': // go to course registration system
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

        scanner = new Scanner(System.in);

        // creating arraylists for each day of the week
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

        System.out.println(Colors.RED
                + "\n-------------------------------------------------------------Weekly Schedule--------------------------------------------------------------\n"
                + Colors.RESET);
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.printf("|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n", "", "Monday",
                "Tuesday", "Wednesday",
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
                    } else {
                        mondayCoursesID.add(((Lab) course).getLabID());
                    }
                    mondayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    mondayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Salı")) {
                    if (course instanceof Lecture) {
                        tuesdayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        tuesdayCoursesID.add(((Lab) course).getLabID());
                    }

                    tuesdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    tuesdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Çarşamba")) {
                    if (course instanceof Lecture) {
                        wednesdayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        wednesdayCoursesID.add(((Lab) course).getLabID());
                    }
                    wednesdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    wednesdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Perşembe")) {
                    if (course instanceof Lecture) {
                        thursdayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        thursdayCoursesID.add(((Lab) course).getLabID());
                    }
                    thursdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    thursdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Cuma")) {
                    if (course instanceof Lecture) {
                        fridayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        fridayCoursesID.add(((Lab) course).getLabID());
                    }

                    fridayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    fridayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Cumartesi")) {
                    if (course instanceof Lecture) {
                        saturdayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        saturdayCoursesID.add(((Lab) course).getLabID());
                    }
                    saturdayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    saturdayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                } else if (courseSession.getCourseDay().get(j).equals("Pazar")) {
                    if (course instanceof Lecture) {
                        sundayCoursesID.add(((Lecture) course).getLectureID());
                    } else {
                        sundayCoursesID.add(((Lab) course).getLabID());
                    }
                    sundayCoursesPlace.add(courseSession.getCoursePlace().get(j));
                    sundayCoursesStartTime.add(courseSession.getCourseStartTime().get(j));

                }
            }

        }
        String mondayCourses;
        String tuesdayCourses;
        String wednesdayCourses;
        String thursdayCourses;
        String fridayCourses;
        String saturdayCourses;
        String sundayCourses;

        String mondayCoursePlace;
        String tuesdayCoursePlace;
        String wednesdayCoursePlace;
        String thursdayCoursePlace;
        String fridayCoursePlace;
        String saturdayCoursePlace;
        String sundayCoursePlace;

        WeeklySchedule weeklySchedule = new WeeklySchedule();

        for (int k = 0; k < CourseSessionTimes.SESSION_START.size(); k++) {

            mondayCourses = weeklySchedule.printMondayCourses(mondayCoursesID, mondayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            tuesdayCourses = weeklySchedule.printTuesdayCourses(tuesdayCoursesID, tuesdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START,
                    k);
            wednesdayCourses = weeklySchedule.printWednesdayCourses(wednesdayCoursesID, wednesdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            thursdayCourses = weeklySchedule.printThursdayCourses(thursdayCoursesID, thursdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            fridayCourses = weeklySchedule.printFridayCourses(fridayCoursesID, fridayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            saturdayCourses = weeklySchedule.printSaturdayCourses(saturdayCoursesID, saturdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            sundayCourses = weeklySchedule.printSundayCourses(sundayCoursesID, sundayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);

            mondayCoursePlace = weeklySchedule.printMondayCoursePlace(mondayCoursesPlace, mondayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            tuesdayCoursePlace = weeklySchedule.printTuesdayCoursePlace(tuesdayCoursesPlace, tuesdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            wednesdayCoursePlace = weeklySchedule.printWednesdayCoursePlace(wednesdayCoursesPlace,
                    wednesdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            thursdayCoursePlace = weeklySchedule.printThursdayCoursePlace(thursdayCoursesPlace,
                    thursdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            fridayCoursePlace = weeklySchedule.printFridayCoursePlace(fridayCoursesPlace, fridayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);
            saturdayCoursePlace = weeklySchedule.printSaturdayCoursePlace(saturdayCoursesPlace,
                    saturdayCoursesStartTime,
                    CourseSessionTimes.SESSION_START,
                    k);
            sundayCoursePlace = weeklySchedule.printSundayCoursePlace(sundayCoursesPlace, sundayCoursesStartTime,
                    CourseSessionTimes.SESSION_START, k);

            if (mondayCourses != "" || tuesdayCourses != "" || wednesdayCourses != "" || thursdayCourses != ""
                    || fridayCourses != "" || saturdayCourses != "" || sundayCourses != "") {
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n",
                        "", mondayCourses,
                        tuesdayCourses, wednesdayCourses, thursdayCourses, fridayCourses, saturdayCourses,
                        sundayCourses);
                System.out.printf("|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n",
                        CourseSessionTimes.SESSION_START.get(k) + " - " + CourseSessionTimes.SESSION_END.get(k),
                        mondayCoursePlace, tuesdayCoursePlace, wednesdayCoursePlace, thursdayCoursePlace,
                        fridayCoursePlace, saturdayCoursePlace, sundayCoursePlace);
            }

        }
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------\n");

        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Back to Advisor Menu");
        System.out.print("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ");
        System.out.print(Colors.BLUE);
        char caseToken = scanner.next().charAt(0);
        System.out.print(Colors.RESET);
        switch (caseToken) {
            case '0':
                advMenu();
                break;
            default:
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                showWeeklySchedule(courses);
                break;
        }

    }

    // it prints the given courses of advisor
    public void showGivenCourses(ArrayList<Course> courses) {
        scanner = new Scanner(System.in);
        System.out.println(Colors.RED + "\n----------------------Given Courses----------------------\n" + Colors.RESET);

        Course course;
        ArrayList<String> courseID = new ArrayList<String>();

        System.out.println("---------------------------------------------------------");
        System.out.printf("|  %s%-13s%s|  %s%-35s%s  |%n", Colors.YELLOW, "Course ID", Colors.RESET, Colors.YELLOW,
                "Course Name", Colors.RESET);

        for (int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            if (course instanceof Lecture) {
                courseID.add(((Lecture) course).getLectureID());
            } else {
                courseID.add(((Lab) course).getLabID());
            }

            System.out.println("---------------------------------------------------------");
            System.out.printf("|  %-13s|  %-35s  |%n", courseID.get(i), course.getCourseName());

        }
        System.out.println("---------------------------------------------------------\n");

        System.out.println(Colors.YELLOW + "0" + Colors.RESET + ".  Back to Advisor Menu");
        System.out.print("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ");
        System.out.print(Colors.BLUE);
        char caseToken = scanner.next().charAt(0);
        System.out.print(Colors.RESET);
        switch (caseToken) {
            case '0':
                advMenu();
                break;
            default:
                System.out.println(Colors.YELLOW + "Invalid input! Please try again." + Colors.RESET);
                showGivenCourses(courses);
                break;
        }

    }

}