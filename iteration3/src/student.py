from course import Course
from person import Person
from transcript import Transcript


class Student(Person):
    def __init__(
        self,
        name,
        surname,
        email,
        phoneNumber,
        ID,
        password,
        faculty,
        department,
        currentSemester,
        advisor,
    ):
        super().__init__(
            name, surname, email, phoneNumber, ID, password, faculty, department
        )
        self.__advisor = advisor
        self.__transcript = Transcript(self)
        self.__currentSemester = currentSemester
        self.__currentTakenCourses: list[Course] = []

    def get_transcript(self) -> Transcript:
        return self.__transcript

    def set_transcript(self, transcript):
        self.__transcript = transcript

    def get_advisor(self):
        return self.__advisor

    def set_advisor(self, advisor):
        self.__advisor = advisor

    def get_current_semester(self) -> int:
        return self.__currentSemester

    def set_current_semester(self, currentSemester):
        self.__currentSemester = currentSemester

    def get_current_taken_courses(self) -> list[Course]:
        return self.__currentTakenCourses

    def set_current_taken_courses(self, course):
        self.__currentTakenCourses.append(course)
