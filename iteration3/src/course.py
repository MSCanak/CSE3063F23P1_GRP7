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
        self.course_name = course_name
        self.course_id = course_id
        self.credit = credit
        self.course_type = course_type
        self.semester = semester
        self.grade = grade
        self.optional_prerequisite = optional_prerequisite or []
        self.mandatory_prerequisite = mandatory_prerequisite or []
        self.theoric = theoric
        self.practice = practice
        self.course_students = course_students
        self.lecturer = lecturer
        self.quota = quota
        self.course_session = course_session

    # getters and setters
    def get_course_name(self):
        return self.course_name

    def set_course_name(self, course_name):
        self.course_name = course_name

    def get_course_id(self):
        return self.course_id

    def set_course_id(self, course_id):
        self.course_id = course_id

    def get_credit(self):
        return self.credit

    def set_credit(self, credit):
        self.credit = credit

    def get_course_type(self):
        return self.course_type

    def set_course_type(self, course_type):
        self.course_type = course_type

    def get_optional_prerequisite(self):
        return self.optional_prerequisite

    def set_optional_prerequisite(self, optional_prerequisite):
        self.optional_prerequisite = optional_prerequisite

    def get_mandatory_prerequisite(self):
        return self.mandatory_prerequisite

    def set_mandatory_prerequisite(self, mandatory_prerequisite):
        self.mandatory_prerequisite = mandatory_prerequisite

    def get_theoric(self):
        return self.theoric

    def set_theoric(self, theoric):
        self.theoric = theoric

    def get_practice(self):
        return self.practice

    def set_practice(self, practice):
        self.practice = practice

    def get_course_students(self):
        return self.course_students

    def set_course_students(self, course_students):
        self.course_students = course_students

    def get_lecturer(self):
        return self.lecturer

    def set_lecturer(self, lecturer):
        self.lecturer = lecturer

    def get_quota(self):
        return self.quota

    def set_quota(self, quota):
        self.quota = quota

    def get_course_session(self):
        return self.course_session

    def set_course_session(self, course_session):
        self.course_session = course_session

    def get_semester(self):
        return self.semester

    def set_semester(self, semester):
        self.semester = semester

    def get_grade(self):
        return self.grade

    def set_grade(self, grade):
        self.grade = grade
