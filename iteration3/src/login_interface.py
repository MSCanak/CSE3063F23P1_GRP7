from datetime import datetime
import json
import sys

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
            ID = self.get_user_id()
            password = self.get_user_password()

            user_type = self.get_user_type(ID)
            if user_type == "student":
                if self.user_exists("students", ID):
                    if self.check_user_login_info("students", ID, password):
                        self.__session = Session(self.create_student(ID, None))
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
                        print("You will be directed to the Student Menu!\n")
                        self.write_log(
                            "Student {} logged in.".format(
                                self.__session.get_user().get_id()
                            )
                        )
                        student_interface = StudentInterface(self.__session, self)
                        student_interface.stu_menu()
                    else:
                        print(
                            "{}Invalid ID or password! Please try again.{}".format(
                                self.__colors.get_yellow(), self.__colors.get_reset()
                            )
                        )
                        continue
                else:
                    print(
                        "{}Invalid ID! Please try again.{}".format(
                            self.__colors.get_yellow(), self.__colors.get_reset()
                        )
                    )
                    continue
            elif user_type == "lecturer":
                if self.user_exists("lecturers", ID):
                    if self.check_user_login_info("advisors", ID, password):
                        self.__session = Session(self.create_advisor(ID, "null"))
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
                        print("You will be directed to the Advisor Menu!\n")
                        self.write_log(
                            "Advisor {} logged in.".format(
                                self.__session.get_user().get_id()
                            )
                        )
                        advisor_interface = AdvisorInterface(self.__session, self)
                        advisor_interface.adv_menu()
                    elif self.check_user_login_info("lecturers", ID, password):
                        self.__session = Session(self.create_lecturer(ID))
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
                        print("You will be directed to the Lecturer Menu!\n")
                        self.write_log(
                            "Lecturer {} logged in.".format(
                                self.__session.get_user().get_id()
                            )
                        )
                        advisor_interface = AdvisorInterface(self.__session, self)
                        advisor_interface.adv_menu()
                    else:
                        print(
                            "{}Invalid ID or password! Please try again.{}".format(
                                self.__colors.get_yellow(), self.__colors.get_reset()
                            )
                        )
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
                print(
                    "{}{}Thank you for using Marmara Course Registration System{}".format(
                        self.__colors.get_red(),
                        self.__colors.get_bold(),
                        self.__colors.get_reset(),
                    )
                )
                self.write_log(
                    "User {} logged out.".format(self.__session.get_user().get_id())
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

    def get_user_id(self):
        while True:
            print(
                "{}ID: {}".format(
                    self.__colors.get_yellow(), self.__colors.get_reset()
                ),
                end="",
            )
            ID = input()
            if not self.id_format_checker(ID):
                print(
                    "{}Invalid input! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
            else:
                return ID

    def get_user_password(self):
        while True:
            print(
                "{}Password: {}".format(
                    self.__colors.get_yellow(), self.__colors.get_reset()
                ),
                end="",
            )
            password = input()
            if not self.password_format_checker(password):
                print(
                    "{}Invalid input! Please try again.{}".format(
                        self.__colors.get_yellow(), self.__colors.get_reset()
                    )
                )
            else:
                return password

    def user_exists(self, file_name, ID):
        try:
            with open("jsons/{}.json".format(file_name), "r") as reader:
                user_list = json.load(reader)

                for user in user_list:
                    if user["ID"] == ID:
                        return True
        except (IOError, json.JSONDecodeError):
            pass
        return False

    def check_user_login_info(self, file_name, ID, password):
        while True:
            try:
                with open("jsons/{}.json".format(file_name), "r") as reader:
                    user_list = json.load(reader)

                    for user in user_list:
                        if user["ID"] == ID and user["Password"] == password:
                            return True
            except (IOError, json.JSONDecodeError):
                pass
            return False

    def create_student(self, ID, advisor):
        try:
            with open("jsons/student/{}.json".format(ID), "r") as reader:
                student = json.load(reader)

                name = student["Name"]
                surname = student["Surname"]
                email = student["EMail"]
                phone_number = student["PhoneNumber"]
                department = student["Department"]
                advisor_ID = student["AdvisorID"]
                semester = student["Semester"]
                faculty = student["Faculty"]
                student_ID = student["ID"]
                password = student["Password"]
                taken_courses_array = student["TakenCourses"]

                if advisor is None:
                    advisor = self.create_advisor(advisor_ID, student_ID)

                stu = Student(
                    name,
                    surname,
                    email,
                    phone_number,
                    student_ID,
                    password,
                    faculty,
                    department,
                    semester,
                    advisor,
                )

                if taken_courses_array:
                    for course_ID in taken_courses_array:
                        stu.set_current_taken_courses(self.create_course(course_ID))

                return stu
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def create_advisor(self, advisor_ID, student_ID):
        try:
            with open("jsons/advisors.json", "r") as reader:
                advisor_list: list = json.load(reader)

                for advisor in advisor_list:
                    if advisor["ID"] == advisor_ID:
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
                            advisor_ID,
                            password,
                            faculty,
                            department,
                            academic_title,
                        )

                        for student in student_list:
                            student_ID_2 = student["ID"]
                            if student_ID == student_ID_2:
                                continue
                            adv.set_student(self.create_student(student_ID_2, adv))

                        for course_code in given_courses:
                            adv.set_course(self.create_course(course_code))

                        return adv
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def create_lecturer(self, lecturer_ID):
        try:
            with open("jsons/lecturers.json", "r") as reader:
                lecturer_list: list = json.load(reader)

                for lecturer in lecturer_list:
                    if lecturer["ID"] == lecturer_ID:
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
                            lecturer_ID,
                            password,
                            faculty,
                            department,
                            given_courses,
                            academic_title,
                        )

                        for course_code in given_courses:
                            lec.set_course(self.create_course(course_code))

                        return lec
        except (IOError, json.JSONDecodeError):
            pass
        return None

    def create_course(self, course_code: str):
        try:
            with open("jsons/CoursesOffered.json", "r", encoding='utf-8') as reader:
                courses_list = json.load(reader)

                for course_obj in courses_list:
                    if course_obj["CourseID"] == course_code:
                        course_name = course_obj["CourseName"]
                        course_day_time_location = course_obj["CourseDayTimeLocation"]
                        course_session = self.create_course_session(
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

    def create_course_session(self, course_day_time_location: str) -> CourseSession:
        parts = course_day_time_location.split(" ")
        course_day = parts[::5]
        course_start_time = parts[1::5]
        course_end_time = parts[3::5]
        course_place = parts[4::5]

        return CourseSession(
            course_day, course_start_time, course_end_time, course_place
        )

    def id_format_checker(self, ID: str):
        if not ID or not ID.isdigit():
            return False
        if len(ID) != 9 and len(ID) != 6:
            return False
        return True

    def password_format_checker(self, password):
        if not password or " " in password:
            return False
        return True

    def get_user_type(self, ID):
        if len(ID) == 9:
            return "student"
        elif len(ID) == 6:
            return "lecturer"
        else:
            return None

    def write_log(self, message: str):
        with open("jsons/logs.json", "a") as f:
            log = {"time": datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "log": message}
            f.write(json.dumps(log) + "\n")
