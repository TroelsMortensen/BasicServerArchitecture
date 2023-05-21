package requestobjects.task;

import java.io.Serializable;

public class CreateTaskRequest implements Serializable {
    private String title;

    public CreateTaskRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
