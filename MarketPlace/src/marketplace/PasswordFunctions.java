package marketplace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @param
 * @return
 */
public class PasswordFunctions {

    /**
     * constructor
     * @param
     * @return
     */
    public PasswordFunctions() {

    }

    /**
     *
     * @param
     * @return
     */
    public String generatePassword() {
        String newPassword = "";

        int randomLength = ThreadLocalRandom.current().nextInt(12, 16);

        for (int i = 0; i < randomLength; i++) {
            // generate random characters and have at least one of each necessary onnes
        }

        return newPassword;
    }

    /**
     *
     * @param
     * @return
     */
    public boolean checkStrength(String password) {
        if (password.length() >= 12) {
            // do other if statements in here
            return true;
        } else {
            // throw password too short exception
            return false;
        }

        // go through each character in for loop
        // check if digit, letter, capital, symbol
    }
}
