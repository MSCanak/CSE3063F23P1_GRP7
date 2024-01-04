class CourseSession:
    def __init__(self, course_day, course_start_time, course_end_time, course_place):
        self.__course_day = course_day
        self.__course_start_time = course_start_time
        self.__course_end_time = course_end_time
        self.__course_place = course_place

    def get_course_day(self):
        return self.__course_day

    def set_course_day(self, course_day):
        self.__course_day = course_day

    def get_course_start_time(self):
        return self.__course_start_time

    def set_course_start_time(self, course_start_time):
        self.__course_start_time = course_start_time

    def get_course_end_time(self):
        return self.__course_end_time

    def set_course_end_time(self, course_end_time):
        self.__course_end_time = course_end_time

    def get_course_place(self):
        return self.__course_place

    def set_course_place(self, course_place):
        self.__course_place = course_place
