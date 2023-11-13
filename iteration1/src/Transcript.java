import java.util.HashMap;
import java.util.Scanner;

public class Transcript {
	private HashMap<Integer, Double> yano;
	private HashMap<Integer, Double> gano;
	private HashMap<Integer, Course> courses;
	private HashMap<Integer, Integer> takenCredit;
	private HashMap<Integer, Integer> completedCredit;
	private Student student;
	private Scanner scanner;
	
	public Transcript(Student student) {
		this.yano = new HashMap<>();
		this.gano = new HashMap<>();
		this.courses = new HashMap<>();
		this.takenCredit = new HashMap<>();
		this.completedCredit = new HashMap<>();
		this.student = student;
		this.scanner = new Scanner(System.in);
	}
	
}