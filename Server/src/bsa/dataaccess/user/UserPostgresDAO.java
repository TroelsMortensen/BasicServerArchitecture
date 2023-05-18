package bsa.dataaccess.user;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.models.User;

import java.sql.*;
import java.util.UUID;

public class UserPostgresDAO implements UserDAO {

    private String connectionString;
    private String username;
    private String password;

    public UserPostgresDAO(String connectionString, String username, String password) throws SQLException {
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
        DriverManager.registerDriver(new org.postgresql.Driver());
    }

    @Override
    public void create(User user) {
        try (Connection connection = getConnection()) {
            String sql = "insert into \"user\" values(?, ?, ?)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, user.getId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getPassword());
            statement.execute();
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    @Override
    public User find(UUID id) {
        try(Connection conn = getConnection()){
            String sql = """
                    select *
                    from \"user\"
                    where id=?
                    """;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                Object retrievedId = resultSet.getObject("id");
                String retrievedUsername = resultSet.getString("username");
                String retrievedPassword = resultSet.getString("password");

                return new User((UUID)retrievedId, retrievedUsername, retrievedPassword);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        throw new DataAccessException("User with id " + id + " not found");
    }

    @Override
    public void update(User user) {
        try(Connection conn = getConnection()){
            String sql = """
                    update \"user\"
                    set username = ?,
                        password = ?
                    where id = ?
                    """;
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setObject(3, user.getId());
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
