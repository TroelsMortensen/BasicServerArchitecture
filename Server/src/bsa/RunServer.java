package bsa;

import bsa.dataaccess.task.TaskDAO;
import bsa.dataaccess.task.TaskPostgresDAO;
import bsa.dataaccess.user.UserDAO;
import bsa.dataaccess.user.UserPostgresDAO;
import bsa.network.RequestHandler;
import bsa.network.SocketServer;
import bsa.services.TaskService;
import bsa.services.TaskServiceImpl;
import bsa.services.UserService;
import bsa.services.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class RunServer {

    public static void main(String[] args) throws SQLException, IOException {

        // data access layer
        UserDAO userDAO = new UserPostgresDAO(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test",
                "postgres",
                "TroelsDatabase");
        TaskDAO taskDAO = new TaskPostgresDAO(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test",
                "postgres",
                "TroelsDatabase");

        // service layer
        TaskService taskService = new TaskServiceImpl(taskDAO);
        UserService userService = new UserServiceImpl(userDAO);

        // network layer
        RequestHandler requestHandler = new RequestHandler(userService, taskService);
        SocketServer server = new SocketServer(requestHandler);
        server.startServer();
    }
}
