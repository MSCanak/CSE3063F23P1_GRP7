from person import Person

from datetime import datetime

class Session:
    def __init__(self, user: Person):
        self.__user = user
        self.__starting_time = datetime.now()

    def get_starting_time(self):
        return self.__starting_time

    def get_user(self):
        return self.__user

    def set_user(self, user: Person):
        self.__user = user
