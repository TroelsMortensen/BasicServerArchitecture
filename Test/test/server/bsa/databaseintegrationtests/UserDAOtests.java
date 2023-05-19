package server.bsa.databaseintegrationtests;

import bsa.dataaccess.user.UserPostgresDAO;
import bsa.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bsa.DatabaseTestUtil;

import java.sql.*;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOtests {


    private UserPostgresDAO userDAO;

    @BeforeEach
    public void setupDAO() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        userDAO = new UserPostgresDAO(DatabaseTestUtil.PGURI, DatabaseTestUtil.USERNAME, DatabaseTestUtil.PASSWORD);
    }

    @BeforeEach
    public void cleanupDatabase() throws SQLException {
        DatabaseTestUtil.clearUserTable();
    }



//    @Test
//    public void testJdbcConnection() throws SQLException {
//        DriverManager.registerDriver(new org.postgresql.Driver());
//
//        try(Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test", "postgres", "TroelsDatabase")){
//            String sql = "insert into \"user\" values(?, ?)";
//
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setObject(1, UUID.randomUUID());
//            statement.setString(2, "username");
//            statement.execute();
//        } catch (SQLException ex){
//           ex.printStackTrace();
//        }
//    }

    @Test
    public void should_insert_user_into_test_database() throws SQLException {
        // arrange
        UUID actualId = UUID.randomUUID();
        String actualUsername = "username";
        String actualPassword = "Password!2";
        User user = new User(actualId, actualUsername, actualPassword);

        // act
        userDAO.create(user);

        // assert
        try (Connection connection = DatabaseTestUtil.getConnectionToTestDb()) {
            String sql = "select * from \"user\";";

            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Object retrievedId = resultSet.getObject("id");
                String retrievedUsername = resultSet.getString("username");
                String retrievedPassword = resultSet.getString("password");
                assertAll(
                        () -> assertEquals(actualUsername, retrievedUsername),
                        () -> assertEquals(actualId, retrievedId),
                        () -> assertEquals(actualPassword, retrievedPassword)
                );
            } else {
                fail("Should have exactly one row in database");
            }
        }
    }

    @Test
    public void should_return_existing_user(){
        // arrange
        UUID actualId = UUID.randomUUID();
        String actualUsername = "Username";
        String actualPassword = "Password!2";
        User user = new User(actualId, actualUsername, actualPassword);

        userDAO.create(user);

        // act
        User retrievedUser = userDAO.find(actualId);

        // assert
        assertAll(
                () -> assertEquals(actualUsername, retrievedUser.getUserName()),
                () -> assertEquals(actualId, retrievedUser.getId()),
                () -> assertEquals(actualPassword, retrievedUser.getPassword())
        );
    }

    @Test
    public void should_update_user(){
        // arrange
        UUID actualId = UUID.randomUUID();
        String actualPassword = "Password!2";

        User user = new User(actualId, "Username", actualPassword);

        userDAO.create(user);

        String updatedUsername = "NewUsername";

        // act
        User updatedUser = new User(actualId, updatedUsername, actualPassword);
        userDAO.update(updatedUser);

        // assert
        User retrievedUser = userDAO.find(actualId);
        assertAll(
                () -> assertEquals(updatedUsername, retrievedUser.getUserName()),
                () -> assertEquals(actualId, retrievedUser.getId()),
                () -> assertEquals(actualPassword, retrievedUser.getPassword())
        );

    }
}
