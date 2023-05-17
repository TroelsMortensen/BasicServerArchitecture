package bsa.models;

import java.util.UUID;

public class Task {
    private UUID id;
    private String title;

    public Task(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
