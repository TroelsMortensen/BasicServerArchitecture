package server.bsa.servicetests.taskservice;

import bsa.models.Task;
import bsa.services.TaskService;
import bsa.services.TaskServiceImpl;
import bsa.services.exceptions.DomainLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.bsa.servicetests.userservice.MockDAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class TaskServiceTests {

    private TaskService taskService;
    private MockDAOs.TaskMockDAO taskDAO;

    @BeforeEach
    public void setup() {
        taskDAO = new MockDAOs.TaskMockDAO();
        taskService = new TaskServiceImpl(taskDAO);
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
        taskService.create(title);
        // assert
        Task createdTask = taskDAO.savedEntities.get(0);
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

    @ParameterizedTest
    @ValueSource(
            strings = {
                    "As a PO, I want to be able to rename the title",
                    "As a whatever, I want whatever"
            }
    )
    public void should_update_title_on_existing_task_with_valid_new_title(String title){
        // arrange
        UUID existingTaskId = UUID.randomUUID();
        Task task = new Task(existingTaskId, "As a dev, I want coffee");
        taskDAO.savedEntities = new ArrayList<>(List.of(task));

        // act

        taskService.updateTitle(existingTaskId, title);

        // assert
        Task updatedTask = taskDAO.savedEntities.get(0);
        assertEquals(title, updatedTask.getTitle());
    }

    // TODO create test for update title of existing task, with invalid title
}
