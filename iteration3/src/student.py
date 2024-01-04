from person import Person
from transcript import Transcript

class Student(Person):
    def __init__(self, name, surname, email, phoneNumber, ID, password, faculty, department, currentSemester, advisor):
        super().__init__(name, surname, email, phoneNumber, ID, password, faculty, department)
        self.advisor = advisor
        self.transcript = Transcript(self)
        self.currentSemester = currentSemester
        self.currentTakenCourses = []

    def get_transcript(self):
        return self.transcript

    def set_transcript(self, transcript):
        self.transcript = transcript

    def get_advisor(self):
        return self.advisor

    def set_advisor(self, advisor):
        self.advisor = advisor

    def get_current_semester(self):
        return self.currentSemester

    def set_current_semester(self, currentSemester):
        self.currentSemester = currentSemester

    def get_current_taken_courses(self):
        return self.currentTakenCourses

    def set_current_taken_courses(self, course):
        self.currentTakenCourses.append(course)
