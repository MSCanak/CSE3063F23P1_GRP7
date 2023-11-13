public abstract class Person {
    // Attributes

    private String name;
    private String surname;
    private String email;
    private String phoneNumber; 
    private String ID;
    private String password;
    private String faculty;
    private String department;

    // Constructor

    public Person (String name, String surname, String email, String phoneNumber, String ID, String password, String faculty, String department) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ID = ID;
        this.password = password;
        this.faculty = faculty;
        this.department = department;
    }

    // Getters

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getDepartment() {
        return department;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Abstract methods

    public int getSemester() {
        return 0;
    }

    public Arraylist<Course> getCoursesTaken() {
        return null;
    }

    public Transcript getTranscript() {
        return null;
    }

    public Advisor getAdvisor() {
        return null;
    }

    public ArrayList<Student> getStudents() {
        return null;
    }
    
    public void setSemester(int semester) {
    }

    public void setCoursesTaken(ArrayList<Course> coursesTaken) {
    }

    public void setTranscript(Transcript transcript) {
    }

    public void setAdvisor(Advisor advisor) {
    }

    public void setStudents(ArrayList<Student> students) {
    }

}