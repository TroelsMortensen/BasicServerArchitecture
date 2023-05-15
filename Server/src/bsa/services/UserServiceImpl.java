package bsa.services;

import bsa.dataaccess.user.UserDAO;
import bsa.models.User;
import bsa.services.exceptions.DomainLogicException;

import java.util.List;
import java.util.UUID;

public class UserServiceImpl implements UserService{
    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void create(String username, String password) {
        validateUsername(username);
        validatePassword(password);
        User user = new User(UUID.randomUUID(), username, password);
        userDAO.create(user);
    }

    @Override
    public void updatePassword(UUID id, String newPassword, String currentPassword) {
        // 1 load
        User user = userDAO.find(id);

        // 2 verify and modify
        if(!user.getPassword().equals(currentPassword)){
            throw new DomainLogicException("Incorrect current password");
        }
        validatePassword(newPassword);

        user.setPassword(newPassword);

        // 3 save
        userDAO.update(user);
    }

    @Override
    public void updateUsername(UUID id, String newUsername) {
        // load
        User user = userDAO.find(id);

        // verify
        validateUsername(newUsername);

        // modify
        user.setUsername(newUsername);

        // save
        userDAO.update(user);
    }

    @Override
    public void delete(UUID id) {
        // load ..
        // verify ..

        // save
        userDAO.delete(id);
    }

    private void validatePassword(String password) {
        if(password == null || "".equals(password)){
            throw new DomainLogicException("Password cannot be empty");
        }
        if(password.length() < 8){
            throw new DomainLogicException("Password must be at least 8 characters");
        }
        boolean hasNumber = false;
        boolean hasSymbol = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        List<Character> necessarySymbols = List.of('!', '_', '-', '?', '+', '#');

        for (char character : password.toCharArray()) {
            if(Character.isDigit(character)){
                hasNumber = true;
            }
            if(necessarySymbols.contains(character)){
                hasSymbol = true;
            }
            if(Character.isUpperCase(character)){
                hasUpperCase = true;
            }
            if(Character.isLowerCase(character)){
                hasLowerCase = true;
            }
        }

        if(!hasNumber){
            throw new DomainLogicException("Password must contain at least one number");
        }
        if(!hasSymbol){
            throw new DomainLogicException("Password must contain at least one symbol of ..");
        }
        if(!hasUpperCase){
            throw new DomainLogicException("Password must contain at least one upper case character");
        }
        if(!hasLowerCase){
            throw new DomainLogicException("Password must contain at least one lower case character");
        }
        // at least 1 upper case, lower case, symbol, number
    }

    private void validateUsername(String username) {
        if(username == null || "".equals(username)){
            throw new DomainLogicException("Username cannot be empty");
        }
        if(username.length() < 3){
            throw new DomainLogicException("Username must be more than 3 characters");
        }
        if(username.length() > 15){
            throw new DomainLogicException("Username must be less than 15 characters");
        }

        User existingUserWithConflictingUsername = userDAO.findByUsername(username);
        if(existingUserWithConflictingUsername != null){
            throw new DomainLogicException("Username already taken");
        }
    }
}
