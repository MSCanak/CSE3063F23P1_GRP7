class Semester:
    def __init__(self, courses):
        # Private attributes
        self.__taken_credit = 0
        self.__completed_credit = 0
        self.__yano = 0.0
        self.__courses = courses

    # Getter and setter methods for private attributes
    def get_courses(self):
        return self.__courses

    def set_courses(self, courses):
        self.__courses = courses

    def get_taken_credit(self):
        return self.__taken_credit

    def set_taken_credit(self, taken_credit):
        self.__taken_credit = taken_credit

    def get_completed_credit(self):
        return self.__completed_credit

    def set_completed_credit(self, completed_credit):
        self.__completed_credit = completed_credit

    def get_yano(self):
        return self.__yano

    def set_yano(self, yano):
        self.__yano = yano
