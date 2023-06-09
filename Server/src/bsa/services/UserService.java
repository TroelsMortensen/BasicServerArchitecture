package bsa.services;

import java.util.UUID;

public interface UserService {
    UUID create(String username, String password);

    void updatePassword(UUID id, String newPassword, String currentPassword);

    void updateUsername(UUID id, String newUsername);

    void delete(UUID id);
}
