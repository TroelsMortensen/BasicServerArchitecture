package bsa.models;

import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;

    public User(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public UUID getId() {
        return id;
    }
}
