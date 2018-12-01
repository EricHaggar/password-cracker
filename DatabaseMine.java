import java.util.HashMap;
import java.util.Collection;

public class DatabaseMine implements DatabaseInterface {

    public String save(String plainPassword, String encryptedPassword) {
        return null;
    }

    public String decrypt(String encryptedPassword) {
        return null;
    }

    public int size() {
        return 0;
    }

    public void printStatistics() {
        System.out.println("*** DatabaseStandard Statistics ***");
        System.out.println("Size is " + size() + " passwords");
        System.out.println("Initial Number of Indexes when Created " + "CHANGE THIS");
        System.out.println("*** End DatabaseStandard Statistics ***");
    }



}