class WeeklySchedule:
    def print_courses(self, courses, courses_start_time, session_start, k):
        result = ""
        for i in range(len(courses)):
            if courses_start_time[i] == session_start[k]:
                result = courses[i]
        return result

    def print_course_places(self, course_places, courses_start_time, session_start, k):
        result = ""
        for i in range(len(course_places)):
            if courses_start_time[i] == session_start[k]:
                result = course_places[i]
        return result