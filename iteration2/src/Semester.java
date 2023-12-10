import java.util.ArrayList;

public class Semester {

    private int takenCredit;
    private int completedCredit;
    private double yano;
    private ArrayList<Course> courses;

    public Semester(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public int getTakenCredit() {
        return takenCredit;
    }

    public void setTakenCredit(int takenCredit) {
        this.takenCredit = takenCredit;
    }

    public int getCompletedCredit() {
        return completedCredit;
    }

    public void setCompletedCredit(int completedCredit) {
        this.completedCredit = completedCredit;
    }

    public double getYano() {
        return yano;
    }

    public void setYano(double yano) {
        this.yano = yano;
    }
}
