package bsa.dataaccess.task;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.models.Task;
import bsa.models.User;

import java.sql.*;
import java.util.UUID;

public class TaskPostgresDAO implements TaskDAO {

    private String connectionString;
    private String username;
    private String password;

    public TaskPostgresDAO(String connectionString, String username, String password) throws SQLException {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    @Override
    public void create(Task task) {
        try (Connection connection = getConnection()) {
            String sql = "insert into task values(?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, task.getId());
            statement.setString(2, task.getTitle());
            statement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public Task find(UUID id) {
        try (Connection conn = getConnection()) {
            String sql = """
                    select *
                    from task
                    where id=?
                    """;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Object retrievedId = resultSet.getObject("id");
                String retrievedTitle = resultSet.getString("title");

                return new Task((UUID) retrievedId, retrievedTitle);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        throw new DataAccessException("Task with id " + id + " not found");
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(UUID id) {

    }
}
