import json

from course_session_times import CourseSessionTimes
from lecture import Lecture
from notifications_interface import NotificationsInterface
from student_course_registration_interface import (
    StudentCourseRegistrationInterface,
)
from weekly_schedule import WeeklySchedule
from session import Session
from colors import Colors


class StudentInterface:
    def __init__(self, session: Session, login_int):
        self.__session = session
        self.__login_int = login_int
        self.__notification_interface: NotificationsInterface = None
        self.__student_course_registration_interface: StudentCourseRegistrationInterface = (
            None
        )
        self.__colors = Colors()

    def stu_menu(self):
        while True:
            print(
                f"{self.__colors.get_red()}{self.__colors.get_bold()}\n> Student Menu\n{self.__colors.get_reset()}"
            )
            print(
                f"{self.__colors.get_yellow()}1{self.__colors.get_reset()}.   View Notifications"
            )
            print(
                f"{self.__colors.get_yellow()}2{self.__colors.get_reset()}.   View Weekly Schedule"
            )
            print(
                f"{self.__colors.get_yellow()}3{self.__colors.get_reset()}.   View Transcript"
            )
            print(
                f"{self.__colors.get_yellow()}4{self.__colors.get_reset()}.   View Curriculum"
            )
            print(
                f"{self.__colors.get_yellow()}5{self.__colors.get_reset()}.   Go to Course Registration System"
            )
            print(f"{self.__colors.get_yellow()}*{self.__colors.get_reset()}.   Logout")
            print(f"{self.__colors.get_yellow()}x{self.__colors.get_reset()}.   Exit")

            choice = input(
                f"\n{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
            ).strip()

            if len(choice) > 1 or not choice.isdigit():
                print(
                    f"{self.__colors.get_yellow()}Invalid input format! Please give a number!{self.__colors.get_reset()}"
                )
                continue

            choice = int(choice)

            if choice == 1:
                self.__show_notifications()
            elif choice == 2:
                courses = self.calculate_weekly_schedule()
                self.show_weekly_schedule(courses)
            elif choice == 3:
                self.__session.get_user().get_transcript().view_transcript()
                while True:
                    print(
                        "\n-------------------------------------------------------------------------------------------------------------\n"
                    )
                    print(
                        f"{self.__colors.get_yellow()}0{self.__colors.get_reset()}.  Go back to the Student Menu."
                    )
                    back_choice = input(
                        f"\n{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
                    ).strip()

                    if len(back_choice) > 1 or not back_choice.isdigit():
                        print(
                            f"{self.__colors.get_yellow()}Invalid input! Please give a number!{self.__colors.get_reset()}"
                        )
                        continue

                    back_choice = int(back_choice)

                    if back_choice == 0:
                        break
                    else:
                        print(
                            f"{self.__colors.get_yellow()}Invalid input! Please try again.{self.__colors.get_reset()}"
                        )
            elif choice == 4:
                try:
                    self.__show_curriculum()
                except Exception:
                    pass
            elif choice == 5:
                self.__student_course_registration_interface = (
                    StudentCourseRegistrationInterface(self.__session)
                )
                self.__student_course_registration_interface.stu_reg_menu()
            elif choice == "*":
                self.__login_int.logout()
            elif choice == "x":
                print(
                    f"{self.__colors.get_yellow()}{self.__colors.get_bold()}\n< Thank you for using Marmara Course Registration System >{self.__colors.get_reset()}"
                )
                self.__login_int.exit()
            else:
                print(
                    f"{self.__colors.get_yellow()}Invalid input! Please try again.{self.__colors.get_reset()}"
                )

    def __show_curriculum(self):
        with open("./jsons/courses.json", "r") as file:
            courses_json = json.load(file)

        print(
            f"{self.__colors.get_red()}{self.__colors.get_bold()}\n>> Curriculum\n{self.__colors.get_reset()}"
        )

        previous_semester = -1
        for course in courses_json:
            course_id = course["CourseID"]
            course_name = course["CourseName"]
            course_type = course["Type"]
            credit = course["Credit"]
            semester = course["Semester"]
            optional_prerequisites = "-".join(
                map(str, course.get("OptionalPrerequisites", []))
            )
            mandatory_prerequisites = "-".join(
                map(str, course.get("MandatoryPrerequisites", []))
            )

            if course_type == "M":
                course_type = "Mandatory"
            elif course_type == "E":
                course_type = "Elective"

            if previous_semester != semester:
                print(
                    "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------"
                )
                print(
                    f"{self.__colors.get_blue()}\nSemester {semester}{self.__colors.get_reset()}\n"
                )
                print(
                    f"\t{self.__colors.get_yellow()}%-10s%-58s%-15s%-8s%-25s%-25s{self.__colors.get_reset()}\n"
                    % (
                        "CourseID",
                        "CourseName",
                        "Type",
                        "Credit",
                        "OptionalPrerequisites",
                        "MandatoryPrerequisites",
                    )
                )

                previous_semester = semester

            print(
                "\t\t%-10s%-58s%-15s%-8s%-25s%-25s"
                % (
                    course_id,
                    course_name,
                    course_type,
                    credit,
                    optional_prerequisites,
                    mandatory_prerequisites,
                )
            )

        while True:
            print(
                "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------"
            )
            print(
                f"{self.__colors.get_yellow()}\n0{self.__colors.get_reset()}.  Go back to the Student Menu."
            )
            back_choice = input(
                f"\n{self.__colors.get_blue()}--> {self.__colors.get_reset()}What do you want to do?   "
            ).strip()

            if len(back_choice) > 1 or not back_choice.isdigit():
                print(
                    f"{self.__colors.get_yellow()}Invalid input! Please give a number!{self.__colors.get_reset()}"
                )
                continue

            back_choice = int(back_choice)

            if back_choice == 0:
                return
            else:
                print(
                    f"{self.__colors.get_yellow()}Invalid input! Please try again.{self.__colors.get_reset()}"
                )

    def __show_notifications(self):
        self.__notification_interface = NotificationsInterface(self.__session)
        self.__notification_interface.notifications_menu()

    def calculate_weekly_schedule(self):
        current_taken_courses = self.__session.get_user().get_current_taken_courses()
        return current_taken_courses

    def show_weekly_schedule(self, courses):
        # creating lists for each day of the week
        monday_courses_id = []
        tuesday_courses_id = []
        wednesday_courses_id = []
        thursday_courses_id = []
        friday_courses_id = []
        saturday_courses_id = []
        sunday_courses_id = []

        monday_courses_start_time = []
        tuesday_courses_start_time = []
        wednesday_courses_start_time = []
        thursday_courses_start_time = []
        friday_courses_start_time = []
        saturday_courses_start_time = []
        sunday_courses_start_time = []

        monday_courses_place = []
        tuesday_courses_place = []
        wednesday_courses_place = []
        thursday_courses_place = []
        friday_courses_place = []
        saturday_courses_place = []
        sunday_courses_place = []

        print(
            self.__colors.get_red()
            + self.__colors.get_bold()
            + "\n>> Weekly Schedule\n"
            + self.__colors.get_reset()
            + self.__colors.get_reset()
        )
        print(
            "------------------------------------------------------------------------------------------------------------------------------------------"
        )

        print(
            "|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n"
            % (
                "",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday",
            )
        )

        for i in range(len(courses)):
            course = courses[i]
            if course is None:
                continue
            course_session = course.get_course_session()

            for j in range(len(course_session.get_course_day())):
                if course_session.get_course_day()[j] == "Pazartesi":
                    if isinstance(course, Lecture):
                        monday_courses_id.append(course.get_lecture_id())
                    else:
                        monday_courses_id.append(course.get_lab_id())
                    monday_courses_place.append(course_session.get_course_place()[j])
                    monday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Salı":
                    if isinstance(course, Lecture):
                        tuesday_courses_id.append(course.get_lecture_id())
                    else:
                        tuesday_courses_id.append(course.get_lab_id())
                    tuesday_courses_place.append(course_session.get_course_place()[j])
                    tuesday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Çarşamba":
                    if isinstance(course, Lecture):
                        wednesday_courses_id.append(course.get_lecture_id())
                    else:
                        wednesday_courses_id.append(course.get_lab_id())
                    wednesday_courses_place.append(course_session.get_course_place()[j])
                    wednesday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Perşembe":
                    if isinstance(course, Lecture):
                        thursday_courses_id.append(course.get_lecture_id())
                    else:
                        thursday_courses_id.append(course.get_lab_id())
                    thursday_courses_place.append(course_session.get_course_place()[j])
                    thursday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Cuma":
                    if isinstance(course, Lecture):
                        friday_courses_id.append(course.get_lecture_id())
                    else:
                        friday_courses_id.append(course.get_lab_id())
                    friday_courses_place.append(course_session.get_course_place()[j])
                    friday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Cumartesi":
                    if isinstance(course, Lecture):
                        saturday_courses_id.append(course.get_lecture_id())
                    else:
                        saturday_courses_id.append(course.get_lab_id())
                    saturday_courses_place.append(course_session.get_course_place()[j])
                    saturday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif course_session.get_course_day()[j] == "Pazar":
                    if isinstance(course, Lecture):
                        sunday_courses_id.append(course.get_lecture_id())
                    else:
                        sunday_courses_id.append(course.get_lab_id())
                    sunday_courses_place.append(course_session.get_course_place()[j])
                    sunday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

        monday_courses = ""
        tuesday_courses = ""
        wednesday_courses = ""
        thursday_courses = ""
        friday_courses = ""
        saturday_courses = ""
        sunday_courses = ""

        monday_course_place = ""
        tuesday_course_place = ""
        wednesday_course_place = ""
        thursday_course_place = ""
        friday_course_place = ""
        saturday_course_place = ""
        sunday_course_place = ""

        weekly_schedule = WeeklySchedule()
        course_session_times = CourseSessionTimes()

        for k in range(len(course_session_times.SESSION_START)):
            monday_courses = weekly_schedule.print_monday_courses(
                monday_courses_id,
                monday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            tuesday_courses = weekly_schedule.print_tuesday_courses(
                tuesday_courses_id,
                tuesday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            wednesday_courses = weekly_schedule.print_wednesday_courses(
                wednesday_courses_id,
                wednesday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            thursday_courses = weekly_schedule.print_thursday_courses(
                thursday_courses_id,
                thursday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            friday_courses = weekly_schedule.print_friday_courses(
                friday_courses_id,
                friday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            saturday_courses = weekly_schedule.print_saturday_courses(
                saturday_courses_id,
                saturday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            sunday_courses = weekly_schedule.print_sunday_courses(
                sunday_courses_id,
                sunday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )

            monday_course_place = weekly_schedule.print_monday_course_place(
                monday_courses_place,
                monday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            tuesday_course_place = weekly_schedule.print_tuesday_course_place(
                tuesday_courses_place,
                tuesday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            wednesday_course_place = weekly_schedule.print_wednesday_course_place(
                wednesday_courses_place,
                wednesday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            thursday_course_place = weekly_schedule.print_thursday_course_place(
                thursday_courses_place,
                thursday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            friday_course_place = weekly_schedule.print_friday_course_place(
                friday_courses_place,
                friday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            saturday_course_place = weekly_schedule.print_saturday_course_place(
                saturday_courses_place,
                saturday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )
            sunday_course_place = weekly_schedule.print_sunday_course_place(
                sunday_courses_place,
                sunday_courses_start_time,
                course_session_times.SESSION_START,
                k,
            )

            if (
                monday_courses != ""
                or tuesday_courses != ""
                or wednesday_courses != ""
                or thursday_courses != ""
                or friday_courses != ""
                or saturday_courses != ""
                or sunday_courses != ""
            ):
                print(
                    "------------------------------------------------------------------------------------------------------------------------------------------"
                )
                print(
                    "|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n"
                    % (
                        "",
                        monday_courses,
                        tuesday_courses,
                        wednesday_courses,
                        thursday_courses,
                        friday_courses,
                        saturday_courses,
                        sunday_courses,
                    )
                )
                print(
                    "|  %-15s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|  %-14s|%n"
                    % (
                        course_session_times.SESSION_START[k]
                        + " - "
                        + course_session_times.SESSION_END[k],
                        monday_course_place,
                        tuesday_course_place,
                        wednesday_course_place,
                        thursday_course_place,
                        friday_course_place,
                        saturday_course_place,
                        sunday_course_place,
                    )
                )

        print(
            "------------------------------------------------------------------------------------------------------------------------------------------\n"
        )

        while True:
            print(
                self.__colors.get_yellow()
                + "0"
                + self.__colors.get_reset()
                + ".  Back to Student Menu"
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
            case_token_line = input()
            if len(case_token_line) > 1:
                print(
                    self.__colors.get_yellow()
                    + "Invalid input format! Please give a number!"
                    + self.__colors.get_reset()
                )
                continue

            case_token = case_token_line[0]
            print(self.__colors.get_reset(), end="")
            if case_token == "0":
                return
            else:
                print(
                    self.__colors.get_yellow()
                    + "Invalid input! Please try again."
                    + self.__colors.get_reset()
                )
                continue
