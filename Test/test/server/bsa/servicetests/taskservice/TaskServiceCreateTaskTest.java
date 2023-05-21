package server.bsa.servicetests.taskservice;

import bsa.dataaccess.task.TaskDAO;
import bsa.dataaccess.task.TaskPostgresDAO;
import bsa.models.Task;
import bsa.services.TaskService;
import bsa.services.TaskServiceImpl;
import bsa.services.exceptions.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.bsa.DatabaseTestUtil;
import server.bsa.servicetests.userservice.MockDAOs;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceCreateTaskTest {

    protected TaskService taskService;
    protected TaskDAO taskDAO;

    public static class WithMockDAO extends TaskServiceCreateTaskTest{
        @BeforeEach
        public void setup() {
            taskDAO = new MockDAOs.TaskMockDAO();
            taskService = new TaskServiceImpl(taskDAO);
        }
    }

    public static class WithPostgresDAO extends TaskServiceCreateTaskTest{
        @BeforeEach
        public void setup() throws SQLException {
            DriverManager.registerDriver(new org.postgresql.Driver());
            taskDAO = new TaskPostgresDAO(DatabaseTestUtil.PGURI, DatabaseTestUtil.USERNAME, DatabaseTestUtil.PASSWORD);
            taskService = new TaskServiceImpl(taskDAO);
        }

        @BeforeEach
        public void cleanupDatabase() throws SQLException {
            DatabaseTestUtil.clearTaskTable();
        }
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "As a product owner I can create a task...",
                    "As a developer I can create a task..."
            }
    )
    public void should_create_task_with_valid_title(String title) {
        // arrange

        // act
        UUID id = taskService.create(title);
        // assert
        Task createdTask = taskDAO.find(id);
        assertEquals(title, createdTask.getTitle());
    }

    @DisplayName("Given invalid title, when creating task, then DomainLogicException is thrown.")
    @ParameterizedTest
    @ValueSource(
            strings = {
                    "",
                    "as a something with a very long title and name and other important stuff, I ..",
            }
    )
    public void should_fail_task_creation_given_invalid_title(String title) {
        // arrange --
        // act/assert

        assertThrows(DomainLogicException.class, () -> taskService.create(title));
    }
}
