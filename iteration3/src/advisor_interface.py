from advisor_course_registration_interface import AdvisorCourseRegistrationInterface
from notifications_interface import NotificationsInterface
from session import Session
from lecture import Lecture


class AdvisorInterface:
    def __init__(self, session: Session):
        self.__session = session
        self.__adv_course_reg_int = AdvisorCourseRegistrationInterface(session=self.__session)

    def adv_menu(self):
        while True:
            print("\n> Advisor Menu")
            print("1. View Notifications")
            print("2. View Weekly Schedule")
            print("3. View Given Courses")
            print("4. Course Registration System")
            print("*. Logout")
            print("x. Exit")
            choice = input("\nWhat do you want to do? ")

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
                print("Invalid input! Please try again.")

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

        print("\n>> Weekly Schedule")
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

        while True:
            print("0.  Back to Advisor Menu")
            choice = input("\nWhat do you want to do? ")
            if choice == "0":
                return
            else:
                print("Invalid input! Please try again.")

    def __show_given_courses(self, courses):
        course_ids = []
        print("\n>> Given Courses")
        print("---------------------------------------------------------")
        print("|  Course ID  |  Course Name")

        for course in courses:
            if isinstance(course, Lecture):
                course_ids.append(course.get_lecture_id())
            else:
                course_ids.append(course.get_lab_id())

            print("---------------------------------------------------------")
            print(f"|  {course_ids[-1]:<11}|  {course.get_course_name():<35}")

        print("---------------------------------------------------------\n")

        while True:
            print("0.  Back to Advisor Menu")
            choice = input("\nWhat do you want to do? ")

            if choice == "0":
                return
            else:
                print("Invalid input! Please try again.")
