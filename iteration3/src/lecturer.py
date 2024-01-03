class Lecturer(Person):
    def __init__(self, name, surname, email, phoneNumber, ID, password, faculty, department, givenCourses, academicTitle):
        super().__init__(name, surname, email, phoneNumber, ID, password, faculty, department)
        self.givenCourses = givenCourses
        self.academicTitle = academicTitle

    def getGivenCourses(self):
        return self.givenCourses
    
    def setGivenCourses(self, givenCourses):
        self.givenCourses = givenCourses
    
    def getAcademicTitle(self):
        return self.academicTitle
    
    def setAcademicTitle(self, academicTitle):
        self.academicTitle = academicTitle
    
    def setCourse(self, course):
        self.givenCourses.append(course)

