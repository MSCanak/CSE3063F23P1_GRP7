from course import Course
class Lab(Course):
    def __init__(self, course_name, lab_id, *args):
        super().__init__(*args)
        self.__lab_id = lab_id
        self.__last_index = lab_id.index(".")
        self__course_id = lab_id[:self.__last_index]
        self.set_course_id(self__course_id)

    def get_lab_id(self):
        return self.__lab_id

    def set_lab_id(self, course_id):
        self.__lab_id = course_id
