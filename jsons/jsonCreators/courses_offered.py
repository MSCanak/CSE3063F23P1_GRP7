import pandas as pd
import json

# Read Excel files
df1 = pd.read_excel("jsons/jsonCreators/20222023bahar.xlsx")
df2 = pd.read_excel("jsons/jsonCreators/20232024guz.xlsx")

# Concatenate df1 and df2
df = pd.concat([df1, df2], ignore_index=True)

# Drop specific columns
columns_to_remove = [
    "Öğrenci Sayısı (Kesin Kayıt)",
    "Öğrenci Sayısı (İlk Kez)",
    "Öğrenci Sayısı (Tekrar Alan)",
    "Öğrenci Sayısı (KY Devam Eden)",
    "Uzem Dersi",
]
df = df.drop(columns=columns_to_remove, errors="ignore")

# Convert the DataFrame to a list of dictionaries with the desired key names
courses = df.rename(
    columns={
        "Ders Kodu": "CourseCode",
        "Ders Adı": "CourseName",
        "Öğretim Elemanı": "Lecturer",
        "Teorik": "Theoretical",
        "Uygulama": "Practical",
        "Kredi": "Credit",
        "Kontenjan": "Quota",
        "Gün Saat Derslik": "CourseDayTimeLocation",
    }
).to_dict(orient="records")

# Write the list of dictionaries to a JSON file
with open("jsons/CoursesOffered.json", "w", encoding="utf-8") as outfile:
    json.dump(courses, outfile, indent=4, ensure_ascii=False)

print("CoursesOffered.json created")
