import java.util.HashMap;

/*
DatabaseStandard stores all the passwords (value) and 
their respective hash code (key) in the java implementation 
of a HashMap (java.util.HashMap)
*/
public class DatabaseStandard implements DatabaseInterface {

    // Defining the hash map as a class variable
    private HashMap<String, String> database;

    /*
    DatabaseStandard constructor used for initializing the hash map
    */
    public DatabaseStandard() {
        database = new HashMap<String, String>();
    }

    /*
    Stores the encrpytedPassword and plainPassword (key,value) in the hash map
    */
    public String save(String plainPassword, String encryptedPassword) {
        return database.put(encryptedPassword, plainPassword);
    }

    /*
    Returns the password corresponding to the encryptedPassword given 
    */
    public String decrypt(String encryptedPassword) {
        if (database.get(encryptedPassword) == null) {
            return "";
        }
        return database.get(encryptedPassword);
    }

    /*
    Returns the size of the current size of the hash map (number of entries)
    */
    public int size() {
        return database.size();
    }

    /*
    Prints relevant information concerning the database
    */
    public void printStatistics() {
        System.out.println("*** DatabaseStandard Statistics ***");
        System.out.println("Size is " + size() + " passwords");
        System.out.println("Initial Number of Indexes when Created 37");
        System.out.println("*** End DatabaseStandard Statistics ***");
    }

}