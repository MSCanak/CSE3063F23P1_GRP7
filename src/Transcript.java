import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Transcript {

	// first argument for semester value
	private HashMap<Integer, Double> yano;
	private HashMap<Integer, Double> gano;
	private HashMap<Integer, ArrayList<Course>> courses;
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
		
		createTranscript();
	}

	// 
	private void createTranscript() {
		initializeYanoAndGano();
		initializeTakenAndCompletedCredit();
		
		// debug
		System.out.println("Yano HashMap:");

		for (Entry<Integer, Double> entry : yano.entrySet()) {
			System.out.println("Semester " + entry.getKey() + ": " + entry.getValue());
		}

		// debug
		System.out.println("Gano HashMap:");

		for (Entry<Integer, Double> entry : gano.entrySet()) {
			System.out.println("Semester " + entry.getKey() + ": " + entry.getValue());
		}
		
		
		// debug
		System.out.println("Taken HashMap:");

		for (Entry<Integer, Integer> entry : takenCredit.entrySet()) {
			System.out.println("Semester " + entry.getKey() + ": " + entry.getValue());
		}
		
		// debug
		System.out.println("Completed HashMap:");

		for (Entry<Integer, Integer> entry : completedCredit.entrySet()) {
			System.out.println("Semester " + entry.getKey() + ": " + entry.getValue());
		}
	}
	
	private void initializeTakenAndCompletedCredit() {
		String filePath = "./jsons/student/" + student.getID() + ".json";
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
				Integer takenCreditVal = ((Number) ((JSONObject) semester.get("SemesterInf")).get("TakenCredit")).intValue();
				Integer completedCreditVal = ((Number) ((JSONObject) semester.get("SemesterInf")).get("CompletedCredit")).intValue();
				System.out.println(takenCreditVal);
				takenCredit.put(i + 1, takenCreditVal);
				completedCredit.put(i + 1, completedCreditVal);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	// initialize YANO and GANO from studentID.json
	private void initializeYanoAndGano() {
		String filePath = "./jsons/student/" + student.getID() + ".json";
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
				// since the its a long value cast to number then get double value
				Double yanoVal = ((Number) ((JSONObject) semester.get("SemesterInf")).get("Yano")).doubleValue();
				Double ganoVal = ((Number) ((JSONObject) semester.get("SemesterInf")).get("Gano")).doubleValue();
				yano.put(i + 1, yanoVal);
				gano.put(i + 1, ganoVal);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	// find json file with the corresponding studentId to parse - debug
	public void readAndParseStudentJson() {
		String filePath = "./jsons/student/" + student.getID() + ".json";
		JSONParser parser = new JSONParser();

		try {
			// read json
			Object obj = parser.parse(new FileReader(filePath));
			// parse json
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("Name");
			String surname = (String) jsonObject.get("Surname");

			// Print or use the retrieved information as needed
			System.out.println("Student Name: " + name);
			System.out.println("Student Surname: " + surname);

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

	public HashMap<Integer, ArrayList<Course>> getCourses() {
		return courses;
	}

	public void setCourses(HashMap<Integer, ArrayList<Course>> courses) {
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