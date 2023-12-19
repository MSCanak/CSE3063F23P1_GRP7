import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StudentInterface implements Schedule {
    Colors Colors = new Colors();

    // attributes
    private Session session;
    private NotificationsInterface notificationInterface;
    private LoginInterface loginInterface;
    private StudentCourseRegistrationInterface studentCourseRegistrationInterface;
    private Scanner input;

    // constructor
    public StudentInterface(Session session, LoginInterface loginInterface) {
        this.session = session;
        this.loginInterface = loginInterface;

    }

    public void stuMenu() {

        // prompting
        System.out.println(Colors.getRED()+Colors.getBOLD() + "\n> Student Menu\n" + Colors.getRESET());
        System.out.println(Colors.getYELLOW() + "1" + Colors.getRESET() + ".   View Notifications");
        System.out.println(Colors.getYELLOW() + "2" + Colors.getRESET() + ".   View Weekly Schedule");
        System.out.println(Colors.getYELLOW() + "3" + Colors.getRESET() + ".   View Transcript");
        System.out.println(Colors.getYELLOW() + "4" + Colors.getRESET() + ".   View Curriculum");
        System.out.println(Colors.getYELLOW() + "5" + Colors.getRESET() + ".   Go to Course Registration System");

        System.out.println(Colors.getYELLOW() + "*" + Colors.getRESET() + ".   Logout");
        System.out.println(Colors.getYELLOW() + "x" + Colors.getRESET() + ".   Exit");
        System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");

        input = new Scanner(System.in);

        System.out.print(Colors.getBLUE());
        char choice = input.next().charAt(0);
        System.out.print(Colors.getRESET());

        switch (choice) {

            case '1': // viewing notifications
                showNotificatons();
                break;

            case '2': // viewing weekly schedule
                showWeeklySchedule(calculateWeeklySchedule());
                break;

            case '3': // viewing transcript
                ((Student) session.getUser()).getTranscript().viewTranscript();
                boolean a = true;
                while (a) {
                    System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Student Menu.");
                    char backChoice = input.next().charAt(0);
                    if (backChoice == '0') {
                        stuMenu();
                    } else {
                        System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                    }
                }

            case '4': // show curriculum
                try {
                    showCurriculum();
                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                } catch (ParseException e) {
                }
                break;

            // going to course registration system
            case '5':
                studentCourseRegistrationInterface = new StudentCourseRegistrationInterface(session, this);
                studentCourseRegistrationInterface.stuRegMenu();
                break;

            // logging out
            case '*':
                loginInterface.logout();
                break;

            // exiting
            case 'x':
                System.out.println(
                        Colors.getYELLOW() + Colors.getBOLD()
                                + "\n< Thank you for using Marmara Course Registration System >\n"
                                + Colors.getRESET()+Colors.getRESET());
                loginInterface.exit();
                break;

            // invalid input
            default:
                System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                stuMenu();
                break;
        }
    }

    private void showCurriculum() throws FileNotFoundException, IOException, ParseException {
        Object coursesJSONobj;
        JSONArray courseJSONarr;
        coursesJSONobj = new JSONParser().parse(new FileReader("./jsons/courses.json"));

        courseJSONarr = (JSONArray) coursesJSONobj;

        int previousSemester = -1;
        for (Object courseObj : courseJSONarr) {
            JSONObject course = (JSONObject) courseObj;

            String CourseID = (String) course.get("CourseID");
            String CourseName = (String) course.get("CourseName");
            String Type = (String) course.get("Type");
            long Credit = (long) course.get("Credit");
            long Semester = (long) course.get("Semester");
            JSONArray OptionalPrerequisites = (JSONArray) course.get("OptionalPrerequisites");
            JSONArray MandatoryPrerequisites = (JSONArray) course.get("MandatoryPrerequisites");

            if (Type.equals("M")) {
                Type = "Mandatory";
            } else if (Type.equals("E")) {
                Type = "Elective";
            }

            String optionalPrerequisitesString = OptionalPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");
            String mandatoryPrerequisitesString = MandatoryPrerequisites.toString().replace("\"", "").replace("[", "")
                    .replace("]", "").replace(", ", "-");

            if (previousSemester != Semester) {
                System.out.printf(
                        "%n-----------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
                System.out.printf("%sSemester %s%s", Colors.getRED(), Semester, Colors.getRESET());
                System.out.printf("\t%s%-10s%-45s%-15s%-8s%-35s%-35s%s%n%n", Colors.getYELLOW(),
                        "CourseID", "CourseName", "Type", "Credit",
                        "OptionalPrerequisites", "MandatoryPrerequisites", Colors.getRESET());
                previousSemester = (int) Semester;
            }

            System.out.printf("\t\t%-10s%-45s%-15s%-8s%-35s%-35s%n",
                    CourseID, CourseName, Type, Credit,
                    optionalPrerequisitesString, mandatoryPrerequisitesString);

        }
        boolean a = true;
        while (a) {
            System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Student Menu.");
            input = new Scanner(System.in);
            char backChoice = input.next().charAt(0);
            if (backChoice == '0') {
                stuMenu();
            } else {
                System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
            }
        }
    }

    private void showNotificatons() {
        notificationInterface = new NotificationsInterface(session);
        notificationInterface.notificationsMenu();

    }

    public ArrayList<Course> calculateWeeklySchedule() {
        ArrayList<Course> currentTakenCourses = ((Student) (session.getUser())).getCurrentTakenCourses();
        return currentTakenCourses;
    }

    @Override
    public void showWeeklySchedule(ArrayList<Course> courses) {
        Scanner scanner = new Scanner(System.in);

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

        System.out.println(Colors.getRED()+Colors.getBOLD()
                + "\n>> Weekly Schedule\n"
                + Colors.getRESET()+Colors.getRESET());
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

        System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Back to Student Menu");
        System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
        System.out.print(Colors.getBLUE());
        char caseToken = scanner.next().charAt(0);
        System.out.print(Colors.getRESET());
        switch (caseToken) {
            case '0':
                stuMenu();
                break;
            default:
                System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                showWeeklySchedule(courses);
                break;
        }

    }

}
