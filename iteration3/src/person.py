from abc import ABC, abstractmethod

class Person(ABC):
    def __init__(self, name, surname, email, phoneNumber, id, password, faculty, department):
        self.name = name
        self.surname = surname
        self.email = email
        self.phoneNumber = phoneNumber
        self.id = id
        self.password = password
        self.faculty = faculty
        self.department = department

    def get_name(self):
        return self.name

    def get_surname(self):
        return self.surname

    def get_email(self):
        return self.email

    def get_phone_number(self):
        return self.phoneNumber

    def get_id(self):
        return self.id

    def get_password(self):
        return self.password

    def get_faculty(self):
        return self.faculty

    def get_department(self):
        return self.department

    def set_name(self, name):
        self.name = name

    def set_surname(self, surname):
        self.surname = surname

    def set_email(self, email):
        self.email = email

    def set_phone_number(self, phoneNumber):
        self.phoneNumber = phoneNumber

    def set_id(self, id):
        self.id = id

    def set_password(self, password):
        self.password = password

    def set_faculty(self, faculty):
        self.faculty = faculty

    def set_department(self, department):
        self.department = department
