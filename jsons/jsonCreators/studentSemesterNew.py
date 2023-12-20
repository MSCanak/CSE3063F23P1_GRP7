import json
import random
names = []
surnames = []
ids = []
passwards = []

faculty = "Engineering"
depart = "Computer Science Engineering"
phoneNumber = "0555555555"


with open("students.json", "r") as read_file:
    data = json.load(read_file)

with open("courses.json", "r") as read_file:
    courses = json.load(read_file)


for i in range (0,40):
    names.append(data[i]["Name"])
    surnames.append(data[i]["Surname"])
    ids.append(data[i]["ID"])
    passwards.append(data[i]["Password"])

    

            
for i in range(0, 40):

   
    
    id = ids[i]
    name = names[i]
    surname = surnames[i]
    password = passwards[i]

    if i < 10:
        semesterInt = 7
    elif i < 20:
        semesterInt = 5
    elif i < 30:
        semesterInt = 3
    else:
        semesterInt = 1

    with open(id + ".json", "r") as read_file:
        studentData = json.load(read_file)

    advisor = studentData["AdvisorID"]

    semester = []

    

    for k in range(1, semesterInt):
        add = {
                    "Courses": []
                }
        for course in courses:
            if course["Semester"] == k:
                newCourse = {
                    "CourseID": course["CourseID"],
                    "CourseName": course["CourseName"],
                    "Credit": course["Credit"],
                    "Type": course["Type"],
                    "Semester": course["Semester"],
                    "Grade": 0
                }
                add["Courses"].append(newCourse)
        
        if add["Courses"] != []:
            semester.append(add)
            add = {
                    "Courses": []
                }

    students = {
        "ID": id,
        "Name": name,
        "Surname": surname,
        "Password": password,
        "EMail": name + "@marun.edu.tr",
        "Faculty": faculty,
        "Department": depart,
        "AdvisorID": advisor,
        "PhoneNumber": phoneNumber,
        "Semester": semesterInt,
        "TakenCourses": [],
        "Transcript": {
            "Semester": semester
        }
    }


    

#     # for i in students["Transcript"]["Semester"]:
#     # semesterCredit = 0
#     # for j in i["Courses"]:
#     #     semesterCredit += j["Credit"]
#     #     print(semesterCredit)

    for k in students["Transcript"]["Semester"]:
        for j in k["Courses"]:
            numbers = [0, 1, 1.5, 2, 2.5, 3, 3.5, 4]
            weights = [5, 10, 10, 25, 25, 25, 25, 10]

            selected_number = random.choices(numbers, weights=weights)[0]

            j["Grade"] = selected_number

            # print(j["Grade"])

    with open("./newone/" + id + ".json", "w") as outfile:
        json.dump(students, outfile)



