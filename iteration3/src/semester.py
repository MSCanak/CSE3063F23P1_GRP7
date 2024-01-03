class Semester:
    def __init__(self, courses):
        self.taken_credit = 0
        self.completed_credit = 0
        self.yano = 0.0
        self.courses = courses

    def get_courses(self):
        return self.courses

    def set_courses(self, courses):
        self.courses = courses

    def get_taken_credit(self):
        return self.taken_credit

    def set_taken_credit(self, taken_credit):
        self.taken_credit = taken_credit

    def get_completed_credit(self):
        return self.completed_credit

    def set_completed_credit(self, completed_credit):
        self.completed_credit = completed_credit

    def get_yano(self):
        return self.yano

    def set_yano(self, yano):
        self.yano = yano
