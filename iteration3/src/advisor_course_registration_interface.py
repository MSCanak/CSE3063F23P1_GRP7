import json
class AdvisorCourseRegistrationInterface:
    def __init__(self, session, advisor_int):
        self.session = session
        self.selection_courses = []
        self.id_to_selection_courses = {}
        self.courses = []
        self.id_to_courses = {}
        self.course_to_lectures = {}
        self.id_to_course_lectures = {}
        self.course_to_labs = {}
        self.id_to_course_labs = {}
        self.messages_interface = None
        self.advisor_int = advisor_int
        self.requests_obj = None
        self.request_json = None
        self.cours_object = None
        self.course_json = None

        try:
            with open('./jsons/RegistrationRequests.json') as requests_file:
                self.requests_obj = json.load(requests_file)
            with open('./jsons/courses.json') as courses_file:
                self.cours_object = json.load(courses_file)

        except Exception as e:
            print(e)

    def adv_reg_menu(self):
        while True:
            print("\n>> Advisor Course Registration Menu")
            print("1. Show Students")
            print("2. Approve/Deny Courses")
            print("3. Finalize Registration")
            print("4. Messages Menu")
            print("0. Go Back to Advisor Menu")
            
            choice = int(input("What do you want to do? "))
            
            if choice == 1:
                self.show_students()
                self.show_students_question_part()
            elif choice == 2:
                self.approve_courses_menu()
            elif choice == 3:
                self.finalize_registration_menu()
            elif choice == 4:
                self.messages_interface = MessagesInterface(self.session)
                self.messages_interface.messages_menu()
            elif choice == 0:
                return
            else:
                print("\nInvalid input! Please try again.")

    def show_students(self):
        advisor = self.session.user
        print("\n>>> Students")
        number_of_students = 1
        print("------------------------------------------------------------")
        print("| %6s | %-11s | %-33s |" % ("Number", "Student ID", "Student Name"))
        
        for student in advisor.students:
            print("------------------------------------------------------------")
            print("| %-6s | %-11s | %-33s |" % (number_of_students, student.id,
                                            student.name + " " + student.surname))
            number_of_students += 1

        print("------------------------------------------------------------\n")

    def show_students_question_part(self):
        print("0. Go back to the Advisor Course Registration Menu.\n")
        choice1 = input("What do you want to do? ")
        
        if choice1 == '0':
            return

    def approve_courses_menu(self):
        while True:
            advisor = self.session.user
            self.show_students()
            print("0. Go back to the Advisor Course Registration Menu.")
            choice = int(input("Select student for approval: "))
            
            if choice == 0:
                return
            
            student = advisor.students[choice - 1]
            print("\nStudent ID: %s\nStudent Name: %s %s\n" % (student.id, student.name, student.surname))
            student_id = student.id
            self.show_students_requested(student_id)

    def show_students_requested(self, student_id):
        number_of_courses = 1

        for request in self.request_json:
            request_id = request["StudentID"]

            if student_id == request_id:
                selected_courses = request["SelectedCourses"]
                selected_lectures = request["SelectedLectures"]
                selected_labs = request["SelectedLabs"]

                for course in selected_courses:
                    for lecture in selected_lectures:
                        if lecture.contains(course):
                            self.course_to_lectures[course] = lecture

                    for lab in selected_labs:
                        if lab.contains(course):
                            self.course_to_labs[course] = lab

                    self.courses.append(course)
                    number_of_courses += 1

                self.id_to_courses[student_id] = self.courses
                self.id_to_course_lectures[student_id] = self.course_to_lectures
                self.id_to_course_labs[student_id] = self.course_to_labs

                self.courses = []
                self.course_to_lectures = {}
                self.course_to_labs = {}

                for i in range(number_of_courses - 1):
                    print("%s. %s" % (i + 1, self.id_to_courses[student_id][i]))

                    if self.id_to_course_lectures[student_id][self.id_to_courses[student_id][i]] is not None:
                        print("Lecture:", self.id_to_course_lectures[student_id][self.id_to_courses[student_id][i]])

                    if self.id_to_course_labs[student_id][self.id_to_courses[student_id][i]] is not None:
                        print("Lab:", self.id_to_course_labs[student_id][self.id_to_courses[student_id][i]])

                    print("----------------------------------")

        self.approve_course(student_id)

    def approve_course(self, student_id):
        if not self.id_to_courses:
            print("No courses to approve! Please select a different student!")
            return

        print("0. Go back to the Advisor Course Registration Menu.")
        print("\nSelect course to approve: ")

        while True:
            choice = int(input())
            
            if choice == 0:
                break

            course = self.id_to_courses[student_id][choice - 1]

            if course in self.selection_courses:
                print("You have already approved this course! Press 0")
                continue
            else:
                self.selection_courses.append(course)

            print("%s is approved!" % course)
            print("If your selection is finished, you can press 0.")
            print("OR")
            print("Select course to approve: ")

        self.save_approval(student_id)

        receiver_id = student_id
        description = "Advisor has approved your courses!"
        sender_id = self.session.user.id
        notification = Notification(receiver_id, description, sender_id)
        notification.send_notification(sender_id)

    def save_approval(self, student_id):
        for request in self.request_json:
            request_id = request["StudentID"]

            if student_id == request_id:
                approved_courses_json_array = request["ApprovedCourses"]
                selected_courses = request["SelectedCourses"]
                approved_lecture_json_array = request["ApprovedLectures"]
                selected_lectures = request["SelectedLectures"]
                approved_lab_json_array = request["ApprovedLabs"]
                selected_labs = request["SelectedLabs"]

                course_to_labs_temp = self.id_to_course_labs[student_id]
                course_to_lecture_temp = self.id_to_course_lectures[student_id]

                for course in self.selection_courses:
                    approved_courses_json_array.append(course)

                    if course_to_labs_temp.get(course) is not None:
                        approved_lab_json_array.append(course_to_labs_temp[course])

                    if course_to_lecture_temp.get(course) is not None:
                        approved_lecture_json_array.append(course_to_lecture_temp[course])

                selected_courses.clear()
                selected_lectures.clear()
                selected_labs.clear()

        try:
            with open('./jsons/RegistrationRequests.json', 'w') as requests_file:
                json.dump(self.request_json, requests_file)

        except Exception as e:
            print(e)

    def finalize_registration_menu(self):
        self.show_students()
        print("0. Go back to the Advisor Course Registration Menu.")
        print("\nSelect student for finalization: ")
        choice = int(input())
        advisor = self.session.user

        if choice == 0:
            return

        cur_student = advisor.students[choice - 1]
        for_delete = None

        for request in self.request_json:
            for_delete = request
            finalized_array_list = []

            request_id = request["StudentID"]

            if cur_student.id != request_id:
                continue

            approved_courses_json_array = request["ApprovedCourses"]

            if not approved_courses_json_array:
                print("No courses to finalize! Please select a different student!")
                return

            for course in approved_courses_json_array:
                for course_list_object in self.course_json:
                    course_list = course_list_object
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
                with open('./jsons/student/' + filename) as student_file:
                    student = json.load(student_file)
                    transcript_obj = student["Transcript"]
                    sem_array = transcript_obj["Semester"]
                    taken_courses = student["TakenCourses"]
                    student_id = student["ID"]
                    course_to_labs_temp = self.id_to_course_labs[student_id]
                    course_to_lecture_temp = self.id_to_course_lectures[student_id]

                    for course in approved_courses_json_array:
                        taken_courses.append(course)

                        if course_to_labs_temp is not None and course_to_labs_temp.get(course) is not None:
                            taken_courses.append(course_to_labs_temp[course])

                        if course_to_lecture_temp is not None and course_to_lecture_temp.get(course) is not None:
                            taken_courses.append(course_to_lecture_temp[course])

                    new_courses = {"Courses": finalized_array_list}
                    sem_array.append(new_courses)

                    with open('./jsons/student/' + filename, 'w') as student_file:
                        json.dump(student, student_file)

                    receiver_id = student_id
                    description = "Advisor has finalized your courses!"
                    sender_id = advisor.id
                    notification = Notification(receiver_id, description, sender_id)
                    notification.send_notification(sender_id)

                    print("Courses of student %s are finalized!" % student_id)

            except Exception as e:
                print(e)

        try:
            self.request_json.remove(for_delete)

            with open('./jsons/RegistrationRequests.json', 'w') as request_file:
                json.dump(self.request_json, request_file)

        except Exception as e:
            print(e)
