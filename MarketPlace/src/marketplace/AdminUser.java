package marketplace;

/**
 *
 * @param
 * @return
 */
public class AdminUser extends User {


    /**
     *
     * @param
     * @return
     */
    public AdminUser(String username, String password, String type, String organisation, MariaDBDataSource pool) {
        super(username, password, type, organisation, pool);


    }
}
