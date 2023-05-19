package bsa.dataaccess.user;

import bsa.dataaccess.DAO;
import bsa.models.User;

import java.util.UUID;

public interface UserDAO extends DAO<User> {
    User findByUsername(String username);
}
