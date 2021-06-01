package marketplace.Server;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;
import java.util.function.BooleanSupplier;

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
            in = new FileInputStream("MarketPlace2/src/marketplace/util/db.props");
            props.load(in);
            in.close();

            // specify the data source, username and password
            MDBDS.setUrl(props.getProperty("jdbc.url"));
            MDBDS.setUser(props.getProperty("jdbc.username"));
            MDBDS.setPassword(props.getProperty("jdbc.password"));
            MDBDS.setMaxPoolSize(8);
            MDBDS.setMinPoolSize(1);


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

    public ResultSet getResult(String query) {
        ResultSet rs = null;
        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                rs = statement.executeQuery();

            } catch (SQLException e) {
                System.err.println("Exception occurred in method 'getResult' in class 'MariaDBDataSource'");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void updateResult(String query) {

        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean Close() {
        try {
            instance.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static void CloseInstance() {
        if (ds != null)
            ds.Close();
    }

}
