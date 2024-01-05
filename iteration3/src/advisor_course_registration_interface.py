import json
from notifications_interface import NotificationsInterface
from colors import Colors
from session import Session

# color need fix
class AdvisorCourseRegistrationInterface:
    def __init__(self, session : Session):
        self
        self.__session = session

        self.__selection_courses = []

        self.__courses = []
        self.__id_to_courses = {}

        self.__course_to_lectures = {}
        self.__id_to_course_lectures = {}

        self.__course_to_labs = {}
        self.__id_to_course_labs = {}

        # self.__messages_interface = MessagesInterface(self.__session)

        try:
            self.__request_file = json.load(open("./jsons/RegistrationRequests.json"))
            self.__courses_file = json.load(open("./jsons/courses.json"))
        
        except Exception as e:
            print(e)

    def adv_reg_menu(self):
        while True:
            # print(Colors.BOLD + Colors.RED + "\n>> Advisor Course Registration Menu\n" + Colors.RESET + Colors.RESET)
            # print(Colors.YELLOW + "1" + Colors.RESET + ".   Show Students")
            # print(Colors.YELLOW + "2" + Colors.RESET + ".   Approve/Deny Courses")
            # print(Colors.YELLOW + "3" + Colors.RESET + ".   Finalize Registration")
            # print(Colors.YELLOW + "4" + Colors.RESET + ".   Messages Menu")
            # print(Colors.YELLOW + "0" + Colors.RESET + ".   Go Back to Advisor Menu")
            # print("\n" + Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ", end="")

            choice = int(input())

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
                print(Colors.YELLOW + "\nInvalid input! Please try again." + Colors.RESET)

    def __show_students(self):
        advisor = self.__session.get_user()

        print(Colors.BOLD + Colors.RED + "\n>>> Students\n" + Colors.RESET + Colors.RESET)
        number_of_students = 1
        print("------------------------------------------------------------")

        print("| %6s | %-11s | %-33s |" % ("Number", "Student ID", "Student Name"))
        for student in advisor.get_students():
            print("------------------------------------------------------------")
            print("| %-6s | %-11s | %-33s |" % (number_of_students, student.get_id(),
                                            student.get_name() + " " + student.get_surname()))
            number_of_students += 1

        print("------------------------------------------------------------\n")

    def __show_students_question_part(self):
        print(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Advisor Course Registration Menu.\n")
        print(Colors.BLUE + "--> " + Colors.RESET + "What do you want to do?   ", end="")
        choice1 = input(Colors.BLUE)

        if choice1 == '0':
            return

    def __approve_courses_menu(self):
        while True:
            advisor = self.__session.get_user()

            self.__show_students()

            print(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Advisor Course Registration Menu.\n")
            print(Colors.BLUE + "--> " + Colors.RESET + "Select student for approval: ", end="")
            choice = int(input(Colors.BLUE))
            if choice == 0:
                return

            student = advisor.get_students()[choice - 1]
            print()
            print(Colors.GREEN + "Student ID: " + student.get_id() + "\n" +
                  "Student Name: " + student.get_name() + " " + student.get_surname() + Colors.RESET + "\n")
            student_id = student.get_id()
            self.__show_students_requested(student_id)

    def __show_students_requested(self, student_id):
        print(Colors.BOLD + Colors.RED + "\n>>> Requested Courses\n" + Colors.RESET + Colors.RESET)
        for request_obj in self.__request_file:
            request_id = request_obj["StudentID"]
            if student_id != request_id:
                continue
            

        self.__approve_course(student_id)

    def __approve_course(self, student_id):
        if not self.__id_to_courses:
            print(Colors.YELLOW + "No courses to approve! Please select a different student!" + Colors.RESET)
            return

        print(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Advisor Course Registration Menu.")
        print(Colors.BLUE + "\n--> " + Colors.RESET + "Select course to approve: ", end="")

        while True:
            choice = int(input(Colors.BLUE))
            if choice == 0:
                break

            course = self.__id_to_courses[student_id][choice - 1]
            if course in self.__selection_courses:
                print(Colors.YELLOW + "You have already approved this course! Press 0" + Colors.RESET)
                continue
            else:
                self.__selection_courses.append(course)

            print(Colors.GREEN + course + " is approved!" + Colors.RESET)
            print(Colors.BLUE + " If your selection is finished, you can press 0. " + Colors.RESET)
            print(Colors.RED + " OR " + Colors.BLUE + "\n--> " + Colors.RESET + "Select course to approve: ", end="")

        self.__save_approval(student_id)

        # send notification to student
        receiver_id = student_id
        description = "Advisor has approved your courses!"
        sender_id = self.__session.get_user().get_id()
        notification = NotificationsInterface(receiver_id, description, sender_id)
        notification.send_notification(sender_id)

    def __save_approval(self, student_id):
        for request_obj in self.__request_json:
            request = request_obj
            request_id = request["StudentID"]
            if student_id == request_id:
                approved_courses_json_array = request["ApprovedCourses"]
                selected_courses = request["SelectedCourses"]

                approved_lecture_json_array = request["ApprovedLectures"]
                selected_lectures = request["SelectedLectures"]

                approved_lab_json_array = request["ApprovedLabs"]
                selected_labs = request["SelectedLabs"]

                course_to_labs_temp = self.__id_to_course_labs[student_id]
                course_to_lecture_temp = self.__id_to_course_lectures[student_id]

                for course in self.__selection_courses:
                    approved_courses_json_array.append(course)
                    if course_to_labs_temp is not None and course_to_labs_temp.get(course) is not None:
                        approved_lab_json_array.append(course_to_labs_temp.get(course))
                    if course_to_lecture_temp is not None and course_to_lecture_temp.get(course) is not None:
                        approved_lecture_json_array.append(course_to_lecture_temp.get(course))

                selected_courses.clear()
                selected_lectures.clear()
                selected_labs.clear()

        try:
            with open("./jsons/RegistrationRequests.json", "w") as request_file:
                json.dump(self.__request_json, request_file)

        except Exception as e:
            print(e)

    def __finalize_registration_menu(self):
        self.__show_students()
        print(Colors.YELLOW + "0" + Colors.RESET + ".  Go back to the Advisor Course Registration Menu.\n")
        print(Colors.BLUE + "--> " + Colors.RESET + "Select student for finalization: ", end="")
        choice = int(input(Colors.BLUE))
        advisor = self.__session.get_user()

        if choice == 0:
            return

        cur_student = advisor.get_students()[choice - 1]
        for_delete = None

        for request_obj in self.__request_json:
            for_delete = request_obj
            finalized_array_list = []

            request = request_obj
            request_id = request["StudentID"]
            if cur_student.get_id() != request_id:
                continue

            approved_courses_json_array = request["ApprovedCourses"]
            if not approved_courses_json_array:
                print(Colors.YELLOW + "No courses to finalize! Please select a different student!" + Colors.RESET)
                return

            for course_obj in approved_courses_json_array:
                course = course_obj
                for course_list_obj in self.__course_json:
                    course_list = course_list_obj
                    course_id = course_list["CourseID"]
                    if course == course_id:
                        new_course_id = course_list["CourseID"]
                        new_course_name = course_list["CourseName"]
                        new_course_type = course_list["Type"]
                        new_course_credit = course_list["Credit"]
                        new_course_semester = course_list["Semester"]
                        new_course_grade = 0.0

                        new_course = {
                            "CourseID": new_course_id,
                            "CourseName": new_course_name,
                            "Type": new_course_type,
                            "Credit": new_course_credit,
                            "Semester": new_course_semester,
                            "Grade": new_course_grade
                        }

                        finalized_array_list.append(new_course)

            filename = request_id + ".json"

            try:
                with open("./jsons/student/" + filename, "r") as student_file:
                    student_obj = json.load(student_file)
                    student = student_obj
                    transcript_obj = student["Transcript"]
                    sem_array = transcript_obj["Semester"]
                    taken_courses = student["TakenCourses"]
                    student_id = student["ID"]
                    course_to_labs_temp = self.__id_to_course_labs[student_id]
                    course_to_lecture_temp = self.__id_to_course_lectures[student_id]

                    for course_obj in approved_courses_json_array:
                        course = course_obj
                        taken_courses.append(course)
                        if course_to_labs_temp is not None and course_to_labs_temp.get(course) is not None:
                            taken_courses.append(course_to_labs_temp.get(course))

                        if course_to_lecture_temp is not None and course_to_lecture_temp.get(course) is not None:
                            taken_courses.append(course_to_lecture_temp.get(course))

                    new_courses = {
                        "Courses": finalized_array_list
                    }

                    sem_array.append(new_courses)

                with open("./jsons/student/" + filename, "w") as student_file:
                    json.dump(student, student_file)

                # send notification to student
                receiver_id = student_id
                description = "Advisor has finalized your courses!"
                sender_id = advisor.get_id()
                notification = NotificationsInterface(receiver_id, description, sender_id)
                notification.send_notification(sender_id)

                print(Colors.GREEN + "Courses of student " + student_id + " are finalized!" + Colors.RESET)

            except Exception as e:
                print(e)

        try:
            self.__request_json.remove(for_delete)

            with open("./jsons/RegistrationRequests.json", "w") as request_file:
                json.dump(self.__request_json, request_file)

        except Exception as e:
            print(e)
