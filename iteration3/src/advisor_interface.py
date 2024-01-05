from advisor_course_registration_interface import AdvisorCourseRegistrationInterface
from notifications_interface import NotificationsInterface
from session import Session
from lecture import Lecture
from colors import Colors
from weekly_schedule import WeeklySchedule
from course_session_times import CourseSessionTimes


class AdvisorInterface:
    def __init__(self, session: Session):
        self.__session = session
        self.__adv_course_reg_int = AdvisorCourseRegistrationInterface(
            session=self.__session
        )
        self.__colors = Colors()
        

    def adv_menu(self):
        while True:
            print(
                f"{self.__colors.get_red()}{self.__colors.get_bold()}\n> Advisor Menu\n{self.__colors.get_reset()}"
            )
            print(
                f"{self.__colors.get_yellow()}1{self.__colors.get_reset()}.   View Notifications"
            )
            print(
                f"{self.__colors.get_yellow()}2{self.__colors.get_reset()}.   View Weekly Schedule"
            )
            print(
                f"{self.__colors.get_yellow()}3{self.__colors.get_reset()}.   View Given Courses"
            )
            print(
                f"{self.__colors.get_yellow()}4{self.__colors.get_reset()}.   Course Registration System"
            )
            print(f"{self.__colors.get_yellow()}*{self.__colors.get_reset()}.   Logout")
            print(f"{self.__colors.get_yellow()}x{self.__colors.get_reset()}.   Exit")

            print(
                "\n"
                + self.__colors.get_blue()
                + "--> "
                + self.__colors.get_reset()
                + "What do you want to do?   ",
                end="",
            )
            print(self.__colors.get_blue(), end="")
            choice = input()

            if choice == "1":
                notifications_int = NotificationsInterface(self.__session)
                notifications_int.notifications_menu()
            elif choice == "2":
                weekly_schedule = self.__calculate_weekly_schedule()
                self.__show_weekly_schedule(weekly_schedule)
            elif choice == "3":
                given_courses = self.__calculate_weekly_schedule()
                self.__show_given_courses(given_courses)
            elif choice == "4":
                self.__adv_course_reg_int.adv_reg_menu()
            elif choice == "*":
                self.__login_int.logout()
            elif choice == "x":
                self.__login_int.exit()
            else:
                print(
                    f"{self.__colors.get_yellow()}Invalid input! Please give a number!{self.__colors.get_reset()}"
                )

    def __calculate_weekly_schedule(self):
        # Assuming `session` contains the current session and `get_user()` returns the current user
        weekly_schedule = self.__session.get_user().get_given_courses()
        return weekly_schedule

    def __show_weekly_schedule(self, courses):
        # initializing lists for each day of the week
        (
            monday_courses_id,
            tuesday_courses_id,
            wednesday_courses_id,
            thursday_courses_id,
            friday_courses_id,
            saturday_courses_id,
            sunday_courses_id,
        ) = ([] for _ in range(7))
        (
            monday_courses_start_time,
            tuesday_courses_start_time,
            wednesday_courses_start_time,
            thursday_courses_start_time,
            friday_courses_start_time,
            saturday_courses_start_time,
            sunday_courses_start_time,
        ) = ([] for _ in range(7))
        (
            monday_courses_place,
            tuesday_courses_place,
            wednesday_courses_place,
            thursday_courses_place,
            friday_courses_place,
            saturday_courses_place,
            sunday_courses_place,
        ) = ([] for _ in range(7))

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
            "|  {:<15}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|".format(
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

        for course in courses:
            course_session = course.get_course_session()
            for j, day in enumerate(course_session.get_course_day()):
                if day == "Pazartesi":
                    if isinstance(course, Lecture):
                        monday_courses_id.append(course.get_lecture_id())
                    else:
                        monday_courses_id.append(course.get_lab_id())

                    monday_courses_place.append(course_session.get_course_place()[j])
                    monday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif day == "Salı":
                    if isinstance(course, Lecture):
                        tuesday_courses_id.append(course.get_lecture_id())
                    else:
                        tuesday_courses_id.append(course.get_lab_id())
                    tuesday_courses_place.append(course_session.get_course_place()[j])
                    tuesday_courses_start_time.append(
                        course_session.get_course_start_time()[j]
                    )

                elif day == "Çarşamba":
                    if isinstance(course, Lecture):
                        wednesday_courses_id.append(course.get_lecture_id())
                    else:
                        wednesday_courses_id.append(course.get_lab_id())
                    wednesday_courses_place.append(course_session.get_course_place()[j])
                    wednesday_courses_start_time.append(
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

        for k in range(len(course_session_times.session_start)):
            monday_courses = weekly_schedule.print_courses(
                monday_courses_id,
                monday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            tuesday_courses = weekly_schedule.print_courses(
                tuesday_courses_id,
                tuesday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            wednesday_courses = weekly_schedule.print_courses(
                wednesday_courses_id,
                wednesday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            thursday_courses = weekly_schedule.print_courses(
                thursday_courses_id,
                thursday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            friday_courses = weekly_schedule.print_courses(
                friday_courses_id,
                friday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            saturday_courses = weekly_schedule.print_courses(
                saturday_courses_id,
                saturday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            sunday_courses = weekly_schedule.print_courses(
                sunday_courses_id,
                sunday_courses_start_time,
                course_session_times.session_start,
                k,
            )

            monday_course_place = weekly_schedule.print_course_places(
                monday_courses_place,
                monday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            tuesday_course_place = weekly_schedule.print_course_places(
                tuesday_courses_place,
                tuesday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            wednesday_course_place = weekly_schedule.print_course_places(
                wednesday_courses_place,
                wednesday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            thursday_course_place = weekly_schedule.print_course_places(
                thursday_courses_place,
                thursday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            friday_course_place = weekly_schedule.print_course_places(
                friday_courses_place,
                friday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            saturday_course_place = weekly_schedule.print_course_places(
                saturday_courses_place,
                saturday_courses_start_time,
                course_session_times.session_start,
                k,
            )
            sunday_course_place = weekly_schedule.print_course_places(
                sunday_courses_place,
                sunday_courses_start_time,
                course_session_times.session_start,
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
                print("|  {:<15}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|".format(
                    "",
                    monday_courses,
                    tuesday_courses,
                    wednesday_courses,
                    thursday_courses,
                    friday_courses,
                    saturday_courses,
                    sunday_courses,
                ))

                print("|  {:<15}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|  {:<14}|".format(
                    course_session_times.session_start[k] + " - " + course_session_times.session_end[k],
                    monday_course_place,
                    tuesday_course_place,
                    wednesday_course_place,
                    thursday_course_place,
                    friday_course_place,
                    saturday_course_place,
                    sunday_course_place,
                ))


        print(
            "------------------------------------------------------------------------------------------------------------------------------------------\n"
        )


        while True:
            print(
                self.__colors.get_yellow()
                + "0"
                + self.__colors.get_reset()
                + ".  Back to Advisor Menu"
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

    def __show_given_courses(self, courses):
        course_ids = []
        print(
            self.__colors.get_red()
            + self.__colors.get_bold()
            + "\n>> Given Courses\n"
            + self.__colors.get_reset()
            + self.__colors.get_reset()
        )

        print("---------------------------------------------------------")
        print("|  Course ID  |  Course Name |")

        for course in courses:
            if isinstance(course, Lecture):
                course_ids.append(course.get_lecture_id())
            else:
                course_ids.append(course.get_lab_id())

            print("---------------------------------------------------------")
            print(f"|  {course_ids[-1]:<11} |  {course.get_course_name():<35}|")

        print("---------------------------------------------------------\n")

        while True:
            print(
                self.__colors.get_yellow()
                + "0"
                + self.__colors.get_reset()
                + ".  Back to Advisor Menu"
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
