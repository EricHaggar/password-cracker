import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

/*
PasswordCracker populates the hash maps with the passwords (values) and encrypted passwords (keys)
then decrypts the leaked accounts using that database
*/
public class PasswordCracker {

    /*
    Populates the hash map using the commonPasswords and the given database
    */
    public void createDatabase(ArrayList<String> commonPasswords, DatabaseInterface database) {

        // Looping through all passwords     
        for (int i = 0; i < commonPasswords.size(); i++) {

            // Stores given passwords without applying the augmentation rules
            String originalPassword = commonPasswords.get(i);
            database.save(originalPassword, encryptPassword(originalPassword));

            // Applies the base case (rules 1 and 2) to the original password given 
            StringBuilder modifiedPassword = new StringBuilder(originalPassword);
            baseCase(modifiedPassword, database);

            // Rules 3,4 and 5 will be applied here
            // These array lists will store the indexes of all instances of a, e and i in the password
            ArrayList<Integer> indexesOfA = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfE = new ArrayList<Integer>();
            ArrayList<Integer> indexesOfI = new ArrayList<Integer>();
            ArrayList<Integer> mergedIndexesList = new ArrayList<Integer>();

            // Looping through the password and populating the array lists with the corresponding indexes
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
            
            /* Applies all possible cases for instances of a,e and i in the password */

            // If the password contains a,e and i
            if (!indexesOfA.isEmpty() && !indexesOfE.isEmpty() && !indexesOfI.isEmpty()) {
                // Adds all indexes corresponsing to a, e and i to mergedIndexesList
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfE);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password contains a and e
            } else if (!indexesOfA.isEmpty() && !indexesOfE.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfE);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password contains a and i
            } else if (!indexesOfA.isEmpty() && !indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password contains e and i
            } else if (!indexesOfE.isEmpty() && !indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfE);
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password only contains a 
            } else if (!indexesOfA.isEmpty()) {
                mergedIndexesList.addAll(indexesOfA);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password only contains e
            } else if (!indexesOfE.isEmpty()) {
                mergedIndexesList.addAll(indexesOfE);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);

                // If the password only contains i
            } else if (!indexesOfI.isEmpty()) {
                mergedIndexesList.addAll(indexesOfI);
                findAllCombinations(indexesOfA, indexesOfE, indexesOfI, mergedIndexesList, database, originalPassword);
            }
        }

    }

    /*
    Returns the original password from the given encryptedPassword
    */
    public String crackPassword(String encryptedPassword, DatabaseInterface database) {
        return database.decrypt(encryptedPassword);
    }

    /*
    Returns the encrypted password using Sha1 algorithm
    */
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

    /*
    Applies rule 1 to the given password and returns augmented password
    */
    public String upperCaseFirstChar(String password) {

        char[] passwordArray = password.toCharArray();
        passwordArray[0] = Character.toUpperCase(passwordArray[0]);
        return new String(passwordArray);
    }

    /*
    Applies rule 2 to the given password and returns augmented password
    */
    public String addYear(String password) {
        return password + "2018";
    }

    /*
     Applies rules 1 and 2 to generate all possibilities
    */
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

    /*
     Applies power sets rules to find all combinations using the mergedIndexesList given
    */
    public void findAllCombinations(ArrayList<Integer> indexesOfA, ArrayList<Integer> indexesOfE,
            ArrayList<Integer> indexesOfI, ArrayList<Integer> mergedIndexesList, DatabaseInterface database,
            String password) {

        // Power sets algorithm
        int indexesListSize = mergedIndexesList.size();
        int numOfCombinations = (int) Math.pow(2d, Double.valueOf(indexesListSize));
        for (int j = 1; j < numOfCombinations; j++) {
            String binaryString = Integer.toBinaryString(numOfCombinations | j).substring(1);
            StringBuilder modifiedPassword = new StringBuilder(password);
            for (int k = 0; k < indexesListSize; k++) {

                // Changes the character depending on whether its a, e and i
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

            // Stores the password with modified a/e/i
            database.save(modifiedPassword.toString(), encryptPassword(modifiedPassword.toString()));

            // Calls baseCase to add more posibilities
            baseCase(modifiedPassword, database);
        }

    }
}
