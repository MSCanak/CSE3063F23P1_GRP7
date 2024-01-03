class Advisor(Lecturer):
    def __init__(self, name, surname, email, phoneNumber, ID, password, faculty, department, academicTitle):
        super().__init__(name, surname, email, phoneNumber, ID, password, faculty, department, academicTitle)
        self.students = []

    def setStudent(self, student):
        self.students.append(student)
    
    def getStudents(self):
        return self.students
    