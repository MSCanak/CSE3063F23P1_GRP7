from person import Person
from transcript import Transcript

class Student(Person):
    def __init__(self, name, surname, email, phoneNumber, ID, password, faculty, department, currentSemester, advisor):
        super().__init__(name, surname, email, phoneNumber, ID, password, faculty, department)
        self.advisor = advisor
        self.transcript = Transcript(self)
        self.currentSemester = currentSemester
        self.currentTakenCourses = []

    def getTranscript(self):
        return self.transcript

    def setTranscript(self, transcript):
        self.transcript = transcript

    def getAdvisor(self):
        return self.advisor

    def setAdvisor(self, advisor):
        self.advisor = advisor

    def getCurrentSemester(self):
        return self.currentSemester

    def setCurrentSemester(self, currentSemester):
        self.currentSemester = currentSemester

    def getCurrentTakenCourses(self):
        return self.currentTakenCourses

    def setCurrentTakenCourses(self, course):
        self.currentTakenCourses.append(course)
