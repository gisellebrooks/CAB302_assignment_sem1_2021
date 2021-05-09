package users;

import Server.MariaDBDataSource;
import Server.QueryTemplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String passwordHash;
    private String accountType;
    private String organisation;
    private String name;

    private ResultSet orderHistory;
    private final MariaDBDataSource pool;
    private final QueryTemplate query;

    // reference: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    private final String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{12,20}$";

    private static final String salt = "4@#ndssa213";

    private static final String GET_USER_DETAILS = "SELECT * FROM Users WHERE username=?";
    private static final String INSERT_NEW_USER = "INSERT INTO Users VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM Users WHERE Username=?";

    public User(String username, String passwordHash, String accountType, String organisation, String name, MariaDBDataSource pool){
        this.username = username;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.organisation = organisation;
        this.name = name;

        this.pool = pool;
        this.query = new QueryTemplate(pool);

    }
    public void setUsername(String username){
        this.username = username;
    }

    public void setPasswordHash(String password){
        this.passwordHash = passwordHash;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    public void setOrganisation(String organisation){
        this.organisation = organisation;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return passwordHash;
    }

    public String getAccountType(){
        return accountType;
    }

    public String getOrganisation(){
        return organisation;
    }

    public String getName(){
        return name;
    }



    // after a new user is created, this method can be invoked to add them to the database
    public void addUser(User user) throws SQLException {

        Map<String, Object> params = new HashMap<>();
        params.put("Username", this.username);
        params.put("passwordHash", this.passwordHash);
        params.put("accountType", this.accountType);
        params.put("organisation", this.organisation);
        params.put("name", this.name);

        query.add(INSERT_NEW_USER, params);
    }

    public void removeUser(User user) throws SQLException {

        Map<String, Object> params = new HashMap<>();
        params.put("Username", this.username);

        query.update(DELETE_USER, params);
    }

    public boolean userExists(String username) {
        ResultSet result;

        Map<String, Object> params = new HashMap<>();
        params.put("username", username);

        result = query.get(GET_USER_DETAILS, params);

        // Handle if query returns empty
        try {
            if (result.next()) {
                return true;
            } else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Generates a strong random password with 13-19 characters, picked from digits, letters and characters
     * @return newPassword , the randomly generated password
     */
    public String generatePassword() {

        Pattern pattern = Pattern.compile(passwordRegex);
        SecureRandom random = new SecureRandom();
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()?";
        String newPassword = "";
        Matcher match = pattern.matcher(newPassword);
        int randomLength = ThreadLocalRandom.current().nextInt(13, 19);

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
     * @param password , the password to be checked
     * @return boolean of whether the input was a storng password or not
     */
    public boolean IsPasswordStrong(String password) {

        // is password null
        if (password == null || password == "") {
            return false; // throw error for new password being empty
        }

        // check password is strong
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher match = pattern.matcher(password);

        if (match.matches()){
            return true;
        } else {
            return false;
        }
    }


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
