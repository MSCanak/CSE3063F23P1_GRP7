from course import Course


class Lab(Course):
    def __init__(
        self,
        course_name: str,
        lab_id: str,
        credit: int = 0,
        course_type: str = None,
        semester: int = 0,
        grade: float = 0,
        course_session=None,
        lecturer=None,
        quota=None,
    ):
        super().__init__(
            course_name=course_name,
            course_id=lab_id,
            credit=credit,
            course_type=course_type,
            semester=semester,
            grade=grade,
            course_session=course_session,
            lecturer=lecturer,
            quota=quota,
        )
        last_index = lab_id.index(".")
        course_id = lab_id[:last_index]
        self.set_course_id(course_id)
        self.__lab_id = lab_id

    def get_lab_id(self) -> str:
        return self.__lab_id

    def set_lab_id(self, lab_id: str):
        self.__lab_id = lab_id
