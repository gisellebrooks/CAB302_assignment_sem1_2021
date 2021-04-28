package marketplace;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @param
 * @return
 */
public class User {

    private String username;
    private String passwordHash;
    private String type;
    private String organisation;

    /**
     *
     * @param
     * @return
     */
    public User() {

    }

    /**
     *
     * @param
     * @return
     */
    public User(String username, String passwordHash, String type, String organisation) {
        username = username;
        passwordHash = passwordHash;
        type = type;
        organisation = organisation;
    }

    /**
     *
     * @param
     * @return
     */
    public boolean removeUser(String username) {
        // run checks to see if user exists
        //server.removeUser(username);
        return false;
    }

    /**
     *
     * @param
     * @return
     */
    public boolean ChangeUserPassword(String username, String newPassword) {
        // run checks to see if user exists
        // server.changePasword(username);

        return false;
    }

    /**
     *
     * @param
     * @return
     */
    public boolean checkPassword(String username, String password) throws SQLException, NoSuchAlgorithmException {

        ToHash getHash = new ToHash();
        String hash = getHash.intoHash(password);
        return false;
//        if (hash == server.getPasswordHash(username)){
//            return true;
//        }
//        else {
//            return false;
//        }


    }

    /**
     *
     * @param
     * @return
     */
    public boolean login(String username, String password) throws SQLException, NoSuchAlgorithmException {
        // throw exceptions if wrong password, etc
        // return true if login successful
        return false;
    }

    /**
     *
     * @param
     * @return
     */
    public boolean logout(String username, String password) throws SQLException, NoSuchAlgorithmException {

        // return true if logout successful
        return false;
    }
}
