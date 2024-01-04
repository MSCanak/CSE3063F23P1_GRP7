from person import Person
class Lecturer(Person):
    def __init__(self, name, surname, email, phone_number, ID, password, faculty, department, given_courses):
        super().__init__(name, surname, email, phone_number, ID, password, faculty, department)
        self.__given_courses = given_courses
        self.__academic_title = None


    def get_given_courses(self):
        return self.__given_courses
    
    def set_given_courses(self, given_courses):
        self.__given_courses = given_courses
    
    def get_academic_title(self):
        return self.__academic_title
    
    def set_academic_title(self, academic_title):
        self.__academic_title = academic_title
    
    def set_course(self, course):
        self.__given_courses.append(course)

