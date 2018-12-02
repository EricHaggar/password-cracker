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
            String originalPassword = commonPasswords.get(i);
            database.save(originalPassword, encryptPassword(originalPassword));

            StringBuilder modifiedPassword = new StringBuilder(originalPassword);
            baseCase(modifiedPassword, database);

            ArrayList<Integer> indexesOfA = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfE = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfI = new ArrayList<Integer>();
            ArrayList<Integer> mergedIndexesList = new ArrayList<Integer>();

            for (int j = 0; j < originalPassword.length(); j++) {
                if (originalPassword.charAt(j) == 'a' || originalPassword.charAt(j) == 'A') {
                    indexesOfA.add(j);
                }
                if (originalPassword.charAt(j) == 'e' || originalPassword.charAt(j) == 'E') {
                    indexesOfE.add(j);
                }
                if (originalPassword.charAt(j) == 'i' || originalPassword.charAt(j) == 'I') {
                    indexesOfI.add(j);
                }

            }

            if (!indexesOfA.isEmpty() && !indexesOfE.isEmpty() && !indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfE);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfA.isEmpty() && !indexesOfE.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfE);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfA.isEmpty() && !indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfE.isEmpty() && !indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfE);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfA.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfE.isEmpty()) {
                mergedIndexesList.addAll(indexesOfE);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

            } else if (!indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);
            } else {

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

    public void baseCase(StringBuilder modifiedPassword, DatabaseInterface database) {

        StringBuilder temp = modifiedPassword;

        modifiedPassword = new StringBuilder(addYear(modifiedPassword.toString()));
        database.save(modifiedPassword.toString(), encryptPassword(modifiedPassword.toString()));

        if (!Character.isDigit((modifiedPassword.toString()).charAt(0))) {

            modifiedPassword = new StringBuilder(upperCaseFirstChar(modifiedPassword.toString()));
            database.save(modifiedPassword.toString(), encryptPassword(modifiedPassword.toString()));

            temp = new StringBuilder(upperCaseFirstChar(temp.toString()));
            database.save(temp.toString(), encryptPassword(temp.toString()));
        }
    }

    public void findAllCombinations(ArrayList<Integer> indexesOfA, ArrayList<Integer> indexesOfE,
            ArrayList<Integer> indexesOfI, ArrayList<Integer> mergedIndexesList, DatabaseInterface database,
            String password) {

        int indexesListSize = mergedIndexesList.size();
        int numOfCombinations = (int) Math.pow(2d, Double.valueOf(indexesListSize));
        for (int j = 1; j < numOfCombinations; j++) {
            String binaryString = Integer.toBinaryString(numOfCombinations | j).substring(1);
            StringBuilder modifiedPassword = new StringBuilder(password);
            for (int k = 0; k < indexesListSize; k++) {

                if (binaryString.charAt(k) == '1') {

                    if (indexesOfA.contains(mergedIndexesList.get(k))) {
                        modifiedPassword.setCharAt(mergedIndexesList.get(k), '@');

                    } else if (indexesOfE.contains(mergedIndexesList.get(k))) {
                        modifiedPassword.setCharAt(mergedIndexesList.get(k), '3');

                    } else {
                        modifiedPassword.setCharAt(mergedIndexesList.get(k), '1');

                    }
                }
            }

            database.save(modifiedPassword.toString(), encryptPassword(modifiedPassword.toString()));
            baseCase(modifiedPassword, database);
        }

    }
}
