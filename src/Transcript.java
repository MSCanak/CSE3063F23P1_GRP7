import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

	public Transcript(Student student) {
		this.yano = new HashMap<>();
		this.gano = new HashMap<>();
		this.courses = new HashMap<>();
		this.takenCredit = new HashMap<>();
		this.completedCredit = new HashMap<>();
		this.student = student;

		createTranscript();
	}

	private void createTranscript() {
		initializeYanoAndGano();
		initializeTakenAndCompletedCredit();
		initializeAndConstructCourses();
	}

	private void initializeAndConstructCourses() {
		String filePath = "./jsons/student/" + student.getID() + ".json";
		JSONParser parser = new JSONParser();

		try {
			// read student json file
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject studentJson = (JSONObject) obj;
			// get transcript attribute then obtain the array of courses inside
			JSONObject transcript = (JSONObject) studentJson.get("Transcript");
			JSONArray arrayOfSemesters = (JSONArray) transcript.get("Semester");

			// fetch each semester's courses
			for (int i = 0; i < arrayOfSemesters.size(); i++) {
				// get current semester with its corresponding courses
				JSONObject semester = (JSONObject) arrayOfSemesters.get(i);
				JSONArray coursesArray = (JSONArray) semester.get("Courses");

				ArrayList<Course> semesterCourses = new ArrayList<>();
				for (int j = 0; j < coursesArray.size(); j++) {
					JSONObject jsonCourse = (JSONObject) coursesArray.get(j);

					// variables to create course objects
					// get method in json-simple returns object as an Object needs a cast
					String courseName = (String) jsonCourse.get("CourseName");
					String courseID = (String) jsonCourse.get("CourseID");
					int credit = ((Number) jsonCourse.get("Credit")).intValue();
					String type = (String) jsonCourse.get("CourseType");
					int semesterVal = ((Number) jsonCourse.get("Semester")).intValue();
					double grade = ((Number) jsonCourse.get("Grade")).doubleValue();

					Course course = new Course(courseName, courseID, credit, type, semesterVal, grade);
					semesterCourses.add(course);
				}
				courses.put(i + 1, semesterCourses);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
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

			// fetch each semester's completed and taken credits
			for (int i = 0; i < arrayOfSemesters.size(); i++) {
				JSONObject semester = (JSONObject) arrayOfSemesters.get(i);
				Integer takenCreditVal = ((Number) ((JSONObject) semester.get("SemesterInf")).get("TakenCredit"))
						.intValue();
				Integer completedCreditVal = ((Number) ((JSONObject) semester.get("SemesterInf"))
						.get("CompletedCredit")).intValue();
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

	public void viewTranscript() {
		System.out.printf("%nStudent ID: %s\nName and Surname: %s%n", student.getID(),
				student.getName() + " " + student.getSurname());
		for (int i = 1; i <= student.getSemester() + 1; i++) {

			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");
			System.out.println("Semester " + i + "\n");
			System.out.printf("\t%-15s%-70s%-10s%-10s%n%n", "Course Code", "Course Name", "Credit", "Grade");
			ArrayList<Course> coursesForPrinting = courses.get(i);
			for (int j = 0; j < coursesForPrinting.size(); j++) {
				System.out.printf("\t%-15s%-70s%-10s%-10s%n", coursesForPrinting.get(j).getCourseID(),
						coursesForPrinting.get(j).getCourseName(), coursesForPrinting.get(j).getCredit(),
						coursesForPrinting.get(j).getGrade());
			}
			System.out.println("\nTaken Credit: " + takenCredit.get(i));
			System.out.println("Completed Credit: " + completedCredit.get(i));
			System.out.println("Yano: " + String.format("%.2f", yano.get(i)));
			System.out.println("Gano: " + String.format("%.2f", gano.get(i)));
			System.out.println();
		}
	}

	// find json file with the corresponding studentId to parse - debug0

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