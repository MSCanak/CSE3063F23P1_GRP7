import java.util.ArrayList;

public class CourseSession {

    private ArrayList<String> courseDay;
    private ArrayList<String> courseStartTime;
    private ArrayList<String> courseEndTime;
    private ArrayList<String> coursePlace;

    public CourseSession(ArrayList<String> courseDay, ArrayList<String> courseStartTime, ArrayList<String> courseEndTime, ArrayList<String> coursePlace) {
        this.courseDay = courseDay;
        this.courseStartTime = courseStartTime;
        this.courseEndTime = courseEndTime;
        this.coursePlace = coursePlace;
    }

    public ArrayList<String> getCourseDay() {
        return courseDay;
    }

    public void setCourseDay(ArrayList<String> courseDay) {
        this.courseDay = courseDay;
    }

    public ArrayList<String> getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(ArrayList<String> courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public ArrayList<String> getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(ArrayList<String> courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public ArrayList<String> getCoursePlace() {
        return coursePlace;
    }

    public void setCoursePlace(ArrayList<String> coursePlace) {
        this.coursePlace = coursePlace;
    }
}