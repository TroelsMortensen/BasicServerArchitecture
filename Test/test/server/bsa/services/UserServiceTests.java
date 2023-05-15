package server.bsa.services;

import bsa.dataaccess.user.UserDAO;
import bsa.models.User;
import bsa.services.UserServiceImpl;
import bsa.services.UserService;
import bsa.services.exceptions.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTests {

    private UserMockDAO userDAO;
    private UserService userService;

    @BeforeEach
    public void createUserServiceAndDao() {
        userDAO = new UserMockDAO();
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
        UserDAO userDAO = new UserMockDAO();
        UserService userService = new UserServiceImpl(userDAO);
    }

    @Test
    public void created_user_has_actual_values_for_username_and_password() {
        // arrange
        String actualUsername = "acb";
        String actualPassword = "password";

        // act
        userService.create(actualUsername, actualPassword);

        // assert
        User user = userDAO.getSavedUser();
        assertAll(
                "Grouped assertions",
                () -> assertEquals(actualUsername, user.getUserName()),
                () -> assertEquals(actualPassword, user.getPassword())
        );
    }

    @Test
    public void should_create_user_with_valid_username() {
        String actualUsername = "abc"; // between [3, 15] characters

        userService.create(actualUsername, "password");
        User user = userDAO.getSavedUser();
        assertEquals(actualUsername, user.getUserName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "abcdeighf", "skldjflskjdfsjk"})
    public void should_create_user_with_valid_username2(String actualUsername) {

        userService.create(actualUsername, "password");
        User user = userDAO.getSavedUser();
        assertEquals(actualUsername, user.getUserName());
    }

    @Test
    public void should_fail_user_creation_username_is_null() {
        assertThrows(DomainLogicException.class, () -> {
            userService.create(null, "password");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "ab", "adjfskjksjskdjfa", "adjfskjksjskdjfasdfdsffa"})
    public void should_fail_user_creation_with_invalid_username(String actualUsername) {
        assertThrows(DomainLogicException.class, () -> {
            userService.create(actualUsername, "password");
        });
    }

    @Test
    public void should_update_password_provided_correct_current_password() {
        // arrange
        UUID id = UUID.randomUUID();
        String currentPassword = "Password";
        userDAO.savedUser = new User(id, "Username", currentPassword);

        // act
        String updatedPassword = "Whatever";
        userService.updatePassword(id, updatedPassword, currentPassword);

        // assert
        User savedUser = userDAO.getSavedUser();
        assertEquals(updatedPassword, savedUser.getPassword());
    }

    @Test
    public void should_update_username() {
        UUID id = UUID.randomUUID();
        String currentUsername = "Username";
        userDAO.savedUser = new User(id, currentUsername, "Password");

        // act
        String newUsername = "OtherUsername";
        userService.updateUsername(id, newUsername);

        // assert
        User savedUser = userDAO.getSavedUser();
        assertEquals(newUsername, savedUser.getUserName());
    }

    @Test
    public void should_fail_when_updating_username_with_conflict() {
        // arrange
        UUID id = UUID.randomUUID();
        String newUsername = "OtherUsername";

        User actualUser = new User(id, "Username", "Password");
        User user2 = new User(UUID.randomUUID(), newUsername, "Password2");
        userDAO.savedUsers = Arrays.asList(actualUser, user2);

        // act
        assertThrows(DomainLogicException.class, () -> {
            userService.updateUsername(id, newUsername);
        });

    }


}