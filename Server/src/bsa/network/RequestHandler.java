package bsa.network;

import bsa.services.TaskService;
import bsa.services.UserService;
import requestobjects.task.CreateTaskRequest;
import requestobjects.user.CreateUserRequest;
import requestobjects.RequestObject;
import requestobjects.user.UpdateUsernameRequest;

public class RequestHandler {
    private final UserService userService;
    private final TaskService taskService;

    public RequestHandler(UserService userService, TaskService taskService) {

        this.userService = userService;
        this.taskService = taskService;
    }

    public Object handle(RequestObject requestObject) {
        String requestType = requestObject.getRequestType();
        Object payload = requestObject.getPayload();
        String prefix = requestType.split("/")[0];
        String action = requestType.split("/")[1];

        switch (prefix){
            case "user" : return handleUserActions(action, payload);
            case "task" : return handleTaskActions(action, payload);
        }
        throw new RuntimeException("Handler '" + prefix + "' not found");
    }

    private Object handleUserActions(String action, Object payload) {
        switch (action){
            case "create" : {
                CreateUserRequest cur = (CreateUserRequest)payload;
                return userService.create(cur.getUsername(), cur.getPassword());
            }
            case "updateusername" : {
                UpdateUsernameRequest uur = (UpdateUsernameRequest) payload;
                userService.updateUsername(uur.getId(), uur.getNewUsername());
                return null;
            }
            default : throw new RuntimeException("Action '" + action + "' not recognized");
        }
    }

    private Object handleTaskActions(String action, Object payload) {
        switch (action){
            case "create" : {
                CreateTaskRequest request = (CreateTaskRequest) payload;
                return taskService.create(request.getTitle());
            }
            default : throw new RuntimeException("Action '" + action + "' not recognized");
        }
    }
}
