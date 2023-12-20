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
        while (true) {
            System.out.println(Colors.getRED() + Colors.getBOLD() + "\n> Student Menu\n" + Colors.getRESET());
            System.out.println(Colors.getYELLOW() + "1" + Colors.getRESET() + ".   View Notifications");
            System.out.println(Colors.getYELLOW() + "2" + Colors.getRESET() + ".   View Weekly Schedule");
            System.out.println(Colors.getYELLOW() + "3" + Colors.getRESET() + ".   View Transcript");
            System.out.println(Colors.getYELLOW() + "4" + Colors.getRESET() + ".   View Curriculum");
            System.out.println(Colors.getYELLOW() + "5" + Colors.getRESET() + ".   Go to Course Registration System");

            System.out.println(Colors.getYELLOW() + "*" + Colors.getRESET() + ".   Logout");
            System.out.println(Colors.getYELLOW() + "x" + Colors.getRESET() + ".   Exit");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            input = new Scanner(System.in);
            System.out.print(Colors.getRESET());

            // get user choice
            String choiceLine = input.nextLine();
            System.out.print(Colors.getBLUE());
            if (choiceLine.length() > 1) {
                System.out.println(
                        Colors.getYELLOW() + "Invalid input format! Please give a number!" + Colors.getRESET());
                continue;
            }
            char choice = choiceLine.charAt(0);

            System.out.print(Colors.getRESET());

            switch (choice) {

                case '1': // viewing notifications
                    showNotificatons();
                    break;

                case '2': // viewing weekly schedule
                    ArrayList<Course> courses = calculateWeeklySchedule();
                    System.out.println(Colors.getRED() + Colors.getBOLD() + "\n>> Weekly Schedule\n" + Colors.getRESET()
                            + Colors.getRESET());
                    if (courses.size() == 0) {
                        System.out.println(Colors.getYELLOW() + "You have no courses for this semester!\n"
                                + Colors.getRESET());
                        while (true) {
                            System.out.println(
                                    Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Student Menu.");
                            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET()
                                    + "What do you want to do?   ");
                            System.out.print(Colors.getBLUE());

                            String backChoiceLine = input.nextLine();
                            System.out.print(Colors.getRESET());

                            if (backChoiceLine.length() > 1) {
                                System.out.println(Colors.getYELLOW() + "Invalid input! Please give a number!"
                                        + Colors.getRESET());
                                continue;
                            }
                            char backChoice = backChoiceLine.charAt(0);
                            if (backChoice == '0') {
                                break;
                            } else {
                                System.out.println(
                                        Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                            }
                        }
                    } else {
                        showWeeklySchedule(courses);
                    }
                    break;

                case '3': // viewing transcript
                    ((Student) session.getUser()).getTranscript().viewTranscript();
                    boolean a = true;
                    while (a) {
                        System.out.println(
                                "-------------------------------------------------------------------------------------------------------------\n");

                        System.out.println(
                                Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Go back to the Student Menu.");
                        System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET()
                                + "What do you want to do?   ");
                        System.out.print(Colors.getBLUE());
                        String backChoiceLine = input.nextLine();
                        System.out.print(Colors.getRESET());
                        if (backChoiceLine.length() > 1) {
                            System.out.println(Colors.getYELLOW() + "Invalid input! Please give a number!"
                                    + Colors.getRESET());
                            continue;
                        }
                        char backChoice = backChoiceLine.charAt(0);
                        if (backChoice == '0') {
                            break;
                        } else {
                            System.out.println(
                                    Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                        }
                    }
                    break;
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
                                    + Colors.getRESET() + Colors.getRESET());
                    loginInterface.exit();
                    break;

                // invalid input
                default:
                    System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                    continue;
            }
        }

    }

    private void showCurriculum() throws FileNotFoundException, IOException, ParseException {
        Object coursesJSONobj;
        JSONArray courseJSONarr;
        coursesJSONobj = new JSONParser().parse(new FileReader("./jsons/courses.json"));

        courseJSONarr = (JSONArray) coursesJSONobj;
        System.out.println(Colors.getRED() + Colors.getBOLD() + "\n>> Curriculum\n" + Colors.getRESET()+Colors.getRESET());

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
                System.out.printf("%sSemester %s%s%n", Colors.getBLUE(), Semester, Colors.getRESET());
                System.out.printf("%n\t%s%-10s%-58s%-15s%-8s%-25s%-25s%s%n%n", Colors.getYELLOW(),
                        "CourseID", "CourseName", "Type", "Credit",
                        "OptionalPrerequisites", "MandatoryPrerequisites", Colors.getRESET());
                previousSemester = (int) Semester;
            }

            System.out.printf("\t\t%-10s%-58s%-15s%-8s%-25s%-25s%n",
                    CourseID, CourseName, Type, Credit,
                    optionalPrerequisitesString, mandatoryPrerequisitesString);

        }
        boolean a = true;
        while (a) {
            System.out.printf(
                    "%n-----------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
            System.out.println(Colors.getYELLOW() + "\n0" + Colors.getRESET() + ".  Go back to the Student Menu.");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET()
                    + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            input = new Scanner(System.in);
            System.out.print(Colors.getRESET());
            String backChoiceLine = input.nextLine();
            if (backChoiceLine.length() > 1) {
                System.out.println(
                        Colors.getYELLOW() + "Invalid input! Please give a number!" + Colors.getRESET());
                continue;
            }
            char backChoice = backChoiceLine.charAt(0);
            if (backChoice == '0') {
                return;
            } else {
                System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                continue;
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

        System.out.println(Colors.getRED() + Colors.getBOLD()
                + "\n>> Weekly Schedule\n"
                + Colors.getRESET() + Colors.getRESET());
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

        CourseSessionTimes courseSessionTimes = new CourseSessionTimes();

        for (int k = 0; k < courseSessionTimes.SESSION_START.size(); k++) {

            mondayCourses = weeklySchedule.printMondayCourses(mondayCoursesID, mondayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            tuesdayCourses = weeklySchedule.printTuesdayCourses(tuesdayCoursesID, tuesdayCoursesStartTime,
                    courseSessionTimes.SESSION_START,
                    k);
            wednesdayCourses = weeklySchedule.printWednesdayCourses(wednesdayCoursesID, wednesdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            thursdayCourses = weeklySchedule.printThursdayCourses(thursdayCoursesID, thursdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            fridayCourses = weeklySchedule.printFridayCourses(fridayCoursesID, fridayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            saturdayCourses = weeklySchedule.printSaturdayCourses(saturdayCoursesID, saturdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            sundayCourses = weeklySchedule.printSundayCourses(sundayCoursesID, sundayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);

            mondayCoursePlace = weeklySchedule.printMondayCoursePlace(mondayCoursesPlace, mondayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            tuesdayCoursePlace = weeklySchedule.printTuesdayCoursePlace(tuesdayCoursesPlace, tuesdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            wednesdayCoursePlace = weeklySchedule.printWednesdayCoursePlace(wednesdayCoursesPlace,
                    wednesdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            thursdayCoursePlace = weeklySchedule.printThursdayCoursePlace(thursdayCoursesPlace,
                    thursdayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            fridayCoursePlace = weeklySchedule.printFridayCoursePlace(fridayCoursesPlace, fridayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);
            saturdayCoursePlace = weeklySchedule.printSaturdayCoursePlace(saturdayCoursesPlace,
                    saturdayCoursesStartTime,
                    courseSessionTimes.SESSION_START,
                    k);
            sundayCoursePlace = weeklySchedule.printSundayCoursePlace(sundayCoursesPlace, sundayCoursesStartTime,
                    courseSessionTimes.SESSION_START, k);

            if (mondayCourses != "" || tuesdayCourses != "" || wednesdayCourses != "" || thursdayCourses != ""
                    || fridayCourses != "" || saturdayCourses != "" || sundayCourses != "") {
                System.out.println(
                        "------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.printf("|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n",
                        "", mondayCourses,
                        tuesdayCourses, wednesdayCourses, thursdayCourses, fridayCourses, saturdayCourses,
                        sundayCourses);
                System.out.printf("|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n",
                        courseSessionTimes.SESSION_START.get(k) + " - " + courseSessionTimes.SESSION_END.get(k),
                        mondayCoursePlace, tuesdayCoursePlace, wednesdayCoursePlace, thursdayCoursePlace,
                        fridayCoursePlace, saturdayCoursePlace, sundayCoursePlace);
            }

        }
        System.out.println(
                "------------------------------------------------------------------------------------------------------------------------------------------\n");

        while (true) {
            System.out.println(Colors.getYELLOW() + "0" + Colors.getRESET() + ".  Back to Student Menu");
            System.out.print("\n" + Colors.getBLUE() + "--> " + Colors.getRESET() + "What do you want to do?   ");
            System.out.print(Colors.getBLUE());
            String caseTokenLine = scanner.nextLine();
            if (caseTokenLine.length() > 1) {
                System.out.println(
                        Colors.getYELLOW() + "Invalid input format! Please give a number!" + Colors.getRESET());
                continue;

            }
            char caseToken = caseTokenLine.charAt(0);
            System.out.print(Colors.getRESET());
            switch (caseToken) {
                case '0':
                    return;
                default:
                    System.out.println(Colors.getYELLOW() + "Invalid input! Please try again." + Colors.getRESET());
                    continue;
            }
        }

    }

}
