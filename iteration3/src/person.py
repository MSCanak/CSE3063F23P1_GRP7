from abc import ABC, abstractmethod


class Person(ABC):
    def __init__(
        self,
        name: str,
        surname: str,
        email: str,
        phone_number: str,
        id: str,
        password: str,
        faculty: str,
        department: str,
    ):
        self.__name = name
        self.__surname = surname
        self.__email = email
        self.__phone_number = phone_number
        self.__id = id
        self.__password = password
        self.__faculty = faculty
        self.__department = department

    def get_name(self) -> str:
        return self.__name

    def get_surname(self) -> str:
        return self.__surname

    def get_email(self) -> str:
        return self.__email

    def get_phone_number(self) -> str:
        return self.__phone_number

    def get_id(self) -> str:
        return self.__id

    def get_password(self) -> str:
        return self.__password

    def get_faculty(self) -> str:
        return self.__faculty

    def get_department(self) -> str:
        return self.__department

    def set_name(self, name):
        self.__name = name

    def set_surname(self, surname):
        self.__surname = surname

    def set_email(self, email):
        self.__email = email

    def set_phone_number(self, phone_number):
        self.__phone_number = phone_number

    def set_id(self, id):
        self.__id = id

    def set_password(self, password):
        self.__password = password

    def set_faculty(self, faculty):
        self.__faculty = faculty

    def set_department(self, department):
        self.__department = department
