import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public class Transcript {

	private Student student;
	private ArrayList<Double> gano;
	private ArrayList<Semester> semesters;

	public Transcript(Student student) {
		this.student = student;
		this.gano = new ArrayList<Double>();
		this.semesters = new ArrayList<Semester>();

		createTranscript();
	}

	private void createTranscript() {
		initializeSemesters();
		initializeGanoList();
	}

	private void initializeSemesters() {
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
				JSONObject semesterJson = (JSONObject) arrayOfSemesters.get(i);
				JSONArray coursesArray = (JSONArray) semesterJson.get("Courses");
				ArrayList<Course> semesterCourses = new ArrayList<>();

				for (int j = 0; j < coursesArray.size(); j++) {
					JSONObject jsonCourse = (JSONObject) coursesArray.get(j);

					// variable parameters to create course objects
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

				// construct Semester objects and initialize other attributes
				Semester semester = new Semester(semesterCourses);

				// Check if "SemesterInf" is present
				if (semesterJson.containsKey("SemesterInf")) {
					// retrieve "SemesterInf" object
					JSONObject semesterInf = (JSONObject) semesterJson.get("SemesterInf");
	
					semester.setTakenCredit(((Number) semesterInf.get("TakenCredit")).intValue());
					semester.setCompletedCredit(((Number) semesterInf.get("CompletedCredit")).intValue());
					semester.setYano(((Number) semesterInf.get("Yano")).doubleValue());
				}
	
				semesters.add(semester);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	// initialize GANO from studentID.json
	private void initializeGanoList() {
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
				// check if null
				if (semester.containsKey("SemesterInf")) {
					JSONObject semesterInf = (JSONObject) semester.get("SemesterInf");
	
					if (semesterInf.containsKey("Gano")) {
						// since getting a value from JSON returns a long value, cast to number, then get double value
						Double ganoVal = ((Number) semesterInf.get("Gano")).doubleValue();
						gano.add(ganoVal);
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void viewTranscript() {
		System.out.printf("%nStudent ID: %s\nName and Surname: %s%n", student.getID(),
				student.getName() + " " + student.getSurname());
		for (int i = 0; i < semesters.size(); i++) {
			System.out.println(
					"-------------------------------------------------------------------------------------------------------------");
			System.out.println("Semester " + (i + 1) + "\n");
			System.out.printf("\t%-15s%-70s%-10s%-10s%n%n", "Course Code", "Course Name", "Credit", "Grade");
			Semester currentSemester = semesters.get(i);
            for (Course course : currentSemester.getCourses()) {
                System.out.printf("\t%-15s%-70s%-10s%-10s%n", course.getCourseID(),
                        course.getCourseName(), course.getCredit(),
                        course.getGrade());
            }
			System.out.println("\nTaken Credit: " + currentSemester.getTakenCredit());
			System.out.println("Completed Credit: " + currentSemester.getCompletedCredit());
			System.out.println("Yano: " + String.format("%.2f", currentSemester.getYano()));
			System.out.println("Gano: " + String.format("%.2f", gano.get(i)));
			System.out.println();
		}
	}

	public void setGano(ArrayList<Double> gano) {
		this.gano = gano;
	}

	public ArrayList<Double> getGano() {
		return gano;
	}

	public ArrayList<Semester> getSemesters() {
		return semesters;
	}

	public void setSemesters(ArrayList<Semester> semesters) {
		this.semesters = semesters;
	}
}