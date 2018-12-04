import java.util.ArrayList;
import java.io.UnsupportedEncodingException;

public class Part1Test {

    public static void main(String[] args) {

        PasswordCracker testCracker = new PasswordCracker();
        DatabaseMine database1 = new DatabaseMine(37);
        ArrayList<String> commonPass = new ArrayList<String>();
        commonPass.add("123456");
        commonPass.add("password");
        commonPass.add("12345678");
        commonPass.add("brady");
        testCracker.createDatabase(commonPass, database1);
        database1.printStatistics();
        String code = new String("F35D55B3ACF667911A679B44918F5D88B2400823");
        String discoverPassword = testCracker.crackPassword(code, database1);
        System.out.println("Decrypt: " + code + " Password: " + discoverPassword + ";");

    }

}
