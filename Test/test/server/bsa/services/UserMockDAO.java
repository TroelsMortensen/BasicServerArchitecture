package server.bsa.services;

import bsa.dataaccess.user.UserDAO;
import bsa.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMockDAO implements UserDAO {

    public User savedUser;
    public List<User> savedUsers;

    public UserMockDAO() {
        savedUsers = new ArrayList<>();
    }

    private boolean createWasCalled;
    public boolean getCreateWasCalled() {
        return createWasCalled;
    }

    @Override
    public void create(User user) {
        createWasCalled = true;
        savedUser = user;
    }

    @Override
    public User find(UUID id) {
        return savedUser;
    }

    @Override
    public void update(User user) {
        savedUser = user;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : savedUsers) {
            if(user.getUserName().equals(username)) return user;
        }
        return null;
    }

    public User getSavedUser() {
        return savedUser;
    }
}
