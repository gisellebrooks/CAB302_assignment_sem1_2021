package marketplace.Server;

//import Server.Server.MariaDBDataSource;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collections;
import java.util.Map;

public class QueryTemplate {

    private final MariaDBDataSource pool;

    public QueryTemplate(MariaDBDataSource pool) {
        this.pool = pool;
    }

    public void update(String query, Map<String, Object> params)
    {
        try (Connection conn = pool.getConnection()) {
            try(PreparedStatement statement = conn.prepareStatement(query)){
                if (params != null){
                    buildQuery(params, statement).executeUpdate();

                } else{
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String query, Map<String, Object> params){

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                if (params != null){
                    buildQuery(params, statement).executeQuery();

                } else{
                    statement.executeQuery();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void add(String query){

        Map<String, Object> params = Collections.emptyMap();
        add(query, params);

    }

    public ResultSet get(String query, Map<String, Object> params){

        ResultSet result = null;

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(query)) {
                if (params != null){
                    result = buildQuery(params, statement).executeQuery();

                } else{
                    result = statement.executeQuery();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }

    public PreparedStatement buildQuery ( Map<String, Object> params, PreparedStatement statement) throws SQLException {

        for (Map.Entry<String, Object> param : params.entrySet()) {
            int i = 1;
            Object value = param.getValue();

            if (param.getValue() instanceof String) {
                String strVal = (String) value;

                statement.setString(i, strVal);

            } else if (param.getValue() instanceof Integer) {
                int intVal = (Integer) value;
                statement.setInt(i, intVal);

            } else if (param.getValue() instanceof BigDecimal) {
                BigDecimal bigVal = (BigDecimal) value;
                statement.setBigDecimal(i, bigVal);
            }
            else if (param.getValue() instanceof Timestamp) {
                Timestamp timeVal = (Timestamp) value;
                statement.setTimestamp(i, timeVal);
            }
        }
        return statement;
    }

}
