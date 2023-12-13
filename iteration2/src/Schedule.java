import java.util.ArrayList;

interface Schedule {
    // calculates the weekly schedule by reading json file
    public abstract ArrayList<Course> calculateWeeklySchedule();
    // shows the weekly schedule by printing courses arraylist
    public abstract void showWeeklySchedule(ArrayList<Course> courses);
}
