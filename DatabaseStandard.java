import java.util.HashMap;

public class DatabaseStandard implements DatabaseInterface {

    private HashMap<String, String> database;

    public DatabaseStandard() {

        database = new HashMap<String, String>();
    }

    public String save(String plainPassword, String encryptedPassword) {

        return database.put(encryptedPassword, plainPassword);
    }

    public String decrypt(String encryptedPassword) {
        return null; // Change this!!
    }

    public int size() {
        return database.size();
    }

    public void printStatistics() {
        System.out.println("*** DatabaseStandard Statistics ***");
        System.out.println("Size is " + size() + " passwords");
        System.out.println("Initial Number of Indexes when Created " + "CHANGE THIS");
        System.out.println("*** End DatabaseStandard Statistics ***");
    }

}