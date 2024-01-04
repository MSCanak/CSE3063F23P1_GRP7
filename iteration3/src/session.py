from datetime import datetime

class Session:
    def __init__(self, user):
        self.user = user
        self.starting_time = datetime.now()

    def get_startingTime(self):
        return self.starting_time

    def get_user(self):
        return self.user

    def set_user(self, user):
        self.user = user
