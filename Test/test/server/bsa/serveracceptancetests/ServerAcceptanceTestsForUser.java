package server.bsa.serveracceptancetests;

import bsa.dataaccess.user.UserDAO;
import bsa.dataaccess.user.UserPostgresDAO;
import bsa.models.User;
import bsa.network.RequestHandler;
import bsa.services.UserService;
import bsa.services.UserServiceImpl;
import bsa.services.exceptions.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestobjects.user.CreateUserRequest;
import requestobjects.RequestObject;
import requestobjects.user.UpdateUsernameRequest;
import server.bsa.DatabaseTestUtil;

import java.sql.SQLException;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class ServerAcceptanceTestsForUser {

    private RequestHandler requestHandler;
    private UserDAO userDAO;

    @BeforeEach
    public void setupEntireSystem() throws SQLException {
        userDAO = new UserPostgresDAO(DatabaseTestUtil.PGURI, DatabaseTestUtil.USERNAME, DatabaseTestUtil.PASSWORD);
        UserService userService = new UserServiceImpl(userDAO);
        requestHandler = new RequestHandler(userService, null);
    }

    @BeforeEach
    public void clearDatabase() throws SQLException {
        DatabaseTestUtil.clearUserTable();
    }

    @Test
    public void should_insert_user(){
        // arrange
        String requestType = "user/create";
        CreateUserRequest payload = new CreateUserRequest("Username", "Pazzw0rd!2");
        RequestObject requestObject = new RequestObject(requestType, payload);

        // act
        Object responseObject = requestHandler.handle(requestObject);
        UUID createdId = (UUID)responseObject;

        // assert
        User user = userDAO.find(createdId);
        assertNotNull(user);
    }

    // TODO insert user failure scenario

    @Test
    public void should_fail_creating_user_given_invalid_password(){
        // arrange
        String requestType = "user/create";
        CreateUserRequest payload = new CreateUserRequest("Username", "Pazzw0rd2");
        RequestObject requestObject = new RequestObject(requestType, payload);

        // act assert
        assertThrows(DomainLogicException.class,
                () -> requestHandler.handle(requestObject)
        );
    }

    @Test
    public void should_update_username(){
        // arrange
        UUID id = UUID.randomUUID();
        String existingUsername = "Username";
        userDAO.create(new User(id, existingUsername, "Pazzw0rd!2"));

        // act
        String expectedUsername = "UpdateUsername";
        UpdateUsernameRequest payload = new UpdateUsernameRequest(id, expectedUsername);
        String requestType = "user/updateusername";
        RequestObject requestObject = new RequestObject(requestType, payload);

        requestHandler.handle(requestObject);

        // assert
        User user = userDAO.find(id);
        assertEquals(expectedUsername, user.getUserName());
    }
}
