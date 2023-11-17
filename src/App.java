public class App {

    public static void main(String[] args) throws Exception {
        // Create Login object and login
        Json json = new Json();

        //Person person = json.searchPerson("150101");
        //System.out.println(person.getID());
        //Student person = new Student("kadir", "bat", "kadirbatmarun", "5372073625", "150120012", "kadirr", "engineer", "cse", 3, null);
        //StudentInterface studentInterface = new StudentInterface(person,loginInterface);
       
       LoginInterface loginInterface = new LoginInterface();

        loginInterface.login();
    }
}