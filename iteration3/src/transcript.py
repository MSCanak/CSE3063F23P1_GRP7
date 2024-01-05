import json
from typing import List

from colors import Colors
from semester import Semester
from course import Course


class Transcript:
    def __init__(self, student):
        # private attributes
        self.__student = student
        self.__gano: List[float] = []
        self.__semesters: List[Semester] = []
        self.__colors = Colors()

        # initialization method
        self.__create_transcript()

    def __create_transcript(self):
        # helper methods to initialize semesters and GANO list
        self.__initialize_semesters()
        self.__initialize_gano_list()

    def __initialize_semesters(self):
        # read and initialize semester information from JSON file
        file_path = f"./jsons/student/{self.__student.get_id()}.json"

        try:
            with open(file_path, "r") as file:
                student_json = json.load(file)
                transcript = student_json.get("Transcript", {})
                array_of_semesters = transcript.get("Semester", [])

                for semester_json in array_of_semesters:
                    courses_array = semester_json.get("Courses", [])
                    semester_courses = []

                    for json_course in courses_array:
                        # extracting course information from JSON
                        course_name = json_course.get("CourseName", "")
                        course_id = json_course.get("CourseID", "")
                        credit = int(json_course.get("Credit", 0))
                        course_type = json_course.get("CourseType", "")
                        semester_val = int(json_course.get("Semester", 0))
                        grade = float(json_course.get("Grade", 0.0))

                        # constructing Course objects
                        course = Course(
                            course_name,
                            course_id,
                            credit,
                            course_type,
                            semester_val,
                            grade,
                        )
                        semester_courses.append(course)

                    # creating Semester objects
                    semester = Semester(semester_courses)

                    if "SemesterInf" in semester_json:
                        semester_inf = semester_json["SemesterInf"]
                        semester.set_taken_credit(
                            int(semester_inf.get("TakenCredit", 0))
                        )
                        semester.set_completed_credit(
                            int(semester_inf.get("CompletedCredit", 0))
                        )
                        semester.set_yano(float(semester_inf.get("Yano", 0.0)))

                    self.__semesters.append(semester)

        except Exception as e:
            print(f"Error: {e}")

    def __initialize_gano_list(self):
        # helper method to initialize GANO list from JSON file
        file_path = f"./jsons/student/{self.__student.get_id()}.json"

        try:
            with open(file_path, "r") as file:
                student_json = json.load(file)
                transcript = student_json.get("Transcript", {})
                array_of_semesters = transcript.get("Semester", [])

                for semester in array_of_semesters:
                    if "SemesterInf" in semester:
                        semester_inf = semester["SemesterInf"]

                        if "Gano" in semester_inf:
                            # Extracting GANO values
                            gano_val = float(semester_inf["Gano"])
                            self.__gano.append(gano_val)

        except Exception as e:
            print(f"Error: {e}")

    def view_transcript(self):
        # method to display the transcript information
        print(
            self.__colors.get_red()
            + self.__colors.get_bold()
            + "\n>> Transcript\n"
            + self.__colors.get_reset()
            + self.__colors.get_reset()
        )

        print(
            f"{self.__colors.get_green()}Student ID: {self.__student.get_id()}\n"
            f"Name and Surname: {self.__student.get_name()} {self.__student.get_surname()}{self.__colors.get_reset()}"
        )

        for i, semester in enumerate(self.__semesters):
            # displaying information for each semester
            print(
                "--------------------------------------------------------------------------------------------------------------"
            )
            print(
                f"{self.__colors.get_blue()}Semester {i + 1}{self.__colors.get_reset()}"
            )
            print(self.__colors.get_yellow())
            print("{:<10}{:<15}{:<70}{:<10}{:<10}".format("","Course Code", "Course Name", "Credit", "Grade"))

            print(self.__colors.get_reset())

            for course in semester.get_courses():
                # displaying course information
                print("{:<10}{:<15}{:<70}{:<10}{:<10}".format(
                    "",
                    course.get_course_id(),
                    course.get_course_name(),
                    course.get_credit(),
                    course.get_grade(),
                ))
            
            print()


            taken_credit = semester.get_taken_credit()
            if taken_credit != 0:
                print(
                    f"{self.__colors.get_yellow()}Taken Credit: {self.__colors.get_reset()}{taken_credit}"
                )

            print(
                f"{self.__colors.get_yellow()}Completed Credit: {self.__colors.get_reset()}"
                f"{semester.get_completed_credit() if semester.get_completed_credit() != 0 else 'Semester has not been completed yet'}"
            )

            print(
                f"{self.__colors.get_yellow()}Yano: {self.__colors.get_reset()}"
                f"{f'{semester.get_yano():.2f}' if semester.get_yano() != 0.0 else 'Semester has not been completed yet'}"
            )

            gano_index = min(i, len(self.__gano) - 1)
            current_gano = self.__gano[gano_index] if gano_index >= 0 else 0.0

            if current_gano != 0.0:
                print(
                    f"{self.__colors.get_yellow()}Gano: {self.__colors.get_reset()}{current_gano:.2f}"
                )
            else:
                print("Gano: N/A")

           

    def set_gano(self, gano: List[float]):
        self.__gano = gano

    def get_gano(self) -> List[float]:
        return self.__gano

    def get_semesters(self) -> List[Semester]:
        return self.__semesters

    def set_semesters(self, semesters: List[Semester]):
        self.__semesters = semesters
