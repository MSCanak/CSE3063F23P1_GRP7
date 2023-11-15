public class App {

    public static void main(String[] args) throws Exception {
        // Create Login object and login
        Json json = new Json();

        Person person = json.searchPerson("150101");
        System.out.println(person.getID());
    }
}