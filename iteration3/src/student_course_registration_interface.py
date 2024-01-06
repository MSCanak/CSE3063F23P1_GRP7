from datetime import datetime
import json

from colors import Colors
from messages_interface import MessagesInterface
from notification import Notification
from session import Session
from course import Course
from course_session import CourseSession
from lecture import Lecture
from lab import Lab
import logging



class StudentCourseRegistrationInterface:
    logging.basicConfig(filename='./logs/app.log', filemode='w', format='%(name)s - %(levelname)s - %(message)s', level=logging.INFO)
    def __init__(self, session: Session):
        self.__session = session

        self.__available_courses: list[Course] = []
        self.__available_lectures: list[Lecture] = []
        self.__available_labs: list[Lab] = []

        self.__selected_courses: list[Course] = []
        self.__selected_lectures: list[Lecture] = []
        self.__selected_labs: list[Lab] = []

        self.__all_courses: list[Course] = []
        self.__all_lectures: list[Lecture] = []
        self.__all_labs: list[Lab] = []

        self.__get_all_courses()  # Load all courses from courses.json
        self.__set_all_lectures_and_labs()  # Load all lectures and labs from CoursesOffered.json
        self.__registration_process: str = 'Draft'
        self.__colors = Colors()

    def __try_parse_int(self, value: str) -> int or None:
        try:
            return int(value)
        except ValueError:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), ValueError))
            return None

    def __check_conflict(
        self, selected_lecture: Lecture, selected_lab: Lab = None
    ) -> bool:
        if self.__check_conflict_lecture(selected_lecture):
            return True
        if selected_lab and self.__check_conflict_lab(selected_lab):
            return True
        return False

    def __check_conflict_lecture(self, selected_lecture: Lecture) -> bool:
        selected_lecture_course_session = selected_lecture.get_course_session()
        selected_lecture_course_days = selected_lecture_course_session.get_course_day()
        selected_lecture_course_start_times = (
            selected_lecture_course_session.get_course_start_time()
        )

        for lecture in self.__selected_lectures:
            lecture_course_session = lecture.get_course_session()
            lecture_course_days = lecture_course_session.get_course_day()
            lecture_course_start_times = lecture_course_session.get_course_start_time()

            for i in range(len(selected_lecture_course_days)):
                for j in range(len(lecture_course_days)):
                    if selected_lecture_course_days[i] == lecture_course_days[j]:
                        if (
                            selected_lecture_course_start_times[i]
                            == lecture_course_start_times[j]
                        ):
                            print(
                                "\nThere is a conflict between {} and {}!".format(
                                    selected_lecture.get_lecture_id(),
                                    lecture.get_lecture_id(),
                                )
                            )
                            return True
        return False

    def __check_conflict_lab(self, selected_lab: Lab) -> bool:
        selected_lab_course_session: CourseSession = selected_lab.get_course_session()
        selected_lab_course_days = selected_lab_course_session.get_course_day()
        selected_lab_course_start_times = (
            selected_lab_course_session.get_course_start_time()
        )

        for lab in self.__selected_labs:
            lab_course_session = lab.get_course_session()
            lab_course_days = lab_course_session.get_course_day()
            lab_course_start_times = lab_course_session.get_course_start_time()

            for i in range(len(selected_lab_course_days)):
                for j in range(len(lab_course_days)):
                    if selected_lab_course_days[i] == lab_course_days[j]:
                        if (
                            selected_lab_course_start_times[i]
                            == lab_course_start_times[j]
                        ):
                            print(
                                "\nThere is a conflict between {} and {}!".format(
                                    selected_lab.get_lab_id(), lab.get_lab_id()
                                )
                            )
                            return True
        return False

    def stu_reg_menu(self) -> None:
        while True:
            print("\n>> Student Course Registration System")
            self.__show_student_inf()
            print("1. Selected Courses Menu")
            print("2. Available Courses Menu")
            print("3. Messages Menu")
            print("0. Go back to Student Menu")
            choice = input("--> What do you want to do? ")

            if choice == "1":
                self.__selected_courses_menu()
            elif choice == "2":
                self.__available_courses_menu()
            elif choice == "3":
                messages_interface = MessagesInterface(self.__session)
                messages_interface.messages_menu()
            elif choice == "0":
                return
            else:
                print("Invalid input! Please try again.")

    def __show_student_inf(self) -> None:
        self.__get_registration_process()
        print("\n--------------------------------------------------------")
        print(
            "| {:<45} {:<30} |".format(
                "Student ID - Name and Surname:",
                "{} - {} {}".format(
                    self.__session.get_user().get_id(),
                    self.__session.get_user().get_name(),
                    self.__session.get_user().get_surname(),
                ),
            )
        )
        print(
            "| {:<45} {:<30} |".format(
                "Advisor:",
                "{} {}".format(
                    self.__session.get_user().get_advisor().get_name(),
                    self.__session.get_user().get_advisor().get_surname(),
                ),
            )
        )
        print(
            "| {:<45} {:<30} |".format(
                "Semester:", self.__session.get_user().get_current_semester()
            )
        )
        print(
            "| {:<45} {:<30} |".format(
                "Registration Process:", self.__registration_process
            )
        )
        print("--------------------------------------------------------")

    def __get_registration_process(self) -> None:
        try:
            student_id = self.__session.get_user().get_id()
            registration_requests_json = "jsons/RegistrationRequests.json"
            with open(registration_requests_json, "r", encoding='utf-8') as file:
                registration_requests_array = json.load(file)

            is_exists = False
            for registration_request_json in registration_requests_array:
                if registration_request_json["StudentID"] == student_id:
                    is_exists = True
                    approved_courses = registration_request_json["ApprovedCourses"]
                    if len(approved_courses) == 0:
                        self.__registration_process = "In Advisors Approval"
                    else:
                        self.__registration_process = "Advisor Approved"
                    break

            if not is_exists:
                self.__registration_process = "Draft"
        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))

            print(e)

    def __selected_courses_menu(self) -> None:
        while True:
            print(
                self.__colors.get_bold()
                + self.__colors.get_red()
                + "\n>>> Selected Course Menu"
                + self.__colors.get_reset()
                + self.__colors.get_reset()
            )
            self.__show_student_inf()
            self.__show_selected_courses()

            print(
                self.__colors.get_yellow()
                + "1"
                + self.__colors.get_reset()
                + ".   Delete selected courses"
            )
            print(
                self.__colors.get_yellow()
                + "2"
                + self.__colors.get_reset()
                + ".   Send registration request"
            )
            print(
                self.__colors.get_yellow()
                + "0"
                + self.__colors.get_reset()
                + ".   Go back to Student Course Registration System"
            )
            print(
                "\n"
                + self.__colors.get_blue()
                + "--> "
                + self.__colors.get_reset()
                + "What do you want to do?   "
            )

            choice = input(self.__colors.get_blue())
            print(self.__colors.get_reset())

            if not self.__selected_courses:
                print(
                    self.__colors.get_yellow()
                    + "\nAt present, no courses have been selected!"
                    + self.__colors.get_reset()
                )
                print(
                    "You will be "
                    + self.__colors.get_green()
                    + "redirected"
                    + self.__colors.get_reset()
                    + " to Student Course Registration System"
                )
                print("You can add courses from Available Courses Menu\n")
                break

            if choice == "1":
                self.__delete_selected_course_menu()
            elif choice == "2":
                self.__send_reg_request()
                # send notification
                receiver_id = self.__session.get_user().get_advisor().get_id()
                description = (
                    "Student "
                    + self.__session.get_user().get_id()
                    + " sent a registration request!"
                )
                sender_id = self.__session.get_user().get_id()
                notification = Notification(receiver_id, description, sender_id)
                notification.send_notification(sender_id)
            elif choice == "0":
                return
            else:
                print(
                    self.__colors.get_yellow()
                    + "Invalid input! Please try again."
                    + self.__colors.get_reset()
                )

    def __show_selected_courses(self) -> None:
        course_number = 1
        print(
            "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------"
        )
        print(
            "| %-8s | %-13s | %-50s| %-50s | %-8s| %-15s |"
            % ("Number", "CourseID", "CourseName", "Lecturer", "Credit", "CourseType")
        )
        print(
            "-----------------------------------------------------------------------------------------------------------------------------------------------------------------"
        )

        for lecture in self.__selected_lectures:
            print(
                "| %-8s | %-13s | %-50s| %-50s | %-8s| %-15s |"
                % (
                    course_number,
                    lecture.get_lecture_id(),
                    lecture.get_course_name(),
                    lecture.get_lecturer(),
                    lecture.get_credit(),
                    "Elective" if lecture.get_type() == "E" else "Mandatory",
                )
            )
            course_number += 1

        print(
            "-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n"
        )

    def __delete_selected_course_menu(self) -> None:
        while True:
            print(
                self.__colors.get_bold()
                + self.__colors.get_red()
                + "\n>>>> Delete Selected Course Menu"
                + self.__colors.get_reset()
                + self.__colors.get_reset()
            )
            self.__show_student_inf()

            print(
                self.__colors.get_yellow()
                + "1"
                + self.__colors.get_reset()
                + ".   Delete all selected courses"
            )
            print(
                self.__colors.get_yellow()
                + "2"
                + self.__colors.get_reset()
                + ".   Delete a selected course"
            )
            print(
                self.__colors.get_yellow()
                + "0"
                + self.__colors.get_reset()
                + ".   Go back to Selected Course Menu\n"
            )
            print(
                self.__colors.get_blue()
                + "--> "
                + self.__colors.get_reset()
                + "What do you want to do?   ",
                end="",
            )
            choice = input(self.__colors.get_blue())

            if choice == "1":
                confirmation = input(
                    self.__colors.get_blue()
                    + "--> "
                    + self.__colors.get_reset()
                    + "Do you want to delete selected courses? ("
                    + self.__colors.get_yellow()
                    + "y/n"
                    + self.__colors.get_reset()
                    + "): "
                )
                if confirmation.lower() == "y":
                    self.__selected_courses.clear()
                    self.__selected_lectures.clear()
                    self.__selected_labs.clear()
                    return

            elif choice == "2":
                print(
                    "Enter the number of the courses you want to delete (for example -> 1-2-3): "
                )
                print(
                    self.__colors.get_yellow()
                    + "0"
                    + self.__colors.get_reset()
                    + ".   Go back to Student Course Registration System"
                )
                print(
                    self.__colors.get_blue()
                    + "--> "
                    + self.__colors.get_reset()
                    + "What do you want to do?   ",
                    end="",
                )

                selected_indexes = []
                while True:
                    input_str = input(self.__colors.get_blue())
                    if not input_str:
                        print(
                            self.__colors.get_yellow()
                            + "Invalid choice"
                            + self.__colors.get_reset()
                        )
                        continue
                    selected_indexes = input_str.split("-")
                    if len(selected_indexes) > len(self.__selected_lectures):
                        print(
                            self.__colors.get_yellow()
                            + "Invalid choice"
                            + self.__colors.get_reset()
                        )
                        continue
                    is_invalid = False
                    for selected_index in selected_indexes:
                        if not selected_index.isdigit() or int(selected_index) > len(
                            self.__selected_lectures
                        ):
                            print(
                                self.__colors.get_yellow()
                                + "Invalid choice"
                                + self.__colors.get_reset()
                            )
                            is_invalid = True
                            break
                    if is_invalid:
                        continue
                    break

                if selected_indexes[0] == "0":
                    break

                confirmation = input(
                    self.__colors.get_blue()
                    + "--> "
                    + self.__colors.get_reset()
                    + "Do you want to delete selected courses? ("
                    + self.__colors.get_yellow()
                    + "y/n"
                    + self.__colors.get_reset()
                    + "): "
                )
                if confirmation.lower() == "y":
                    self.__delete_selected_courses(selected_indexes)
                return

            elif choice == "0":
                return

            else:
                print(
                    self.__colors.get_yellow()
                    + "Invalid choice"
                    + self.__colors.get_reset()
                )

    def __delete_selected_courses(self, selected_indexes: list[int]) -> None:
        selected_lectures_copy = list(self.__selected_lectures)

        for selected_index in selected_indexes:
            index = int(selected_index)
            lecture = selected_lectures_copy[index - 1]

            for lab in self.__selected_labs:
                if lecture.get_lecture_id() in lab.get_lab_id():
                    self.__selected_labs.remove(lab)
                    break

            self.__selected_lectures.remove(lecture)
            self.__selected_courses.remove(lecture)

    def __send_reg_request(self) -> None:
        try:
            existing_registration_array = []

            try:
                with open("jsons/RegistrationRequests.json", "r", encoding='utf-8') as file:
                    existing_registration_array = json.load(file)
            except json.JSONDecodeError:
                pass

            selected_courses_json_array = [
                course.get_course_id() for course in self.__selected_courses
            ]
            selected_lectures_json_array = [
                lecture.get_lecture_id() for lecture in self.__selected_lectures
            ]
            selected_labs_json_array = [
                lab.get_lab_id() for lab in self.__selected_labs
            ]

            registration_json = {
                "StudentID": self.__session.get_user().get_id(),
                "SelectedCourses": selected_courses_json_array,
                "SelectedLectures": selected_lectures_json_array,
                "SelectedLabs": selected_labs_json_array,
                "ApprovedCourses": [],
                "ApprovedLectures": [],
                "ApprovedLabs": [],
            }

            existing_registration_array.append(registration_json)

            with open("jsons/RegistrationRequests.json", "w", encoding='utf-8') as file:
                json.dump(existing_registration_array, file)

            print(
                "\033[92mThe registration request sent to advisor successfully !\033[0m"
            )
            logging.info("time: {}, User {} select courses".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))


        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))

            print(e)

    def __available_courses_menu(self) -> None:
        while True:
            print("\n>>> Available Course Menu")
            self.__show_student_inf()

            if len(self.__selected_courses) >= 5:
                print("5 courses already selected")
                print("\033[93m0. Go back to Student Course Registration System\033[0m")
                choice = input("--> What do you want to do? ")
                if choice == "0":
                    return
                else:
                    print("\033[93mInvalid input\033[0m")
                    continue

            self.__calculate_available_courses()
            self.__show_available_lectures()

            print("Select courses you want to add (for example -> 1): ")
            print("\033[93m0. Go back to Student Course Registration System\033[0m")
            choice = input("--> What do you want to do? ")

            if choice == "0":
                return
            elif self.__try_parse_int(choice) is None or not (
                1 <= int(choice) <= len(self.__available_lectures)
            ):
                print("\033[93mInvalid input\033[0m")
                continue

            is_labs_available = self.__show_available_labs(int(choice))

            if is_labs_available:
                lab_input = input("Select labs you want to add (for example -> 1): ")
                print("\033[93m0. Go back to Student Course Registration System\033[0m")

                if lab_input == "0":
                    break
                elif self.__try_parse_int(lab_input) is None or not (
                    1 <= int(lab_input) <= len(self.__available_labs)
                ):
                    print("\033[93mInvalid input\033[0m")
                    continue

                selected_lecture: Lecture = self.__available_lectures[int(choice) - 1]
                selected_lab: Lab = self.__available_labs[int(lab_input) - 1]
                has_conflict: bool = self.__check_conflict(
                    selected_lecture, selected_lab
                )

                if has_conflict:
                    continue

                self.__save_available_courses(selected_lecture, selected_lab)
                print("\033[92mSelected lecture and lab added !\033[0m")
                logging.info("time: {}, User {} select courses".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id()))


            else:
                selected_lecture: Lecture = self.__available_lectures[int(choice) - 1]
                has_conflict: bool = self.__check_conflict(selected_lecture)

                if has_conflict:
                    continue

                self.__save_available_courses(selected_lecture)
                print("\033[93mSelected lecture added only !\033[0m")

            print("\033[93m1. Select another course\033[0m")
            print("\033[93m0. Go back to Student Course Registration System\033[0m")
            choice = input("--> What do you want to do? ")

            if choice == "0":
                return
            elif choice == "1":
                continue
            else:
                print("\033[93mInvalid input\033[0m")

    def __find_course_by_id(self, courses: list[Course], course_id: str) -> Course:
        for course in courses:
            if course.get_course_id() == course_id:
                return course
        return None  # returns when the course is not found

    def __create_course_session(self, course_day_time_location: str) -> CourseSession:
        # Parse the string and create a course __session object
        parts = course_day_time_location.strip().split(" ")
        course_day = []
        course_start_time = []
        course_end_time = []
        course_place = []

        # Each object in the list is a different course __session
        for i in range(0, len(parts), 5):
            course_day.append(parts[i])
            course_start_time.append(parts[i + 1])
            course_end_time.append(parts[i + 3])
            course_place.append(parts[i + 4])

        return CourseSession(
            course_day, course_start_time, course_end_time, course_place
        )

    def __calculate_available_lectures(self) -> None:
        self.__available_lectures.clear()
        try:
            for lecture in self.__all_lectures:
                for available_course in self.__available_courses:
                    if lecture.get_course_id() == available_course.get_course_id():
                        self.__available_lectures.append(lecture)
        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def __calculate_available_labs(self, selected_lecture: Lecture) -> None:
        self.__available_labs.clear()
        try:
            for lab in self.__all_labs:
                for i in range(1, 20):  # 20 is the maximum number of labs for a lecture
                    # (e.g. CSE101.1.1, CSE101.1.2, ... CSE101.1.19, CSE101.1.20 etc)
                    if (
                        selected_lecture.get_lecture_id() + "." + str(i)
                        == lab.get_lab_id()
                    ):
                        self.__available_labs.append(lab)
        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def __calculate_available_courses(self) -> None:
        target_semester = self.__session.get_user().get_current_semester()
        gano = (
            self.__session.get_user().get_transcript().get_gano()[target_semester - 2]
        )

        self.__available_courses.clear()

        for course in self.__all_courses:
            if gano >= 3 and course.get_semester() >= target_semester:
                self.__available_courses.append(course)
            elif course.get_semester() == target_semester:
                self.__available_courses.append(course)

        try:
            stu_id = self.__session.get_user().get_id()
            student_json = f"jsons/student/{stu_id}.json"
            with open(student_json, "r", encoding='utf-8') as file:
                student_data: list = json.load(file)

                transcript = student_data["Transcript"]
                all_semester_array = transcript["Semester"]

                for semester in all_semester_array:
                    current_semester = semester
                    current_course_array = current_semester["Courses"]

                    for course_data in current_course_array:
                        course_id = course_data["CourseID"].strip()
                        course_grade = course_data["Grade"]

                        if isinstance(course_grade, int):
                            course_grade = float(course_grade)
                        elif isinstance(course_grade, float):
                            course_grade = float(course_grade)

                        course_obj = self.__find_course_by_id(
                            self.__all_courses, course_id
                        )

                        if course_obj is not None:
                            if course_grade <= 1:
                                if course_obj not in self.__available_courses:
                                    self.__available_courses.append(course_obj)

                                available_courses_copy = list(self.__available_courses)

                                for available_course in available_courses_copy:
                                    mandatory_prerequisites = (
                                        available_course.get_mandatory_prerequisite()
                                    )

                                    for (
                                        mandatory_prerequisite
                                    ) in mandatory_prerequisites:
                                        if (
                                            mandatory_prerequisite.get_course_id()
                                            == course_id
                                        ):
                                            self.__available_courses.remove(
                                                available_course
                                            )

            if not self.__available_courses:
                print("\nCurrently, there are no courses available!\n")

            if self.__selected_courses:
                available_courses_copy = list(self.__available_courses)

                for available_course in available_courses_copy:
                    for selected_course in self.__selected_courses:
                        if (
                            selected_course.get_course_id()
                            == available_course.get_course_id()
                        ):
                            self.__available_courses.remove(available_course)

            self.__calculate_available_lectures()

        except Exception as e:
            print(e)

    def __get_all_courses(self) -> None:
        try:
            with open("jsons/courses.json", encoding='utf-8') as file:
                courses_array = json.load(file)

            for course_json in courses_array:
                course_name = course_json["CourseName"]
                semester = int(course_json["Semester"])
                course_id = course_json["CourseID"].strip()
                credit = int(course_json["Credit"])
                course_type = course_json["Type"]

                mandatory_prerequisites = [
                    prerequisite.strip()
                    for prerequisite in course_json["MandatoryPrerequisites"]
                ]
                optional_prerequisites = [
                    prerequisite.strip()
                    for prerequisite in course_json["OptionalPrerequisites"]
                ]

                mandatory_prerequisite_list = [
                    self.__find_course_by_id(self.__all_courses, course_id)
                    for course_id in mandatory_prerequisites
                    if self.__find_course_by_id(self.__all_courses, course_id)
                ]
                optional_prerequisite_list = [
                    self.__find_course_by_id(self.__all_courses, course_id)
                    for course_id in optional_prerequisites
                    if self.__find_course_by_id(self.__all_courses, course_id)
                ]

                course = Course(
                    course_name=course_name,
                    course_id=course_id,
                    credit=credit,
                    course_type=course_type,
                    semester=semester,
                )
                course.set_mandatory_prerequisite(mandatory_prerequisite_list)
                course.set_optional_prerequisite(optional_prerequisite_list)
                self.__all_courses.append(course)

        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def __set_all_lectures_and_labs(self) -> None:
        try:
            with open("jsons/CoursesOffered.json", encoding='utf-8') as file:
                courses_offered_array = json.load(file)

            for course_json in courses_offered_array:
                course_id: str = course_json["CourseID"]
                course_name = course_json["CourseName"]
                quota = int(course_json["Quota"])
                course_day_time_location = course_json["CourseDayTimeLocation"]
                lecturer = course_json["Lecturer"]
                theoric = int(course_json["Theoric"])
                practice = int(course_json["Practice"])
                course_students = int(course_json["CourseStudents"])

                dot_count = course_id.count(".")
                if dot_count == 1:
                    course_session = self.__create_course_session(
                        course_day_time_location
                    )
                    lecture = Lecture(
                        course_name=course_name,
                        lecture_id=course_id,
                        quota=quota,
                        course_session=course_session,
                    )
                    corresponding_course = self.__find_course_by_id(
                        self.__all_courses, lecture.get_course_id()
                    )

                    if corresponding_course is not None:
                        corresponding_course.set_course_session(course_session)
                        lecture.set_type(corresponding_course.get_type())
                        lecture.set_credit(corresponding_course.get_credit())
                        lecture.set_semester(corresponding_course.get_semester())
                        lecture.set_lecturer(lecturer)
                        lecture.set_theoric(theoric)
                        lecture.set_practice(practice)
                        lecture.set_course_students(course_students)
                        lecture.set_mandatory_prerequisite(
                            corresponding_course.get_mandatory_prerequisite()
                        )
                        lecture.set_optional_prerequisite(
                            corresponding_course.get_optional_prerequisite()
                        )
                        self.__all_lectures.append(lecture)

                elif dot_count == 2:
                    course_session = self.__create_course_session(
                        course_day_time_location
                    )
                    lab = Lab(
                        course_name=course_name,
                        lab_id=course_id,
                        quota=quota,
                        course_session=course_session,
                    )
                    corresponding_course = self.__find_course_by_id(
                        self.__all_courses, lab.get_course_id()
                    )

                    if corresponding_course is not None:
                        corresponding_course.set_course_session(course_session)
                        lab.set_type(corresponding_course.get_type())
                        lab.set_credit(corresponding_course.get_credit())
                        lab.set_semester(corresponding_course.get_semester())
                        lab.set_lecturer(lecturer)
                        lab.set_theoric(theoric)
                        lab.set_practice(practice)
                        lab.set_course_students(course_students)
                        lab.set_mandatory_prerequisite(
                            corresponding_course.get_mandatory_prerequisite()
                        )
                        lab.set_optional_prerequisite(
                            corresponding_course.get_optional_prerequisite()
                        )
                        self.__all_labs.append(lab)

        except Exception as e:
            logging.error("time: {}, User {} error {}".format(datetime.now().strftime("%Y-%m-%d %H:%M:%S"), self.__session.get_user().get_id(), e))
            print(e)

    def __show_available_lectures(self) -> None:
        course_number = 1
        print(
            "\n{:<8}{:<13}{:<70}{:<8}{:<15}".format(
                "Number", "CourseID", "CourseName", "Credit", "CourseType"
            )
        )
        print(
            "--------------------------------------------------------------------------------------------------------------"
        )

        for lecture in self.__available_lectures:
            print(
                "{:<8}{:<13}{:<70}{:<8}{:<15}".format(
                    course_number,
                    lecture.get_lecture_id(),
                    lecture.get_course_name(),
                    lecture.get_credit(),
                    "Elective" if lecture.get_type() == "E" else "Mandatory",
                )
            )
            course_number += 1

        if course_number == 1:
            print("\n!!! Currently, there are no lectures available !!!\n")

    def __show_available_labs(self, selected_index: int) -> bool:
        course_number = 1
        print(
            "\n{:<8}{:<13}{:<70}{:<8}{:<15}".format(
                "Number", "CourseID", "CourseName", "Credit", "CourseType"
            )
        )
        print(
            "--------------------------------------------------------------------------------------------------------------"
        )
        selected_lecture = self.__available_lectures[selected_index - 1]

        self.__calculate_available_labs(selected_lecture)

        for available_lab in self.__available_labs:
            print(
                "{:<8}{:<13}{:<70}{:<8}{:<15}".format(
                    course_number,
                    available_lab.get_lab_id(),
                    available_lab.get_course_name(),
                    available_lab.get_credit(),
                    "Elective" if available_lab.get_type() == "E" else "Mandatory",
                )
            )
            course_number += 1

        if course_number == 1:
            print("\n!!! Currently, there are no labs available for this lecture !!!\n")
            return False
        return True

    def __save_available_courses(
        self, selected_lecture: Lecture, selected_lab: Lab = None
    ) -> None:
        self.__selected_courses.append(selected_lecture)
        self.__selected_lectures.append(selected_lecture)

        if selected_lab:
            self.__selected_labs.append(selected_lab)

        print("\nSelected Lecture: {}".format(selected_lecture.get_lecture_id()))

        if selected_lab:
            print("Selected Lab: {}".format(selected_lab.get_lab_id()))
