package bsa.dataaccess.user;

import bsa.dataaccess.DAO;
import bsa.models.User;

public interface UserDAO extends DAO<User> {
    User findByUsername(String username);
}
