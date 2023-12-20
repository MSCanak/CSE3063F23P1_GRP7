
import json
for i in range(0, 40):
    id = 150120000 + i

    fileName = (str)(id) + ".json"
    with open("./newone/" + fileName, "r") as f:
        students = json.load(f)

    totalCredit = 0
    totalGrade = 0
    for k in students["Transcript"]["Semester"]:
        semesterCredit = 0
        semesterGrade = 0

        for j in k["Courses"]:
            grade = j["Grade"]
            credit = j["Credit"]
            semesterCredit += credit
            semesterGrade += grade * credit
        
        totalGrade += semesterGrade
        totalCredit += semesterCredit
        yano = semesterGrade / semesterCredit
        gano = totalGrade / totalCredit
        k["SemesterInf"] = {}
        k["SemesterInf"]["Yano"] = yano
        k["SemesterInf"]["Gano"] = gano
        k["SemesterInf"]["TakenCredit"] = totalCredit
        k["SemesterInf"]["CompletedCredit"] = totalCredit


    with open(fileName, "w") as outfile:
        json.dump(students, outfile)