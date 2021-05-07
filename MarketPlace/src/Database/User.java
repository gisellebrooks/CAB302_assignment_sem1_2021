package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class User{
    private final MariaDBDataSource pool;

    public User(MariaDBDataSource pool){
        this.pool = pool;

    }

    public void addUser(String username, String password, String accountType, String organisation, String name) {
        UserAccount userAcc = new UserAccount(username, password, accountType, organisation, name);

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement()) {
                statement.setString(1, userAcc.getUsername());
                statement.setString(2, userAcc.getPassword());
                // etc
                // execute

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String userID){
        // check db if user already exits
        return false;
    }

    // all other methods for user stuff and password set n check etc


}



/**
 *
 * @param
 * @return
 */
//public class User {
//
//    private String username;
//    private String passwordHash;
//    private String accountType;
//    private String organisation;
//    private final MariaDBDataSource pool;
//    private PasswordFunctions passwordFunctions;
//
//    // set userID
//    //set password
//
//    // reference: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
//    private final String passwordRegex = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{12,20}$";
//
//    /**
//     *
//     * @param
//     * @param
//     * @return
//     */
//    public User(String username, String password, String type, String organisation, MariaDBDataSource pool) {
//        username = username;
//        // passwordHash = passwordHash;
//        accountType = type;
//        organisation = organisation;
//
//        passwordFunctions = new PasswordFunctions();
//
//        this.pool = pool;
//
//
//
//
//        // password check
//
//        // is password null
//        if (password == null || password == "") {
//            // password is not valid
//        }
//
//
//        Pattern pattern = Pattern.compile(passwordRegex);
//        Matcher match = pattern.matcher(password);
//
//        if (match.matches()){
//            // password is valid
//        } else {
//            // password is not valid
//        }
//
//
//
//        // check db doesnt have user already, check all parameters are valid
//        // add them to the db and create object correctly, otherwise throw an error in construction
//        try(Connection conn = pool.getConnection()) {
//
//            ResultSet queriedResult;
//
//            // delete user from the database
//            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?)")) {
//                statement.clearParameters();
//                statement.setString(1, username);
//                statement.setString(2, passwordHash);
//                statement.setString(3, accountType);
//                statement.setString(4, organisation);
//                statement.executeUpdate();
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public boolean removeUser(String username) throws SQLException {
//        // run checks to see if user exists
//        // server.removeUser(username);
//
//        try(Connection conn = pool.getConnection()) {
//
//            ResultSet queriedResult;
//
//            // delete user from the database
//            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM USER_INFORMATION " +
//                    " WHERE username=?")) {
//                statement.setString(1, username);
//                statement.executeQuery();
//            }
//
//        }
//
//        return true;
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public boolean ChangeUserPassword(String username, String currentPassword, String newPassword) throws NoSuchAlgorithmException, SQLException {
//        // check current password matches user's stored hash password
//        // get users password hash
//
//        if (!passwordFunctions.IsPasswordStrong(currentPassword)) {
//            // throw error as current password cannot possibly be weak in the db
//        }
//
//
//        if (!checkPasswordMatches(username, currentPassword)){
//            // throw error, current password does not match
//        }
//
//        // MAKE THIS A TRY BLOCK
//        if (passwordFunctions.IsPasswordStrong(newPassword)){
//            // password is valid
//            passwordFunctions.intoHash(newPassword); // send to db for user
//            // add to db
//            return true;
//        } else {
//            // password is weak, throw weak password error
//            return false;
//        }
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public String getPasswordHash(String username) throws SQLException, NoSuchAlgorithmException {
//
//        String passwordHash = "";
//        // get users passwordHash from db
//        return passwordHash;
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public boolean checkPasswordMatches(String username, String passwordToBeChecked) throws SQLException, NoSuchAlgorithmException {
//
//        String hashToBeChecked = passwordFunctions.intoHash(passwordToBeChecked);
//
//         if (hashToBeChecked == this.getPasswordHash(username)){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public boolean login(String username, String password) throws SQLException, NoSuchAlgorithmException {
//        // throw exceptions if wrong password, etc
//        // return true if login successful
//        return false;
//    }
//
//    /**
//     *
//     * @param
//     * @return
//     */
//    public boolean logout(String username, String password) throws SQLException, NoSuchAlgorithmException {
//        return false;
//        // return true if logout successful
//    }
//}
