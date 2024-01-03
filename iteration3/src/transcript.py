import json
from course import Course
from semester import Semester
from colors import Colors

class Transcript:
    def __init__(self, student):
        self.student = student
        self.gano = []
        self.semesters = []
        self.colors = Colors()

        # create the transcript during initialization
        self.create_transcript()

    def create_transcript(self):
        try:
            # initialize semesters and GANO list
            self.initialize_semesters()
            self.initialize_gano_list()
        except Exception as e:
            print(f"Error creating transcript: {e}")

    def initialize_semesters(self):
        # path for the student's transcript JSON file
        file_path = f"./jsons/student/{self.student.get_id()}.json"

        try:
            # read the JSON file
            with open(file_path, 'r') as file:
                student_json = json.load(file)
                transcript = student_json["Transcript"]
                array_of_semesters = transcript["Semester"]

                # iterate through semesters in the JSON file
                for semester_json in array_of_semesters:
                    # extract course information for each semester
                    courses_array = semester_json["Courses"]
                    semester_courses = []

                    # iterate through courses for each semester
                    for json_course in courses_array:
                        try:
                            # construct a Course object from JSON data and add it to the semester
                            course = self.create_course_from_json(json_course)
                            semester_courses.append(course)
                        except Exception as e:
                            print(f"Error creating course: {e}")

                    # construct a Semester object and set additional information
                    semester = Semester(semester_courses)
                    if "SemesterInf" in semester_json:
                        semester_inf = semester_json["SemesterInf"]
                        semester.set_taken_credit(
                            semester_inf.get("TakenCredit", 0))
                        semester.set_completed_credit(
                            semester_inf.get("CompletedCredit", 0))
                        semester.set_yano(semester_inf.get("Yano", 0.0))

                    # add the Semester object to the list of semesters
                    self.semesters.append(semester)

        except Exception as e:
            print(f"Error initializing semesters: {e}")

    def initialize_gano_list(self):
        # File path for the student's transcript JSON file
        file_path = f"./jsons/student/{self.student.get_id()}.json"

        try:
            # Open and read the JSON file
            with open(file_path, 'r') as file:
                student_json = json.load(file)
                transcript = student_json["Transcript"]
                array_of_semesters = transcript["Semester"]

                # Iterate through semesters in the JSON file
                for semester in array_of_semesters:
                    # Check if "SemesterInf" and "Gano" are present
                    if "SemesterInf" in semester:
                        semester_inf = semester["SemesterInf"]
                        if "Gano" in semester_inf:
                            try:
                                # Add GANO values to the GANO list
                                gano_val = semester_inf["Gano"]
                                self.gano.append(gano_val)
                            except Exception as e:
                                print(f"Error adding Gano: {e}")

        except Exception as e:
            print(f"Error initializing Gano list: {e}")

    def view_transcript(self):
        try:
            # Print header for the transcript
            print(
                f"{self.colors.get_red()}{self.colors.get_bold()}\n>> Transcript\n{self.colors.get_reset()}{self.colors.get_reset()}")

            # Print student information
            print(f"{self.colors.get_green()}Student ID: {self.colors.get_reset()}{self.student.get_id()}\n"
                  f"{self.colors.get_green()}Name and Surname: {self.colors.get_reset()}{self.student.get_name()} {self.student.get_surname()}\n")

            # Iterate through semesters and print information for each
            for i, current_semester in enumerate(self.semesters):
                print(
                    "-------------------------------------------------------------------------------------------------------------")
                print(
                    f"{self.colors.get_blue()}Semester {i + 1}\n{self.colors.get_reset()}")
                print(
                    f"{self.colors.get_yellow()}\t%-15s%-70s%-10s%-10s%n%n" % ("Course Code", "Course Name", "Credit",
                                                                               "Grade"))
                print(f"{self.colors.get_reset()}")

                # Print course information for the current semester
                for course in current_semester.get_courses():
                    print(
                        f"{self.colors.get_yellow()}\t%-15s%-70s%-10s%-10s%n" % (
                            course.get_course_id(), course.get_course_name(), course.get_credit(), course.get_grade()))

                print()

                # Print additional information for the current semester
                taken_credit = current_semester.get_taken_credit()
                if taken_credit != 0:
                    print(
                        f"{self.colors.get_yellow()}Taken Credit: {self.colors.get_reset()}{taken_credit}")

                print(
                    f"{self.colors.get_yellow()}Completed Credit: {self.colors.get_reset()}"
                    f"{current_semester.get_completed_credit() if current_semester.get_completed_credit() != 0 else 'Semester has not been completed yet'}")

                print(
                    f"{self.colors.get_yellow()}Yano: {self.colors.get_reset()}"
                    f"{current_semester.get_yano() if current_semester.get_yano() != 0.0 else 'Semester has not been completed yet'}")

                # Retrieve GANO values for the current semester
                gano_index = min(i, len(self.gano) - 1)
                current_gano = self.gano[gano_index] if gano_index >= 0 else 0.0

                # Print GANO information for the current semester
                if current_gano != 0.0:
                    print(
                        f"{self.colors.get_yellow()}Gano: {self.colors.get_reset()}{current_gano:.2f}")
                else:
                    print("Gano: N/A")

                print()

        except Exception as e:
            print(f"Error viewing transcript: {e}")

    def set_gano(self, gano):
        self.gano = gano

    def get_gano(self):
        return self.gano

    def get_semesters(self):
        return self.semesters

    def set_semesters(self, semesters):
        self.semesters = semesters

    def create_course_from_json(self, json_course):
        try:
            # Extract course information from JSON
            course_name = json_course.get("CourseName", "")
            course_id = json_course.get("CourseID", "")
            credit = json_course.get("Credit", 0)
            course_type = json_course.get("CourseType", "")
            semester_val = json_course.get("Semester", 0)
            grade = json_course.get("Grade", 0.0)

            # Create and return a Course object
            return Course(course_name, course_id, credit, course_type, semester_val, grade)

        except Exception as e:
            raise ValueError(f"Error creating course from JSON: {e}")
