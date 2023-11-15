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
    with open("iteration1/jsons/student/" + file, "r") as f:
        json_file = json.load(f)
        student = dict(
            Id=json_file["Id"],
            Name=json_file["Name"],
            Surname=json_file["Surname"],
            Password=json_file["Password"],
        )
        students.append(student)

# create a json file
with open("iteration1/jsons/students.json", "w") as f:
    # dump the list of dictionaries to the json file
    json.dump(students, f, indent=4)

# print the list of dictionaries
print(students)
