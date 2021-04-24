package marketplace;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UnitTesting {

    @BeforeEach
    /*
     * Create a fresh database
     */
    public void create() throws Exception {
        // statement.executeUpdate("DROP DATABASE marketplace_server");
        // addUser("name1", "password12#A", "user", "org1");
        // addOrganisation(1, "org1", 200, 10);
        // addOrganisation(1, "org2", 200, 10);
    }




    // Testing removal of a user ---------------------------------------------------------------------------------------
    @Test
    /* typical case - test if user was removed */
    public void checkUserRemoved() {
        // assertEquals(1, SELECT * FROM [Users] WHERE Username = "name1");
        // removeUser("name1", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "name1");
    }


    // Testing the filters for adding users ----------------------------------------------------------------------------
    @Test
    /* typical case - test if user was added with valid info */
    public void checkUserAdded() {
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "name2");
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, SELECT * FROM [Users] WHERE Username = "name2");
    }

    @Test
    /* typical case - test if username must be unique */
    public void checkUniqueUsername() {
        // addUser("name2", "password12#A", "user", "org1");
        // addUser("name2", "password12#A", "user", "org1");
        // assertThat(SELECT * FROM [Users] WHERE Username = "name2";, lessThan(2));
    }

    @Test
    /* typical case - test if user can be added with empty name */
    public void checkEmptyUsername() {
        // addUser("", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "");
    }

    @Test
    /* typical case - test if user can be added with invalid username, only letters or numbers */
    public void checkInvalidUsername() {
        // addUser("12345abcde!@#$%^&*()?/><.,", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "12345abcde!@#$%^&*()?/><.,");
    }

    @Test
    /* typical case - test if user can be added with invalid username, only letters or numbers */
    public void checkLongUsername() {
        // addUser("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }


    // Testing the filters for adding users ----------------------------------------------------------------------------
    @Test
    /* typical case - test if organisation was removed */
    public void checkOrganisationRemoved() {

    }


    // Testing the filters for adding organisations --------------------------------------------------------------------
    @Test
    /* typical case - test if organisation must be unique */
    public void checkUniqueOrganisationName() {
        // addOrganisation("name2", "password12#A", "user", "org");
        // addOrganisation("name2", "password12#A", "user", "org");
        // assertThat(SELECT * FROM [Users] WHERE organisation = "org";, lessThan(2));
    }

    @Test
    /* typical case - test if organisation can be added with empty name */
    public void checkEmptyOrganisationName() {
        // addOrganisation("name2", "password12#A", "user", "");
        // assertEquals(0, SELECT * FROM [Users] WHERE organisation = "");
    }

    @Test
    /* typical case - test if organisation can be added with invalid name, only letters or numbers */
    public void checkInvalidOrganisationName() {
        // addOrganisation("name2", "password12#A", "user", "12345abcde!@#$%^&*()?/><.,");
        // assertEquals(0, SELECT * FROM [Users] WHERE organisation = "12345abcde!@#$%^&*()?/><.,");
    }

    @Test
    /* typical case - test if organisation name is limited in length */
    public void checkLongOrganisationName() {
        // addOrganisation("name2", "password12#A", "user", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        // assertEquals(0, SELECT * FROM [Users] WHERE organisation = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }


    // Testing changing user password ----------------------------------------------------------------------------------
    @Test
    /* typical case - test if password can be changed to another valid password */
    public void checkChangePasswordValid() {
        // addUser("name2", "password12#A", "user", "org1");
        // changePassword("name2", "password12#A", "abcDSA123!@#ads");
        // assertEquals(1, login("name2", "abcDSA123!@#ads"));
    }

    @Test
    /* typical case - test if password can be changed to a weak password */
    public void checkChangePasswordWeak() {
        // addUser("name2", "password12#A", "user", "org1");
        // changePassword("name2", "password12#A", "123abc");
        // assertEquals(0, login("name2", "123abc"));
        // assertEquals(1, login("name2", "password12#A"));
    }

    @Test
    /* typical case - test if password can be changed to an empty password */
    public void checkChangePasswordEmpty() {
        // addUser("name2", "password12#A", "user", "org1");
        // changePassword("name2", "password12#A", "");
        // assertEquals(0, login("name2", ""));
        // assertEquals(1, login("name2", "password12#A"));
    }

    @Test
    /* boundary case - test if password can be changed to same as before */
    public void checkChangePasswordSame() {
        // addUser("name2", "password12#A", "user", "org1");
        // changePassword("name2", "password12#A", "password12#A");
        // assertEquals(1, login("name2", "password12#A"));
    }

    @Test
    /* boundary case - test if user is locked out after failing to enter current password 3 times in change password */
    public void checkChangePasswordLockout() {
        // addUser("name2", "password12#A", "user", "org1");
        // changePassword("name2", "p1", "12b321j!@#dasF");
        // changePassword("name2", "p1", "12b321j!@#dasF");
        // changePassword("name2", "p1", "12b321j!@#dasF");
        // assertEquals(0, login("name2", "password12#A"));
    }


    // Testing IT user authority ---------------------------------------------------------------------------------------
    @Test
    /* typical case - test if a user can be given Admin authority */
    public void checkAdminUserAdd() {
        // addUser("name2", "password12#A", "Admin", "org1");
        // assertEquals(1, login("name2", "password12#A"));
        // assertEquals(1, SELECT * FROM [Users] WHERE username = "name2");
    }





    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // maybe do all password change, password input filter tests for admin as well, but maybe note seeing as theyre already tested above
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!







    // Testing user login  ---------------------------------------------------------------------------------------------
    @Test
    /* typical case - test if a user can login correctly */
    public void checkValidLogin() {
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, login("name2", "password12#A"));
        // assertEquals(1, SELECT * FROM [Users] WHERE username = "name2");
    }

    @Test
    /* typical case - test if a user can logout correctly */
    public void checkValidLogout() {
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, login("name2", "password12#A"));
        // assertEquals(1, logout("name2", "password12#A"));
    }

    @Test
    /* typical case - test if a user can login without a valid username */
    public void checkLoginUnknownUsername() {
        // removeUser("name2", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE username = "name2");
        // assertEquals(0, login("name2", "password12#A"));
    }

    @Test
    /* typical case - test if a user can login with the wrong password */
    public void checkLoginWrongPassword() {
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, SELECT * FROM [Users] WHERE username = "name2");
        // assertEquals(0, login("name2", "123"));
    }

    @Test
    /* typical case - test if a account is locked out after 3 failed attempts */
    public void checkLoginLockout() {
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, SELECT * FROM [Users] WHERE username = "name2");
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "password12#A"));
    }

    @Test
    /* typical case - test if a wrong password login with password existing for someone else in the database */
    public void checkLoginWrongPasswordButPasswordExistDB() {
        // addUser("name2", "password12#A", "user", "org1");
        // addUser("name3", "12313232sadSDA123@#!#A", "user", "org1");
        // assertEquals(0, login("name2", "12313232sadSDA123@#!#A"));
    }

    @Test
    /* typical case - test if a account is locked out after 3 failed attempts */
    public void checkLockoutReAccess() {
        // addUser("name2", "password12#A", "user", "org1");
        // assertEquals(1, SELECT * FROM [Users] WHERE username = "name2");
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "123"));
        // assertEquals(0, login("name2", "password12#A"));

        // addUser("name3", "123sdsda#A", "admin", "org1");
        // login("name3", "password12#A");
        // String newPassword = openAccount("name2");

        // assertEquals(1, login("name2", "password12#A"));
        // assertEquals(1, login("name2", newPassword));
    }


    // Testing Admin authority -----------------------------------------------------------------------------------------
    @Test
    /* typical case - add a user to admin role */
    public void checkChangeToAdmin() {

    }

    /* typical case - remove a user from admin role */
    public void checkRemoveFromAdmin() {

    }

    /* typical case - add a user to admin role and then remove them over a few times*/
    public void checkBackandFourthFromToAdmin() {

    }
}
