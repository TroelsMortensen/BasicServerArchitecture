package bsa.dataaccess.user;

import bsa.models.User;

import java.util.UUID;

public interface UserDAO {
    void create(User user);

    User find(UUID id);

    void update(User user);

    User findByUsername(String username);

    void delete(UUID id);
}
