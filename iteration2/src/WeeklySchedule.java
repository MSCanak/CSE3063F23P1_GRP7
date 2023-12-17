import java.util.ArrayList;

public class WeeklySchedule {

    // printing course id
    public String printMondayCourses(ArrayList<String> mondayCousesID, ArrayList<String> mondayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String mondayCourses = "";
        for (int i = 0; i < mondayCousesID.size(); i++) {
            if (mondayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                mondayCourses = mondayCousesID.get(i);
            }
        }
        return mondayCourses;

    }

    public String printTuesdayCourses(ArrayList<String> tuesdayCousesID, ArrayList<String> tuesdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String tuesdayCourses = "";
        for (int i = 0; i < tuesdayCousesID.size(); i++) {
            if (tuesdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                tuesdayCourses = tuesdayCousesID.get(i);
            }
        }
        return tuesdayCourses;

    }

    public String printWednesdayCourses(ArrayList<String> wednesdayCousesID,
            ArrayList<String> wednesdayCoursesStartTime, ArrayList<String> SESSION_START, int k) {
        String wednesdayCourses = "";
        for (int i = 0; i < wednesdayCousesID.size(); i++) {
            if (wednesdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                wednesdayCourses = wednesdayCousesID.get(i);
            }
        }
        return wednesdayCourses;

    }

    public String printThursdayCourses(ArrayList<String> thursdayCousesID, ArrayList<String> thursdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String thursdayCourses = "";
        for (int i = 0; i < thursdayCousesID.size(); i++) {
            if (thursdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                thursdayCourses = thursdayCousesID.get(i);
            }
        }
        return thursdayCourses;

    }

    public String printFridayCourses(ArrayList<String> fridayCousesID, ArrayList<String> fridayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String fridayCourses = "";
        for (int i = 0; i < fridayCousesID.size(); i++) {
            if (fridayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                fridayCourses = fridayCousesID.get(i);
            }
        }
        return fridayCourses;

    }

    public String printSaturdayCourses(ArrayList<String> saturdayCousesID, ArrayList<String> saturdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String saturdayCourses = "";
        for (int i = 0; i < saturdayCousesID.size(); i++) {
            if (saturdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                saturdayCourses = saturdayCousesID.get(i);
            }
        }
        return saturdayCourses;

    }

    public String printSundayCourses(ArrayList<String> sundayCousesID, ArrayList<String> sundayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String sundayCourses = "";
        for (int i = 0; i < sundayCousesID.size(); i++) {
            if (sundayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                sundayCourses = sundayCousesID.get(i);
            }
        }
        return sundayCourses;

    }

    // printing course place
    public String printMondayCoursePlace(ArrayList<String> mondayCousesPlace, ArrayList<String> mondayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String mondayCoursePlace = "";
        for (int i = 0; i < mondayCousesPlace.size(); i++) {
            if (mondayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                mondayCoursePlace = mondayCousesPlace.get(i);
            }
        }
        return mondayCoursePlace;

    }

    public String printTuesdayCoursePlace(ArrayList<String> tuesdayCousesPlace,
            ArrayList<String> tuesdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String tuesdayCoursePlace = "";
        for (int i = 0; i < tuesdayCousesPlace.size(); i++) {
            if (tuesdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                tuesdayCoursePlace = tuesdayCousesPlace.get(i);
            }
        }
        return tuesdayCoursePlace;

    }

    public String printWednesdayCoursePlace(ArrayList<String> wednesdayCousesPlace,
            ArrayList<String> wednesdayCoursesStartTime, ArrayList<String> SESSION_START, int k) {
        String wednesdayCoursePlace = "";
        for (int i = 0; i < wednesdayCousesPlace.size(); i++) {
            if (wednesdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                wednesdayCoursePlace = wednesdayCousesPlace.get(i);
            }
        }
        return wednesdayCoursePlace;

    }

    public String printThursdayCoursePlace(ArrayList<String> thursdayCousesPlace,
            ArrayList<String> thursdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String thursdayCoursePlace = "";
        for (int i = 0; i < thursdayCousesPlace.size(); i++) {
            if (thursdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                thursdayCoursePlace = thursdayCousesPlace.get(i);
            }
        }
        return thursdayCoursePlace;

    }

    public String printFridayCoursePlace(ArrayList<String> fridayCousesPlace, ArrayList<String> fridayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String fridayCoursePlace = "";
        for (int i = 0; i < fridayCousesPlace.size(); i++) {
            if (fridayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                fridayCoursePlace = fridayCousesPlace.get(i);
            }
        }
        return fridayCoursePlace;

    }

    public String printSaturdayCoursePlace(ArrayList<String> saturdayCousesPlace,
            ArrayList<String> saturdayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String saturdayCoursePlace = "";
        for (int i = 0; i < saturdayCousesPlace.size(); i++) {
            if (saturdayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                saturdayCoursePlace += saturdayCousesPlace.get(i);
            }
        }
        return saturdayCoursePlace;

    }

    public String printSundayCoursePlace(ArrayList<String> sundayCousesPlace, ArrayList<String> sundayCoursesStartTime,
            ArrayList<String> SESSION_START, int k) {
        String sundayCoursePlace = "";
        for (int i = 0; i < sundayCousesPlace.size(); i++) {
            if (sundayCoursesStartTime.get(i).equals(SESSION_START.get(k))) {
                sundayCoursePlace += sundayCousesPlace.get(i);
            }
        }
        return sundayCoursePlace;

    }
}
