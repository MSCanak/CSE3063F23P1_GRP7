from datetime import datetime
import json
import sys
import logging
import os

from student_interface import StudentInterface
from advisor_interface import AdvisorInterface
from course import Course
from lecture import Lecture
from lab import Lab
from course_session import CourseSession
from advisor import Advisor
from student import Student
from lecturer import Lecturer
from colors import Colors
from session import Session


class LoginInterface:
    logging.basicConfig(filename='./logs/app.log', filemode='w', format='%(name)s - %(levelname)s - %(message)s', level=logging.INFO)
    def __init__(self):
        self.__colors = Colors()
        self.__session: Session = None

    def login(self):
        while True:
            print(
                self.__colors.get_bold()
                + self.__colors.get_red()
                + "\nMarmara Course Registration System\n"
                + self.__colors.get_reset()
                + self.__colors.get_reset()
            )
            print("Please enter your ID and password to login!\n")
            id = self.__get_user_id()
            password = self.__get_user_password()

            user_type = self.__get_user_type(id)
            if user_type == "student":
                if self.__user_exists("students", id):
                    if self.__check_user_login_info("students", id, password):
                        self.__session = Session(self.__create_student(id, None))
                        print(
                            "\nWelcome {} {}!".format(
                                self.__session.get_user().get_name(),
                                self.__session.get_user().get_surname(),
                            )
                        )
                        print(
                            "You have {} successfully {} logged in.".format(
                                self.__colors.get_green(), self.__colors.get_reset()
                            )
                        )
                        logging.info("time: {}, User {} logged in.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))
                        print("You will be directed to the Student Menu!\n")
                        student_interface = StudentInterface(self.__session, self)
                        student_interface.stu_menu()
                    else:
                        print(
                            "{}Invalid ID or password! Please try again.{}".format(
                                self.__colors.get_yellow(), self.__colors.get_reset()
                            )
                        )
                        logging.warning("time: {}, User {} invalid password.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), id))
                        continue
                else:
                    print(
                        "{}Invalid ID! Please try again.{}".format(
                            self.__colors.get_yellow(), self.__colors.get_reset()
                        )
                    )
                    continue
            elif user_type == "lecturer":
                if self.__user_exists("lecturers", id):
                    if self.__check_user_login_info("advisors", id, password):
                        self.__session = Session(self.__create_advisor(id, "null"))
                        print(
                            "\nWelcome {} {}!".format(
                                self.__session.get_user().get_name(),
                                self.__session.get_user().get_surname(),
                            )
                        )
                        print(
                            "You have {} successfully {} logged in.".format(
                                self.__colors.get_green(), self.__colors.get_reset()
                            )
                        )
                        logging.info("time: {}, User {} logged in.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))
                        print("You will be directed to the Advisor Menu!\n")
                        advisor_interface = AdvisorInterface(self.__session, self)
                        advisor_interface.adv_menu()
                    elif self.__check_user_login_info("lecturers", id, password):
                        self.__session = Session(self.__create_lecturer(id))
                        print(
                            "\nWelcome {} {}!".format(
                                self.__session.get_user().get_name(),
                                self.__session.get_user().get_surname(),
                            )
                        )
                        print(
                            "You have {} successfully {} logged in.".format(
                                self.__colors.get_green(), self.__colors.get_reset()
                            )
                        )
                        logging.info("time: {}, User {} logged in.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))
                        print("You will be directed to the Lecturer Menu!\n")
                        advisor_interface = AdvisorInterface(self.__session, self)
                        advisor_interface.adv_menu()
                    else:
                        print(
                            "{}Invalid ID or password! Please try again.{}".format(
                                self.__colors.get_yellow(), self.__colors.get_reset()
                            )
                        )
                        logging.warning("time: {}, User {} invalid password.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), id))
                        continue
                else:
                    print(
                        "{}Invalid ID! Please try again.{}".format(
                            self.__colors.get_yellow(), self.__colors.get_reset()
                        )
                    )
                    continue
            else:
                print(
                    "{}Invalid ID or password! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
                continue

    def logout(self):
        while True:
            print(
                "Do you want to logout and exit? ({}/{})".format(
                    self.__colors.get_yellow() + "y" + self.__colors.get_reset(),
                    self.__colors.get_yellow() + "n" + self.__colors.get_reset(),
                )
            )
            answer = input()
            if answer == "y":
                print(
                    "You have {} successfully {} logged out and exited.".format(
                        self.__colors.get_green(), self.__colors.get_reset()
                    )
                )
                logging.info("time: {}, User {} logged out.".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))
                print(
                    "{}{}Thank you for using Marmara Course Registration System{}".format(
                        self.__colors.get_red(),
                        self.__colors.get_bold(),
                        self.__colors.get_reset(),
                    )
                )
                self.__session.set_user(None)
                self.exit()
            elif answer == "n":
                print(
                    "{}You will be redirected to the login menu.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
                self.__session.set_user(None)
                self.login()
            else:
                print(
                    "{}Invalid input! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
                continue

    def exit(self):
        sys.exit(0)

    def __get_user_id(self):
        while True:
            print(
                "{}ID: {}".format(
                    self.__colors.get_yellow(), self.__colors.get_reset()
                ),
                end="",
            )
            id = input()
            if not self.__id_format_checker(id):
                print(
                    "{}Invalid input! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
            else:
                return id

    def __get_user_password(self):
        while True:
            print(
                "{}Password: {}".format(
                    self.__colors.get_yellow(), self.__colors.get_reset()
                ),
                end="",
            )
            password = input()
            if not self.__password_format_checker(password):
                print(
                    "{}Invalid input! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
            else:
                return password

    def __user_exists(self, file_name, id):
        try:
            with open("jsons/{}.json".format(file_name), "r", encoding='utf-8') as reader:
                user_list = json.load(reader)

                for user in user_list:
                    if user["ID"] == id:
                        return True
        except (IOError, json.JSONDecodeError):
            pass
        return False

    def __check_user_login_info(self, file_name, id, password):
        while True:
            try:
                with open("jsons/{}.json".format(file_name), "r", encoding='utf-8') as reader:
                    user_list = json.load(reader)

                    for user in user_list:
                        if user["ID"] == id and user["Password"] == password:
                            return True
            except (IOError, json.JSONDecodeError):
                pass
            return False

    def __create_student(self, id, advisor):
        try:
            with open("jsons/student/{}.json".format(id), "r") as reader:
                student = json.load(reader)

                name = student["Name"]
                surname = student["Surname"]
                email = student["EMail"]
                phone_number = student["PhoneNumber"]
                department = student["Department"]
                advisor_id = student["AdvisorID"]
                semester = student["Semester"]
                faculty = student["Faculty"]
                student_id = student["ID"]
                password = student["Password"]
                taken_courses_array = student["TakenCourses"]

                if advisor is None:
                    advisor = self.__create_advisor(advisor_id, student_id)

                stu = Student(
                    name,
                    surname,
                    email,
                    phone_number,
                    student_id,
                    password,
                    faculty,
                    department,
                    semester,
                    advisor,
                )

                if taken_courses_array:
                    for course_id in taken_courses_array:
                        stu.set_current_taken_courses(self.__create_course(course_id))

                return stu
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def __create_advisor(self, advisor_id, student_id):
        try:
            with open("jsons/advisors.json", "r") as reader:
                advisor_list: list = json.load(reader)

                for advisor in advisor_list:
                    if advisor["ID"] == advisor_id:
                        name = advisor["Name"]
                        surname = advisor["Surname"]
                        email = advisor["EMail"]
                        phone_number = advisor["PhoneNumber"]
                        faculty = advisor["Faculty"]
                        department = advisor["Department"]
                        password = advisor["Password"]
                        academic_title = advisor["AcademicTitle"]
                        student_list = advisor["Students"]
                        given_courses = advisor["Courses"]

                        adv = Advisor(
                            name,
                            surname,
                            email,
                            phone_number,
                            advisor_id,
                            password,
                            faculty,
                            department,
                            academic_title,
                        )

                        for student in student_list:
                            student_id_2 = student["ID"]
                            if student_id == student_id_2:
                                continue
                            adv.set_student(self.__create_student(student_id_2, adv))

                        for course_code in given_courses:
                            adv.set_course(self.__create_course(course_code))

                        return adv
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def __create_lecturer(self, lecturer_id):
        try:
            with open("jsons/lecturers.json", "r") as reader:
                lecturer_list: list = json.load(reader)

                for lecturer in lecturer_list:
                    if lecturer["ID"] == lecturer_id:
                        name = lecturer["Name"]
                        surname = lecturer["Surname"]
                        email = lecturer["EMail"]
                        phone_number = lecturer["PhoneNumber"]
                        faculty = lecturer["Faculty"]
                        department = lecturer["Department"]
                        password = lecturer["Password"]
                        given_courses = lecturer["Courses"]
                        academic_title = lecturer["AcademicTitle"]

                        lec = Lecturer(
                            name,
                            surname,
                            email,
                            phone_number,
                            lecturer_id,
                            password,
                            faculty,
                            department,
                            given_courses,
                            academic_title,
                        )

                        for course_code in given_courses:
                            lec.set_course(self.__create_course(course_code))

                        return lec
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def __create_course(self, course_code: str):
        try:
            with open("jsons/CoursesOffered.json", "r", encoding='utf-8') as reader:
                courses_list = json.load(reader)

                for course_obj in courses_list:
                    if course_obj["CourseID"] == course_code:
                        course_name = course_obj["CourseName"]
                        course_day_time_location = course_obj["CourseDayTimeLocation"]
                        course_session = self.__create_course_session(
                            course_day_time_location
                        )

                        dot_count = course_code.count(".")
                        if dot_count == 1:
                            return Lecture(
                                course_name=course_name,
                                lecture_id=course_code,
                                course_session=course_session,
                            )
                        elif dot_count == 2:
                            return Lab(
                                course_name=course_name,
                                lab_id=course_code,
                                course_session=course_session,
                            )
                        elif dot_count == 0:
                            return Course(
                                course_name=course_name,
                                course_id=course_code,
                                course_session=course_session,
                            )
                        else:
                            print(
                                "{}Invalid course code!{}".format(
                                    self.__colors.get_cyan(), self.__colors.get_reset()
                                )
                            )
                            continue
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def __create_course_session(self, course_day_time_location: str) -> CourseSession:
        parts = course_day_time_location.split(" ")
        course_day = parts[::5]
        course_start_time = parts[1::5]
        course_end_time = parts[3::5]
        course_place = parts[4::5]

        return CourseSession(
            course_day, course_start_time, course_end_time, course_place
        )

    def __id_format_checker(self, id: str):
        if not id or not id.isdigit():
            return False
        if len(id) != 9 and len(id) != 6:
            return False
        return True

    def __password_format_checker(self, password):
        if not password or " " in password:
            return False
        return True

    def __get_user_type(self, id):
        if len(id) == 9:
            return "student"
        elif len(id) == 6:
            return "lecturer"
        else:
            return None

