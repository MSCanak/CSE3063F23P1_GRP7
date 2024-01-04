from course import Course


class Lecture(Course):
    def __init__(
        self,
        course_name: str,
        lecture_id: str,
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
            course_id=lecture_id,
            credit=credit,
            course_type=course_type,
            semester=semester,
            grade=grade,
            course_session=course_session,
            lecturer=lecturer,
            quota=quota,
        )
        last_index = lecture_id.index(".")
        course_id = lecture_id[:last_index]
        self.set_course_id(course_id)
        self.__lecture_id = lecture_id

    def get_lecture_id(self) -> str:
        return self.__lecture_id

    def set_lecture_id(self, lecture_id: str):
        self.__lecture_id = lecture_id
