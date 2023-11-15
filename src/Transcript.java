import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Transcript {
	
	// first argument for semester value
	private HashMap<Integer, Double> yano;
	private HashMap<Integer, Double> gano;
	private HashMap<Integer, ArrayList<String>> courses;
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

	// initialize YANO from studentID.json
    private void initializeYanoAndGano() {
        String filePath = "../jsons/student/" + student.getID() + ".json";
        JSONParser parser = new JSONParser();

        try {
            // read student json file
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject studentJson = (JSONObject) obj;
            
            // get transcript attribute then obtain the array of courses inside
            JSONObject transcript = (JSONObject) studentJson.get("Transcript");
            JSONArray arrayOfSemesters = (JSONArray) transcript.get("Semester");

            // fetch each semester's yano
            for (int i = 0; i < arrayOfSemesters.size(); i++) {
                JSONObject semester = (JSONObject) arrayOfSemesters.get(i);
                Double yanoVal = (Double) semester.get("Yano");
                Double ganoVal = (Double) semester.get("Gano");
                yano.put(i + 1, yanoVal);
                gano.put(i + 1, ganoVal);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    
	// find json file with the corresponding studentId to parse - debug
    public void readAndParseStudentJson() {
        String filePath = "../jsons/student/" + student.getID() + ".json";
        JSONParser parser = new JSONParser();

        try {
            // read json
            Object obj = parser.parse(new FileReader(filePath));
            // parse json
            JSONObject jsonObject = (JSONObject) obj;
            String studentName = (String) jsonObject.get("Name");
            String studentSurname = (String) jsonObject.get("Surname");

            // Print or use the retrieved information as needed
            System.out.println("Student Name: " + studentName);
            System.out.println("Student Surname: " + studentSurname);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
	
	public HashMap<Integer, Double> getYano() {
		return yano;
	}

	public void setYano(HashMap<Integer, Double> yano) {
		this.yano = yano;
	}

	public HashMap<Integer, Double> getGano() {
		return gano;
	}

	public void setGano(HashMap<Integer, Double> gano) {
		this.gano = gano;
	}

	public HashMap<Integer, ArrayList<String>> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<Integer, ArrayList<String>> courses) {
		this.courses = courses;
	}

	public HashMap<Integer, Integer> getTakenCredit() {
		return takenCredit;
	}

	public void setTakenCredit(HashMap<Integer, Integer> takenCredit) {
		this.takenCredit = takenCredit;
	}

	public HashMap<Integer, Integer> getCompletedCredit() {
		return completedCredit;
	}

	public void setCompletedCredit(HashMap<Integer, Integer> completedCredit) {
		this.completedCredit = completedCredit;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}