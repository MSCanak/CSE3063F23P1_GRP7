# select all students inside of iteration1/jsons/student/ folder
# and create a json file with all of them
# the json file will be saved in iteration1/jsons/students.json
# that json file will be consist of student objects that each have the following attributes:
#   - student id
#   - name
#   - surname

import json
import os

# get all files inside of iteration1/jsons/student/ folder
files = os.listdir("iteration1/jsons/student")
# create a list of student objects
students = []
# iterate over all files

for file in files:
    # open each file
    with open("iteration1/jsons/student" + file, "r") as f:
        # load the json file
        json_file = json.load(f)
        # create a student object
        student = (json_file["id"], json_file["name"], json_file["surname"])
        # append the student object to the list
        students.append(student)

# create a json file with all students

# create a list of dictionaries
students_dict = []
# iterate over all students
for student in students:
    # create a dictionary for each student
    student_dict = {"id": student.id, "name": student.name, "surname": student.surname}
    # append the dictionary to the list
    students_dict.append(student_dict)

# create a json file
with open("iteration1/jsons/students.json", "w") as f:
    # dump the list of dictionaries to the json file
    json.dump(students_dict, f, indent=4)

# print the list of dictionaries
print(students_dict)
