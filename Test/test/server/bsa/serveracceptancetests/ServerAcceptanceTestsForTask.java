package server.bsa.serveracceptancetests;

import bsa.dataaccess.task.TaskDAO;
import bsa.dataaccess.task.TaskPostgresDAO;
import bsa.dataaccess.user.UserDAO;
import bsa.dataaccess.user.UserPostgresDAO;
import bsa.models.Task;
import bsa.network.RequestHandler;
import bsa.services.TaskService;
import bsa.services.TaskServiceImpl;
import bsa.services.UserService;
import bsa.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestobjects.RequestObject;
import requestobjects.task.CreateTaskRequest;
import server.bsa.DatabaseTestUtil;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ServerAcceptanceTestsForTask {

    private RequestHandler requestHandler;
    private TaskDAO taskDAO;

    @BeforeEach
    public void setupEntireSystem() throws SQLException {
        taskDAO = new TaskPostgresDAO(DatabaseTestUtil.PGURI, DatabaseTestUtil.USERNAME, DatabaseTestUtil.PASSWORD);
        TaskService taskService = new TaskServiceImpl(taskDAO);
        requestHandler = new RequestHandler(null, taskService);
    }

    @BeforeEach
    public void clearDatabase() throws SQLException {
        DatabaseTestUtil.clearTaskTable();
    }

    @Test
    public void should_create_task_with_valid_title_and_store_in_database(){
        // arrange
        String requestType = "task/create";
        String actualTitle = "As a user bla bla bla";
        CreateTaskRequest payload = new CreateTaskRequest(actualTitle);
        RequestObject requestObject = new RequestObject(requestType, payload);

        // act
        Object reponsePayload = requestHandler.handle(requestObject);
        UUID createdTaskId = (UUID) reponsePayload;

        // assert
        Task task = taskDAO.find(createdTaskId);
        assertEquals(actualTitle, task.getTitle());
    }
}
