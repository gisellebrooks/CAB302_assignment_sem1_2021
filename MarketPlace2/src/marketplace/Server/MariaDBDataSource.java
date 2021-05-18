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

    public void sendResult(String query) {
        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                statement.executeQuery();

            } catch (SQLException e) {
                System.err.println("Exception occurred in method 'getResult' in class 'MariaDBDataSource'");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long writeJavaObject(String query, Object object){
        String className = object.getClass().getName();
        int id = -1;

        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {

                statement.setString(1, className);
                statement.setObject(2, object);
                statement.executeUpdate();

                try(ResultSet result = statement.getGeneratedKeys()){

                    if (result.next()){
                        id = result.getInt(1);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Object readJavaObject(String query, long id){
        Object object = null;
        try (Connection conn = getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {

                statement.setLong(1, id);

                try (ResultSet result = statement.executeQuery()){
                    result.next();
                    object = result.getObject(1);
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
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

        private void Close() {
        MDBDS.close();
    }

    public static void CloseInstance() {
        if (ds != null)
            ds.Close();
    }

}
