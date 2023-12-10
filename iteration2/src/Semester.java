import java.util.ArrayList;

public class Semester {

    private int year;
    private int takenCredit;
    private int completedCredit;
    private String term;
    private double yano;
    private ArrayList<Course> courses;

    public Semester(int year, String term, ArrayList<Course> courses) {
        this.year = year;
        this.term = term;
        this.courses = courses;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public void setTakenCredit(int takenCredit) {
        this.takenCredit = takenCredit;
    }

    public void setCompletedCredit(int completedCredit) {
        this.completedCredit = completedCredit;
    }

    public void setYano(double yano) {
        this.yano = yano;
    }
}
