package server.bsa.servicetests.userservice;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.dataaccess.user.UserDAO;
import bsa.models.User;
import bsa.services.UserService;
import bsa.services.UserServiceImpl;
import bsa.services.exceptions.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTests {

    private MockDAOs.UserMockDAO userDAO;
    private UserService userService;

    @BeforeEach
    public void createUserServiceAndDao() {
        userDAO = new MockDAOs.UserMockDAO();
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void should_instantiate_userservice() {
        UserService userService;
    }

    @Test
    public void should_instantiate_concrete_service_implementation() {
        UserDAO userDAO = null;
        UserService userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void should_instantiate_concrete_service_with_dao_impl() {
        UserDAO userDAO = new MockDAOs.UserMockDAO();
        UserService userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void created_user_has_actual_values_for_username_and_password() {
        // arrange
        String actualUsername = "acb";
        String actualPassword = "Password!2";

        // act
        userService.create(actualUsername, actualPassword);

        // assert
        User user = userDAO.savedEntities.get(0);
        assertAll(
                "Grouped assertions",
                () -> assertEquals(actualUsername, user.getUserName()),
                () -> assertEquals(actualPassword, user.getPassword())
        );
    }

    @Test
    public void should_create_user_with_valid_username() {
        String actualUsername = "abc"; // between [3, 15] characters

        userService.create(actualUsername, "Password!2");
        User user = userDAO.savedEntities.get(0);
        assertEquals(actualUsername, user.getUserName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcdeighf", "skldjflskjdfsjk"})
    public void should_create_user_with_valid_username2(String actualUsername) {

        userService.create(actualUsername, "Password!2");
        User user = userDAO.savedEntities.get(0);
        assertEquals(actualUsername, user.getUserName());
    }

    @Test
    public void should_fail_user_creation_username_is_null() {
        assertThrows(DomainLogicException.class, () ->
                userService.create(null, "password")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "a",
            "ab",
            "adjfskjksjskdjfa",
            "adjfskjksjskdjfasdfdsffa"
    })
    public void should_fail_user_creation_with_invalid_username(String actualUsername) {
        assertThrows(DomainLogicException.class, () -> {
            userService.create(actualUsername, "password");
        });
    }

    @Test
    public void should_update_password_provided_correct_current_password() {
        // arrange
        UUID id = UUID.randomUUID();
        String currentPassword = "Password!2";
        userDAO.savedEntities = new ArrayList<>(List.of(new User(id, "Username", currentPassword)));

        // act
        String updatedPassword = "PazSw0rd!2";
        userService.updatePassword(id, updatedPassword, currentPassword);

        // assert
        User savedUser = userDAO.savedEntities.get(0);
        assertEquals(updatedPassword, savedUser.getPassword());
    }

    @Test
    public void should_update_username() {
        UUID id = UUID.randomUUID();
        String currentUsername = "Username";
        userDAO.savedEntities = new ArrayList<>(List.of(new User(id, currentUsername, "Password")));

        // act
        String newUsername = "OtherUsername";
        userService.updateUsername(id, newUsername);

        // assert
        User savedUser = userDAO.savedEntities.get(0);
        assertEquals(newUsername, savedUser.getUserName());
    }

    @Test
    public void should_fail_when_updating_username_with_conflict() {
        // arrange
        UUID id = UUID.randomUUID();
        String newUsername = "OtherUsername";

        User actualUser = new User(id, "Username", "Password");
        User user2 = new User(UUID.randomUUID(), newUsername, "Password2");
        userDAO.savedEntities = List.of(actualUser, user2);

        // act
        assertThrows(DomainLogicException.class, () -> {
            userService.updateUsername(id, newUsername);
        });

    }

    @Test // cannot create user with conflicting username
    public void should_fail_when_creating_user_with_username_conflict(){
        // arrange
        String existingUsername = "Username";
        User existingUser = new User(UUID.randomUUID(), existingUsername, "Password");
        userDAO.savedEntities = List.of(existingUser);

        // act

        assertThrows(DomainLogicException.class, () -> {
            userService.create(existingUsername, "Password!2");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Paswo!2",
            "Password!",
            "Password2",
            "password!2",
            "PASSWORD!2"
    })
    public void should_fail_when_creating_user_with_invalid_password(String password){
        // arrange
        String username = "Username";

        // act/assert
        assertThrows(DomainLogicException.class, () ->{
            userService.create(username, password);
        });
    }

    @Test
    public void should_delete_user(){
        // arrange
        UUID id = UUID.randomUUID();
        User userToDelete = new User(id, "Username", "Password!2");
        userDAO.savedEntities = new ArrayList<>(List.of(userToDelete));

        // act
        userService.delete(id);

        // assert
        assertTrue(userDAO.savedEntities.isEmpty());
    }

    @Test
    public void should_throw_exception_when_deleting_notexisting_user(){
        UUID actualId = UUID.randomUUID();
        User userToDelete = new User(UUID.randomUUID(), "Username", "Password!2");
        userDAO.savedEntities = new ArrayList<>(List.of(userToDelete));

        // act
        assertThrows(DataAccessException.class, () ->{
            userService.delete(actualId);
        });
    }
}