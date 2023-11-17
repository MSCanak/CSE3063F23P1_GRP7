import pandas as pd
import json

def excel_to_json(excel_file_path, json_file_path):
    # Read the Excel file into a DataFrame
    df = pd.read_excel(excel_file_path)

    # Convert the DataFrame to a list of dictionaries (each dictionary represents a course)
    courses = []
    for _, row in df.iterrows():
        optional_prerequisites = []
        mandatory_prerequisites = []

        if not pd.isna(row["OptionalPrerequisite"]):
            optional_prerequisites.extend(row["OptionalPrerequisite"].split(","))
        if not pd.isna(row["MandatoryPrerequisite"]):
            mandatory_prerequisites.extend(row["MandatoryPrerequisite"].split(","))

        course = {
            "CourseID": row["CourseID"],
            "CourseName": row["CourseName"],
            "CourseType": row["CourseType"],
            "Credit": int(row["Credit"]),
            "Semester": int(row["Semester"]),
            "OptionalPrerequisites": optional_prerequisites,
            "MandatoryPrerequisites": mandatory_prerequisites
        }
        courses.append(course)

    # Write the list of dictionaries to a JSON file
    with open(json_file_path, 'w') as json_file:
        json.dump(courses, json_file, indent=2)

# Example usage:
excel_file_path = "D:/Projects/Python/json_writer/courses.xlsx"
json_file_path = "D:/Projects/Python/json_writer/Courses.json"
excel_to_json(excel_file_path, json_file_path)
