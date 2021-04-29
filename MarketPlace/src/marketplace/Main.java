package marketplace;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {


    private static void initDb(MariaDBDataSource pool) throws SQLException {
        String string;
        StringBuffer buffer = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./setupDB.sql"));
            while ((string = reader.readLine()) != null) {
                buffer.append(string + "\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] queries = buffer.toString().split(";");

        for (String query : queries) {
            if (query.isBlank()) continue;
            try(Connection conn = pool.getConnection();
                PreparedStatement statement = conn.prepareStatement(query)) {
                    statement.execute();
                }
        }
    }

    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        initDb(pool);

    }
}
