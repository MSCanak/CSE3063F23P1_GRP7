from course import Course
from person import Person
from transcript import Transcript


class Student(Person):
    def __init__(
        self,
        name,
        surname,
        email,
        phone_number,
        id,
        password,
        faculty,
        department,
        current_semester,
        advisor,
    ):
        super().__init__(
            name, surname, email, phone_number, id, password, faculty, department
        )
        self.__advisor = advisor
        self.__transcript = Transcript(self)
        self.__current_semester = current_semester
        self.__current_taken_courses: list[Course] = []

    def get_transcript(self) -> Transcript:
        return self.__transcript

    def set_transcript(self, transcript):
        self.__transcript = transcript

    def get_advisor(self):
        return self.__advisor

    def set_advisor(self, advisor):
        self.__advisor = advisor

    def get_current_semester(self) -> int:
        return self.__current_semester

    def set_current_semester(self, current_semester):
        self.__current_semester = current_semester

    def get_current_taken_courses(self) -> list[Course]:
        return self.__current_taken_courses

    def set_current_taken_courses(self, course):
        self.__current_taken_courses.append(course)
