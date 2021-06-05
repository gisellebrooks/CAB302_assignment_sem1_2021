package marketplace.Handlers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provides hashing functionality, creates strong passwords, checks strength of passwords.
 * Provides all functionality for dealing with passwords.
 *
 * @author Jack Gate-Leven
 */
public class PasswordHandler {

    private static final String salt = "4@#ndssa213";

    // reference: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    private static final String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{12,20}$";

    /**
     * Generates a strong random password with 13-19 characters, picked from digits, letters and characters
     *
     * @return newPassword , a randomly generated strong password
     */
    public String generatePassword(){

        Pattern pattern = Pattern.compile(passwordRegex);
        SecureRandom random = new SecureRandom();
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()?";
        String newPassword = "";
        Matcher match = pattern.matcher(newPassword);
        int randomLength = ThreadLocalRandom.current().nextInt(13, 15);

        while (!match.matches()) {
            newPassword = "";

            for (int i = 0; i < randomLength; i++) {
                int randomIndex = random.nextInt(chars.length());
                newPassword += chars.charAt(randomIndex);
            }
            match = pattern.matcher(newPassword);
        }

        return newPassword;
    }

    /**
     * Checks if a password is strong against previously declared standards
     *
     * @param password , the password to be checked
     * @return boolean of whether the input was a strong password or not
     */
    public static boolean IsPasswordStrong(String password){

        if (password == null || password.equals("")) {
            return false;
        }

        // check password is strong through regular expression
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher match = pattern.matcher(password);

        // if password is strong
        return match.matches();
    }

    /**
     * Helper method for IntoHash, turns byte array into hexadecimal
     *
     * REFERENCE: geeks for geeks article on "SHA-256 Hash in Java"
     * @param hash sha-256 encryption of input message, byte array
     * @return hexString hexadecimal string of SHA-256 hash
     */
    public static String ToHexidecimalString(byte[] hash){
        // Convert byte array into sign number representation
        BigInteger number = new BigInteger(1, hash);

        // Convert sha256 message into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * adds a salt to the input message and gets it's SHA-256 hash
     *
     * @param message string that is to be encrypted with SHA-256
     * @return SHA256 hash of the message in a string
     * @throws NoSuchAlgorithmException if sha-256 algorithm can't be found
     */
    public static String IntoHash(String message) throws NoSuchAlgorithmException {
        try {
            message += salt;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return ToHexidecimalString(md.digest(message.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new NoSuchAlgorithmException("Error with password management");
        }
    }
}