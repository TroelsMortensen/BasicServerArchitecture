package requestobjects.user;

import java.io.Serializable;

public class CreateUserRequest implements Serializable {
    private final String username;
    private final String password;

    public CreateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
