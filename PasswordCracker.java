import java.util.ArrayList;

public class PasswordCracker {

    void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface database) {
        // receives list of passwords and populates database with entries consisting
        // of (key,value) pairs where the value is the password and the key is the
        // encrypted password (encrypted using Sha1)
        // in addition to passwords in commonPasswords list, this class is
        // responsible to add mutated passwords according to rules 1-5.

    }

    String crackPassword(String encryptedPassword, DatabaseInterface database) {
        // uses database to crack encrypted password, returning the original password

        return null;
    }

}