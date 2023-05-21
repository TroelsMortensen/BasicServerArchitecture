package requestobjects.user;

import java.io.Serializable;
import java.util.UUID;

public class UpdateUsernameRequest implements Serializable {

    private final UUID id;
    private final String newUsername;

    public UpdateUsernameRequest(UUID id, String newUsername) {
        this.id = id;
        this.newUsername = newUsername;
    }

    public UUID getId() {
        return id;
    }

    public String getNewUsername() {
        return newUsername;
    }
}
