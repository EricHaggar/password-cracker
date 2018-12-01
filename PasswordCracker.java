import java.util.ArrayList;
import java.util.Arrays;
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
            database.save(password, encryptPassword(password));

            ArrayList<Integer> indexesOfA = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfE = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfI = new ArrayList<Integer>();
            ArrayList<Integer> mergedIndexesList = new ArrayList<Integer>();

            for (int j = 0; j < password.length(); j++) {
                if (password.charAt(j) == 'a' || password.charAt(j) == 'A') {
                    indexesOfA.add(j);
                }
                if (password.charAt(j) == 'e' || password.charAt(j) == 'E') {
                    indexesOfE.add(j);
                }
                if (password.charAt(j) == 'i' || password.charAt(j) == 'I') {
                    indexesOfI.add(j);
                }

            }

            if (!indexesOfA.isEmpty() && !indexesOfE.isEmpty() && !indexesOfI.isEmpty()) {

                for (int s = 0; s < indexesOfA.size(); s++) {
                }

                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfE);
                mergedIndexesList.addAll(indexesOfI);

                // apply power set rule to find all possible combinations
                int indexesListSize = mergedIndexesList.size();
                int numOfCombinations = (int) Math.pow(2d, Double.valueOf(indexesListSize));
                for (int j = 1; j < numOfCombinations; j++) {
                    String binaryString = Integer.toBinaryString(numOfCombinations | j).substring(1);
                    StringBuilder newPassword = new StringBuilder(password);
                    for (int k = 0; k < indexesListSize; k++) {

                        if (binaryString.charAt(k) == '1') {

                            if (indexesOfA.contains(mergedIndexesList.get(k))) {
                                newPassword.setCharAt(mergedIndexesList.get(k), '@');

                            } else if (indexesOfE.contains(mergedIndexesList.get(k))) {

                                newPassword.setCharAt(mergedIndexesList.get(k), '3');

                            } else {

                                newPassword.setCharAt(mergedIndexesList.get(k), '1');

                            }
                        }
                    }

                    // save password with changed a,e and i
                    database.save(newPassword.toString(), encryptPassword(newPassword.toString()));

                    if (!Character.isDigit((newPassword.toString()).charAt(0))) {

                        database.save(addYear(newPassword.toString()), encryptPassword(addYear(newPassword.toString())));

                        newPassword = new StringBuilder(upperCaseFirstChar(newPassword.toString()));

                        database.save(upperCaseFirstChar(newPassword.toString()), encryptPassword(upperCaseFirstChar(newPassword.toString())));
                        
                        database.save(addYear(newPassword.toString()), encryptPassword(addYear(newPassword.toString())));                     

                    } else {

                        database.save(addYear(newPassword.toString()), encryptPassword(addYear(newPassword.toString())));
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
        return password + "2018";
    }

}
