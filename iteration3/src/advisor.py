from lecturer import Lecturer
class Advisor(Lecturer):
    def __init__(self, name, surname, email, phone_number, id, password, faculty, department, academic_title):
        super().__init__(name, surname, email, phone_number, id, password, faculty, department, academic_title)
        self.__students = []

    def set_student(self, student):
        self.__students.append(student)
    
    def get_students(self):
        return self.__students