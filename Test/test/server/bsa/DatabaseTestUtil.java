package server.bsa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseTestUtil {

    public static final String PGURI = "jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "TroelsDatabase";

    public static void clearUserTable() throws SQLException {
        clearTable("user");
    }

    public static void clearTaskTable()throws SQLException{
        clearTable("task");
    }

    private static void clearTable(String table) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());

        try(Connection connection = getConnectionToTestDb()) {
            String sql = "delete from \""+table+"\"";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        }
    }

    public static Connection getConnectionToTestDb() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test", "postgres", "TroelsDatabase");
    }
}
