package bsa.services;

import bsa.dataaccess.task.TaskDAO;
import bsa.models.Task;
import bsa.services.exceptions.DomainLogicException;

import java.util.UUID;

public class TaskServiceImpl implements TaskService{
    private final TaskDAO taskDAO;

    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    public void create(String title) {
        // load N/A

        //verify
        validateTitle(title);

        // modify
        Task task = new Task(UUID.randomUUID(), title);

        // save
        taskDAO.create(task);
    }

    @Override
    public void updateTitle(UUID taskId, String title) {
        // load
        Task task = taskDAO.find(taskId);
        // verify
        validateTitle(title);
        // modify
        task.setTitle(title);
        // save
        taskDAO.update(task);
    }

    @Override
    public void updateDescription(UUID taskId, String description) {
        throw new UnsupportedOperationException("Fix me!");
    }

    @Override
    public void updateRemainingEstimate(UUID taskId, int estimate) {
        throw new UnsupportedOperationException("Fix me!");
    }

    @Override
    public void setAssignee(UUID taskId, UUID userId) {
        throw new UnsupportedOperationException("Fix me!");
    }

    private void validateTitle(String title) {
        if(title == null || "".equals(title)){
            throw new DomainLogicException("Task title cannot be empty");
        }
        int titleMaxLength = 50;
        if(title.length() > titleMaxLength){
            throw new DomainLogicException(
                    "Task title must be less than " + titleMaxLength + " characters"
            );
        }
    }
}
