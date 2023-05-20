package bsa;

import bsa.dataaccess.user.UserDAO;
import bsa.dataaccess.user.UserPostgresDAO;
import bsa.network.RequestHandler;
import bsa.network.SocketServer;
import bsa.services.UserService;
import bsa.services.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

public class RunServer {

    public static void main(String[] args) throws SQLException, IOException {

        UserDAO userDAO = new UserPostgresDAO(
                "jdbc:postgresql://localhost:5432/postgres?currentSchema=bsa_test",
                "postgres",
                "TroelsDatabase");
        UserService userService = new UserServiceImpl(userDAO);
        RequestHandler requestHandler = new RequestHandler(userService);
        SocketServer server = new SocketServer(requestHandler);
        server.startServer();
    }
}
