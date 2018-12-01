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
            database.save(encryptPassword(password), password);

            ArrayList<Integer> indecesOfA = new ArrayList<Integer>();
            ArrayList<Integer> indecesOfE = new ArrayList<Integer>();
            ArrayList<Integer> indecesOfI = new ArrayList<Integer>();
            ArrayList<Integer> mergedIndecesList = new ArrayList<Integer>();

            for (int j = 0; j < password.length(); j++) {
                if (password.charAt(j) == 'a' || password.charAt(j) == 'A') {
                    indecesOfA.add(j);
                }
                if (password.charAt(j) == 'e' || password.charAt(j) == 'E') {
                    indecesOfE.add(j);
                }
                if (password.charAt(j) == 'i' || password.charAt(j) == 'I') {
                    indecesOfI.add(j);
                }

            }

            if (!indecesOfA.isEmpty() && !indecesOfE.isEmpty() && !indecesOfI.isEmpty()) {

                mergedIndecesList.addAll(indecesOfA);
                mergedIndecesList.addAll(indecesOfE);
                mergedIndecesList.addAll(indecesOfI);

                // apply power set rule to find all possible combinations
                int indecesListSize = mergedIndecesList.size();
                int numOfCombinations = (int) Math.pow(2d, Double.valueOf(indecesListSize));
                for (int j = 1; j < numOfCombinations; j++) {
                    String binaryString = Integer.toBinaryString(numOfCombinations | j).substring(1);
                    StringBuilder newPassword = new StringBuilder(password);
                    for (int k = 0; k < indecesListSize; k++) {
                        if (binaryString.charAt(k) == '1') {

                            if (indecesOfA.contains(mergedIndecesList.get(k))) {

                                newPassword.setCharAt(k, '@');

                            } else if (indecesOfE.contains(mergedIndecesList.get(k))) {

                                newPassword.setCharAt(k, '3');

                            } else {

                                newPassword.setCharAt(k, '1');

                            }
                        }
                    }

                    // save password with changed a,e and i
                    database.save(encryptPassword(newPassword.toString()), newPassword.toString());

                    if (!Character.isDigit((newPassword.toString()).charAt(0))) {

                        newPassword = new StringBuilder(upperCaseFirstChar(newPassword.toString()));

                        database.save(encryptPassword(newPassword.toString()), newPassword.toString());

                        database.save(encryptPassword(addYear(newPassword.toString())), addYear(newPassword.toString()));


                    } else {

                        database.save(encryptPassword(addYear(newPassword.toString())), addYear(newPassword.toString()));

                    }

                }
            }

        }

    }

    public String crackPassword(String encryptedPassword, DatabaseInterface database) {
        // uses database to crack encrypted password, returning the original password
        return database.decrypt(encryptedPassword);
    }

    public String encryptPassword(String password) {

        String encryptedPassword = "";

        // calls Sha1 algorithm given and generates a 40 character hash code
        try {

            encryptedPassword = Sha1.hash(password);

        } catch (UnsupportedEncodingException e) {

            System.out.println("EncodingError");

        }

        return encryptedPassword;

    }

    public String upperCaseFirstChar(String password) {

        char[] passwordArray = password.toCharArray();
        passwordArray[0] = Character.toUpperCase(passwordArray[0]);
        return new String(passwordArray);
    }

    public String addYear(String password) {
        return password.concat("2018");
    }

}
