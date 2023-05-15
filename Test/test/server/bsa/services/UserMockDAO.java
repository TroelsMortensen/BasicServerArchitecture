package server.bsa.services;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.dataaccess.user.UserDAO;
import bsa.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMockDAO implements UserDAO {

    public List<User> savedUsers;
    public UserMockDAO() {
        savedUsers = new ArrayList<>();
    }

    @Override
    public void create(User user) {
        savedUsers.add(user);
    }

    @Override
    public User find(UUID id) {
        for (User user : savedUsers) {
            if(user.getId().equals(id)) return user;
        }
        throw new DataAccessException("User with id " + id + " not found");
    }

    @Override
    public void update(User user) {
        boolean wasFound = savedUsers.removeIf(u -> u.getId().equals(user.getId()));
        if(!wasFound){
            throw new DataAccessException("User with id " + user.getId() + " not found");
        }
        savedUsers.add(user);
    }

    @Override
    public User findByUsername(String username) {
        for (User user : savedUsers) {
            if(user.getUserName().equals(username)) return user;
        }
        return null;
    }

    @Override
    public void delete(UUID id) {
        boolean wasFound = savedUsers.removeIf(u -> u.getId().equals(id));
        if(!wasFound){
            throw new DataAccessException("User with id " + id + " not found");
        }
    }

    public User getFirstUser() {
        return savedUsers.get(0);
    }
}
