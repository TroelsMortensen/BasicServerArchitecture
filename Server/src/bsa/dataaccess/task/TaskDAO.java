package bsa.dataaccess.task;

import bsa.models.Task;

import java.util.UUID;

public interface TaskDAO {
    void create(Task task);
    Task find(UUID id);
    void update(Task task);

}
