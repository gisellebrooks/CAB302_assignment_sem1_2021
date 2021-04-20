package marketplace;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PropStuff {

    private static final String salt = "4@#ndssa213";

    public static String toHexidecimalString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    // through sha256 with salt and out as a hash product
    public static String intoHash(String message) throws NoSuchAlgorithmException {
        message += salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.digest((message).getBytes());
        md.digest(message.getBytes(StandardCharsets.UTF_8));
        return toHexidecimalString(md.digest(message.getBytes(StandardCharsets.UTF_8)));
    }
}
