from course import Course
class Lecture(Course):
    def __init__(self, course_name, lecture_id, *args):
        super().__init__(*args)
        self.__lecture_id = lecture_id
        last_index = lecture_id.index(".")
        course_id = lecture_id[:last_index]
        self.set_course_id(course_id)

    def get_lecture_id(self):
        return self.__lecture_id

    def set_lecture_id(self, lecture_id):
        self.__lecture_id = lecture_id
