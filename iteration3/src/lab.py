class Lab(Course):
    def __init__(self, course_name, lab_id, *args):
        super().__init__(*args)
        self.lab_id = lab_id
        last_index = lab_id.index(".")
        course_id = lab_id[:last_index]
        self.set_course_id(course_id)

    def get_lab_id(self):
        return self.lab_id

    def set_lab_id(self, course_id):
        self.lab_id = course_id
