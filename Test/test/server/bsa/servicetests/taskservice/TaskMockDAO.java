package server.bsa.servicetests.taskservice;

import bsa.dataaccess.exceptions.DataAccessException;
import bsa.dataaccess.task.TaskDAO;
import bsa.models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskMockDAO implements TaskDAO {
    public List<Task> savedTasks;

    public TaskMockDAO() {
        savedTasks = new ArrayList<>();
    }

    @Override
    public void create(Task task) {
        savedTasks.add(task);
    }

    @Override
    public Task find(UUID id) {
        for (Task task : savedTasks) {
            if(task.getId().equals(id)) return task;
        }
        throw new DataAccessException("User with id " + id + " not found");
    }

    @Override
    public void update(Task task) {
        boolean wasFound = savedTasks.removeIf(t -> t.getId().equals(task.getId()));
        if(!wasFound){
            throw new DataAccessException("Task with id " + task.getId() + " not found");
        }
        savedTasks.add(task);
    }
}
