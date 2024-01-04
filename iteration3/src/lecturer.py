from person import Person
class Lecturer(Person):
    def __init__(self, name, surname, email, phone_number, ID, password, faculty, department, given_courses):
        super().__init__(name, surname, email, phone_number, ID, password, faculty, department)
        self.__given_courses = given_courses
        self.__academic_title = None


    def getGivenCourses(self):
        return self.__given_courses
    
    def setGivenCourses(self, given_courses):
        self.__given_courses = given_courses
    
    def getAcademicTitle(self):
        return self.__academic_title
    
    def setAcademicTitle(self, academic_title):
        self.__academic_title = academic_title
    
    def setCourse(self, course):
        self.__given_courses.append(course)

