class Course:
    def __init__(
        self,
        course_name,
        course_id,
        credit,
        course_type,
        semester,
        grade=None,
        optional_prerequisite=None,
        mandatory_prerequisite=None,
        theoric=None,
        practice=None,
        course_students=None,
        lecturer=None,
        quota=None,
        course_session=None,
    ):
        self.__course_name = course_name
        self.__course_id = course_id
        self.__credit = credit
        self.__course_type = course_type
        self.__semester = semester
        self.__grade = grade
        self.__optional_prerequisite = optional_prerequisite or []
        self.__mandatory_prerequisite = mandatory_prerequisite or []
        self.__theoric = theoric
        self.__practice = practice
        self.__course_students = course_students
        self.__lecturer = lecturer
        self.__quota = quota
        self.__course_session = course_session

    # getters and setters
    def get_course_name(self):
        return self.__course_name

    def set_course_name(self, course_name):
        self.__course_name = course_name

    def get_course_id(self):
        return self.__course_id

    def set_course_id(self, course_id):
        self.__course_id = course_id

    def get_credit(self):
        return self.__credit

    def set_credit(self, credit):
        self.__credit = credit

    def get_course_type(self):
        return self.__course_type

    def set_course_type(self, course_type):
        self.__course_type = course_type

    def get_optional_prerequisite(self):
        return self.__optional_prerequisite

    def set_optional_prerequisite(self, optional_prerequisite):
        self.__optional_prerequisite = optional_prerequisite

    def get_mandatory_prerequisite(self):
        return self.__mandatory_prerequisite

    def set_mandatory_prerequisite(self, mandatory_prerequisite):
        self.__mandatory_prerequisite = mandatory_prerequisite

    def get_theoric(self):
        return self.__theoric

    def set_theoric(self, theoric):
        self.__theoric = theoric

    def get_practice(self):
        return self.__practice

    def set_practice(self, practice):
        self.__practice = practice

    def get_course_students(self):
        return self.__course_students

    def set_course_students(self, course_students):
        self.__course_students = course_students

    def get_lecturer(self):
        return self.__lecturer

    def set_lecturer(self, lecturer):
        self.__lecturer = lecturer

    def get_quota(self):
        return self.__quota

    def set_quota(self, quota):
        self.__quota = quota

    def get_course_session(self):
        return self.__course_session

    def set_course_session(self, course_session):
        self.__course_session = course_session

    def get_semester(self):
        return self.__semester

    def set_semester(self, semester):
        self.__semester = semester

    def get_grade(self):
        return self.__grade

    def set_grade(self, grade):
        self.__grade = grade
