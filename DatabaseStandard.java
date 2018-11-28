import java.util.HashMap;

public class DatabaseStandard implements DatabaseInterface {

    public DatabaseStandard() {

        HashMap<String, String> database = new HashMap<String, String>();
    }

    public String save(String plainPassword, String encryptedPassword) {

        return null; //Change this!!
    }

    public String decrypt(String encryptedPassword) {
        return null; //Change this!!
    }

    public int size() {
        return 0; //Change this
    }

    public void printStatistics() {

    }



}