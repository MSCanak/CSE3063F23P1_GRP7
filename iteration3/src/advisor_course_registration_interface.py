import json
from notification import Notification
from colors import Colors
from session import Session
import logging
from datetime import datetime


# color need fix
class AdvisorCourseRegistrationInterface:
    logging.basicConfig(
        filename="./logs/app.log",
        filemode="w",
        format="%(name)s - %(levelname)s - %(message)s",
        level=logging.INFO,
    )

    def __init__(self, session: Session):
        self
        self.__colors = Colors()
        self.__session = session

        self.__selection_courses: list = []
        self.__id_to_courses = {}

        self.__id_to_course_lectures = {}

        self.__id_to_course_labs = {}

        # self.__messages_interface = MessagesInterface(self.__session)

        try:
            self.__request_file = json.load(open("./jsons/RegistrationRequests.json"))
            self.__courses_file = json.load(open("./jsons/courses.json"))

        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def adv_reg_menu(self):
        while True:
            print(
                f"{self.__colors.get_bold()}{self.__colors.get_red()}\n>> Advisor Course Registration Menu{self.__colors.get_reset()}{self.__colors.get_reset()}"
            )
            print(
                f"{self.__colors.get_yellow()}1{self.__colors.get_reset()}.   Show Students"
            )
            print(
                f"{self.__colors.get_yellow()}2{self.__colors.get_reset()}.   Approve/Deny Courses"
            )
            print(
                f"{self.__colors.get_yellow()}3{self.__colors.get_reset()}.   Finalize Registration"
            )
            print(
                f"{self.__colors.get_yellow()}4{self.__colors.get_reset()}.   Messages Menu"
            )
            print(
                f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.   Go Back to Advisor Menu"
            )
            print(
                "\n"
                + self.__colors.get_blue()
                + "--> "
                + self.__colors.get_reset()
                + "What do you want to do?   ",
                end="",
            )
            print(self.__colors.get_blue(), end="")
            choice = int(input())
            print(self.__colors.get_reset(), end="")
            if choice == 1:
                self.__show_students()
                self.__show_students_question_part()
            elif choice == 2:
                self.__approve_courses_menu()
            elif choice == 3:
                self.__finalize_registration_menu()
            elif choice == 4:
                self.__messages_interface.messages_menu()
            elif choice == 0:
                return
            else:
                print(
                    f"{self.__colors.get_yellow()}Invalid input! Please give a number!{self.__colors.get_reset()}"
                )

    def __show_students(self):
        advisor = self.__session.get_user()

        print(
            f"{self.__colors.get_bold()}{self.__colors.get_red()}\n>>> Students{self.__colors.get_reset()}{self.__colors.get_reset()}"
        )
        number_of_students = 1
        print("------------------------------------------------------------")

        print("| %6s | %-11s | %-33s |" % ("Number", "Student ID", "Student Name"))
        for student in advisor.get_students():
            print("------------------------------------------------------------")
            print(
                "| %-6s | %-11s | %-33s |"
                % (
                    number_of_students,
                    student.get_id(),
                    student.get_name() + " " + student.get_surname(),
                )
            )
            number_of_students += 1

        print("------------------------------------------------------------\n")

    def __show_students_question_part(self):
        print(
            f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.  Go back to the Advisor Course Registration Menu.\n"
        )
        print(
            f"{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   ",
            end="",
        )

        choice1 = input()

        if choice1 == "0":
            return

    def __approve_courses_menu(self):
        while True:
            advisor = self.__session.get_user()

            self.__show_students()

            print(
                f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.  Go back to the Advisor Course Registration Menu.\n"
            )
            print(
                f"{self.__colors.get_blue()}--> {self.__colors.get_reset()}Select student for approval: ",
                end="",
            )

            choice = int(input())
            if choice == 0:
                return

            student = advisor.get_students()[choice - 1]
            print()
            print(
                f"{self.__colors.get_green()}Student ID: {student.get_id()}\nStudent Name: {student.get_name()} {student.get_surname()}{self.__colors.get_reset()}\n"
            )
            student_id = student.get_id()
            self.__show_students_requested(student_id)

    def __show_students_requested(self, student_id):
        print(
            f"{self.__colors.get_bold()}{self.__colors.get_red()}\n>>> Requested Courses{self.__colors.get_reset()}{self.__colors.get_reset()}"
        )
        if not self.__request_file:
            print(
                f"{self.__colors.get_yellow()}There is no requested courses!{self.__colors.get_reset()}"
            )
            return
        for request_obj in self.__request_file:
            request_id = request_obj["StudentID"]
            if student_id != request_id:
                continue

            course = request_obj["SelectedCourses"]
            lecture = request_obj["SelectedLectures"]
            lab = request_obj["SelectedLabs"]

            # mapping for id to
            self.__id_to_courses = {student_id: course}
            self.__id_to_course_lectures = {student_id: lecture}
            self.__id_to_course_labs = {student_id: lab}

            for course in self.__id_to_courses[student_id]:
                print(
                    f"{self.__colors.get_yellow()}{course}{self.__colors.get_reset()}"
                )
                if self.__id_to_course_lectures[student_id] is not None:
                    for lecture in self.__id_to_course_lectures[student_id]:
                        if lecture.count(course) > 0:
                            print(
                                f"{self.__colors.get_yellow()}{lecture}{self.__colors.get_reset()}"
                            )
                if self.__id_to_course_labs[student_id] is not None:
                    for lab in self.__id_to_course_labs[student_id]:
                        if lab.count(course) > 0:
                            print(
                                f"{self.__colors.get_yellow()}{lab}{self.__colors.get_reset()}"
                            )
                self.__approve_course(student_id)

    def __approve_course(self, student_id):
        if not self.__id_to_courses:
            print(
                f"{self.__colors.get_yellow()}No courses to approve! Please select a different student!{self.__colors.get_reset()}"
            )
            return

        print(
            f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.  Go back to the Advisor Course Registration Menu."
        )
        print(
            f"{self.__colors.get_blue()}--> {self.__colors.get_reset()}Select course to approve: ",
            end="",
        )

        course_len = len(self.__id_to_courses[student_id])
        while True:
            choice = int(input())
            if choice == 0:
                break
            if choice > course_len:
                print(
                    f"{self.__colors.get_red()} invalid input {self.__colors.get_reset()}"
                )
                return
            course = self.__id_to_courses[student_id][choice - 1]
            if (
                self.__selection_courses is not None
                and course in self.__selection_courses
            ):
                print(
                    f"{self.__colors.get_yellow()}You have already approved this course! Press 0{self.__colors.get_reset()}"
                )
                continue
            else:
                self.__selection_courses.append(course)

            print(
                f"{self.__colors.get_green()}{course} is approved!{self.__colors.get_reset()}"
            )
            print(
                f"{self.__colors.get_blue()}If your selection is finished, you can press 0.{self.__colors.get_reset()}"
            )
            print(
                f"{self.__colors.get_red()}OR{self.__colors.get_blue()}\n--> Select course to approve: ",
                end="",
            )

        self.__save_approval(student_id)

        # send notification to student
        receiver_id = student_id
        description = "Advisor has approved your courses!"
        sender_id = self.__session.get_user().get_id()
        notification = Notification(receiver_id, description, sender_id)
        notification.send_notification(sender_id)

    def __save_approval(self, student_id):
        for request in self.__request_file:
            if request["StudentID"] != student_id:
                continue

            if self.__selection_courses is None:
                print(f"{self.__colors.get_red()} There is no approved courses ")
                break

            request["ApprovedCourses"] = self.__selection_courses

            for course in self.__selection_courses:
                if self.__id_to_course_lectures[student_id] is not None:
                    for lecture in self.__id_to_course_lectures[student_id]:
                        if lecture.count(course) > 0:
                            request["ApprovedLectures"].append(lecture)

                    for lab in self.__id_to_course_labs[student_id]:
                        if lab.count(course) > 0:
                            request["ApprovedLabs"].append(lab)

            request["SelectedCourses"] = []
            request["SelectedLectures"] = []
            request["SelectedLabs"] = []

        logging.info(
            "time: {}, User {} approve courses {}".format(
                datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
                self.__session.get_user().get_id(),
                student_id,
            )
        )

        try:
            with open("./jsons/RegistrationRequests.json", "w") as request_file:
                json.dump(self.__request_file, request_file)

        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def __finalize_registration_menu(self):
        while True:
            self.__show_students()
            print(
                f"{self.__colors.get_yellow()} Select student to finalize registration {self.__colors.get_reset()}"
            )

            choice = int(input())
            if choice == 0:
                return

            student = self.__session.get_user().get_students()[choice - 1]

            try:
                with open(
                    "./jsons/student/" + student.get_id() + ".json", "r"
                ) as student_file:
                    student_data = json.load(student_file)
            except Exception as e:
                logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
                print(e)

            for request in self.__request_file:
                if request["StudentID"] != student.get_id():
                    continue

                if request["ApprovedCourses"] is None:
                    print(f"{self.__colors.get_red()} There is no approved courses ")
                    break

                student_data["Transcript"]["Semester"].append({"Courses": []})

                for curr_course in request["ApprovedCourses"]:
                    for course in self.__courses_file:
                        if course["CourseID"] == curr_course:
                            newCourse = {
                                "CourseID": course["CourseID"],
                                "CourseName": course["CourseName"],
                                "Credit": course["Credit"],
                                "Type": course["Type"],
                                "Semester": course["Semester"],
                                "Grade": str(0),
                            }
                            student_data["Transcript"]["Semester"][-1][
                                "Courses"
                            ].append(newCourse)

                request.clear()
                logging.info(
                    "time: {}, User {} finalized courses for {}.".format(
                        datetime.now().strftime("%Y-%m-%d %H:%M:%S"),
                        self.__session.get_user().get_id(),
                        student.get_id(),
                    )
                )

            try:
                with open(
                    "./jsons/student/" + student.get_id() + ".json", "w"
                ) as student_file:
                    json.dump(student_data, student_file)

                with open("./jsons/RegistrationRequests.json", "w") as request_file:
                    json.dump(self.__request_file, request_file)
            except Exception as e:
                logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
                print(e)
