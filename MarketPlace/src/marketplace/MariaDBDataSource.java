package marketplace;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Properties;

public class MariaDBDataSource {
    private static MariaDBDataSource ds;
    private MariaDbPoolDataSource MDBDS;
    private static Connection instance = null;

    /**
     * Constructor intializes the connection.
     */
    private MariaDBDataSource() throws SQLException {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            MDBDS = new MariaDbPoolDataSource();
            in = new FileInputStream("db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            MDBDS.setUrl(props.getProperty("jdbc.url"));
            MDBDS.setUser(props.getProperty("jdbc.username"));
            MDBDS.setPassword(props.getProperty("jdbc.password"));
            MDBDS.setMaxPoolSize(8);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MariaDBDataSource getInstance() throws SQLException {
        if (ds == null)
            ds = new MariaDBDataSource();
        return ds;
    }

    public Connection getConnection() throws SQLException {
        try{
            instance = MDBDS.getConnection();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return instance;
    }








}
