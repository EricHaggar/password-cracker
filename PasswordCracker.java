import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

public class PasswordCracker {

    public void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface database) {
        // receives list of passwords and populates database with entries consisting
        // of (key,value) pairs where the value is the password and the key is the
        // encrypted password (encrypted using Sha1)
        // in addition to passwords in commonPasswords list, this class is
        // responsible to add mutated passwords according to rules 1-5.

        for (int i = 0; i < commonPasswords.size(); i++) {
            String password = commonPasswords.get(i);
            String encryptedPassword = EncryptPassword(password);
            database.save(encryptedPassword, password);
        }

    }

    public String crackPassword(String encryptedPassword, DatabaseInterface database) {
        // uses database to crack encrypted password, returning the original password

        return null;
    }

    public String EncryptPassword(String password) {

        String encryptedPassword = "";

        // calls Sha1 algorithm given and generates a 40 character hash code
        try {

            encryptedPassword = Sha1.hash(password);

        } catch (UnsupportedEncodingException e) {

            System.out.println("EncodingError");

        }

        return encryptedPassword;

    }

}
