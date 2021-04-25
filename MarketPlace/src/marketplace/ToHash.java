package marketplace;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * for ecyrpting (SHA-256 + salted) a message (password)
 * @author Jack Gate-Leven
 */
public class ToHash {

    private static final String salt = "4@#ndssa213";

    /**
     * REFERENCE: geeks for geeks article on "SHA-256 Hash in Java"
     * @param hash sha-256 encryption of input message, byte array
     * @return hexString hexadecimal string of SHA-256 hash
     */
    public static String toHexidecimalString(byte[] hash)
    {
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

    // through sha256 with salt and out as a hash product
    /**
     * adds a salt to the input message and gets it's SHA-256 hash
     * @param message string that is to be encrypted with SHA-256
     * @return SHA256 hash of the message in a string
     * @throws NoSuchAlgorithmException if sha-256 algorithm can't be found
     */
    public static String intoHash(String message) throws NoSuchAlgorithmException {
        message += salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return toHexidecimalString(md.digest(message.getBytes(StandardCharsets.UTF_8)));
    }
}

