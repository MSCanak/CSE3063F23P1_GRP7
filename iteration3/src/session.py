from datetime import datetime

class Session:
    def __init__(self, user):
        self.user = user
        self.startingTime = datetime.now()

    def getStartingTime(self):
        return self.startingTime

    def getUser(self):
        return self.user

    def setUser(self, user):
        self.user = user
