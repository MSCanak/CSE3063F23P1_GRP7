import unittest
from advisor import Advisor
from student import Student
from course import Course
from course_session import CourseSession
from lecturer import Lecturer
from message import Message
from notification import Notification
from login_interface import LoginInterface

class Tests(unittest.TestCase):
    login_interface = LoginInterface()

    advisor = Advisor("Murat", "Ganiz", "murat@mail.com", "055", "150101", "password", "Eng", "CSE", "DR.")
    student = Student("Ensar", "Yozgat", "ensar@mail.com", "051", "150120000", "pass","Eng", "CSE", 5, advisor)
    
    advisor.set_student(student)

    course = Course("CSE 101", "101", 3, "Mandatory", 1, 0, None, None, 3, 0, 0, 0, 0, None)
    course_session = CourseSession("Monday", "09:00", "11:00", "A-101")

    lecturer = Lecturer("Betul", "boz", "boz@mail.com", "0555", "102", "passw", "Eng", "CSE", course ,"DR.")

    message = Message("senderID", "receiverID", "description", "subject")
    notification = Notification( "receiverID", "description","senderID", False ,2)

    def test_advisor(self):
        self.assertEqual(self.advisor.get_name(), "Murat")
        self.assertEqual(self.advisor.get_surname(), "Ganiz")
        self.assertEqual(self.advisor.get_email(), "murat@mail.com")
        self.assertEqual(self.advisor.get_phone_number(), "055")
        self.assertEqual(self.advisor.get_id(), "150101")
        self.assertEqual(self.advisor.get_password(), "password")
        self.assertEqual(self.advisor.get_faculty(), "Eng")
        self.assertEqual(self.advisor.get_department(), "CSE")
        self.assertEqual(self.advisor.get_academic_title(), "DR.")

    def test_advisor_students(self):
        self.assertEqual(self.advisor.get_students(), [self.student])

    def test_student(self):
        self.assertEqual(self.student.get_name(), "Ensar")
        self.assertEqual(self.student.get_surname(), "Yozgat")
        self.assertEqual(self.student.get_email(), "ensar@mail.com")
        self.assertEqual(self.student.get_phone_number(), "051")
        self.assertEqual(self.student.get_id(), "150120000")
        self.assertEqual(self.student.get_password(), "pass")
        self.assertEqual(self.student.get_faculty(), "Eng")
        self.assertEqual(self.student.get_department(), "CSE")

    def test_student_advisor(self):
        self.assertEqual(self.student.get_advisor(), self.advisor)
        self.assertEqual(self.student.get_advisor().get_id(), "150101")

    def test_course(self):
        self.assertEqual(self.course.get_course_name(), "CSE 101")
        self.assertEqual(self.course.get_course_id(), "101")
        self.assertEqual(self.course.get_credit(), 3)
        self.assertEqual(self.course.get_course_type(), "Mandatory")
        self.assertEqual(self.course.get_semester(), 1)
        self.assertEqual(self.course.get_grade(), 0)
        self.assertEqual(self.course.get_optional_prerequisite(), [])
        self.assertEqual(self.course.get_mandatory_prerequisite(), [])
        self.assertEqual(self.course.get_theoric(), 3)
        self.assertEqual(self.course.get_practice(), 0)
        self.assertEqual(self.course.get_course_students(), 0)
        self.assertEqual(self.course.get_lecturer(), 0)
        self.assertEqual(self.course.get_quota(), 0)
        self.assertEqual(self.course.get_course_session(), None)

    def test_course_session(self):
        self.assertEqual(self.course_session.get_course_day(), "Monday")
        self.assertEqual(self.course_session.get_course_start_time(), "09:00")
        self.assertEqual(self.course_session.get_course_end_time(), "11:00")
        self.assertEqual(self.course_session.get_course_place(), "A-101")

    def test_course_session_course(self):
        self.course.set_course_session(self.course_session)
        self.assertEqual(self.course.get_course_session(), self.course_session)

    def test_lecturer(self):
        self.assertEqual(self.lecturer.get_name(), "Betul")
        self.assertEqual(self.lecturer.get_surname(), "boz")
        self.assertEqual(self.lecturer.get_email(), "boz@mail.com")
        self.assertEqual(self.lecturer.get_phone_number(), "0555")
        self.assertEqual(self.lecturer.get_id(), "102")
        self.assertEqual(self.lecturer.get_password(), "passw")
        self.assertEqual(self.lecturer.get_faculty(), "Eng")
        self.assertEqual(self.lecturer.get_department(), "CSE")
        self.assertEqual(self.lecturer.get_academic_title(), "DR.")
        self.assertEqual(self.lecturer.get_given_courses(), self.course)

    def test_message(self):
        self.assertEqual(self.message.get_sender_id(), "senderID")
        self.assertEqual(self.message.get_receiver_id(), "receiverID")
        self.assertEqual(self.message.get_description(), "description")
        self.assertEqual(self.message.get_subject(), "subject")
    
    def test_notification(self):
        self.assertEqual(self.notification.get_sender_id(), "senderID")
        self.assertEqual(self.notification.get_receiver_id(), "receiverID")
        self.assertEqual(self.notification.get_description(), "description")
        self.assertEqual(self.notification.get_notification_id(), 2)
        self.assertEqual(self.notification.get_is_read(), False)

    def test_login_interface(self):
        self.assertEqual(self.login_interface.login())
unittest.main()