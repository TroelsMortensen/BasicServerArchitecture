package server.bsa.servicetests.userservice;

import bsa.dataaccess.task.TaskDAO;
import bsa.dataaccess.user.UserDAO;
import bsa.models.Task;
import bsa.models.User;
import server.bsa.servicetests.GenericMockDAO;

public class MockDAOs {

    public static class UserMockDAO extends GenericMockDAO<User> implements UserDAO {

        @Override
        public User findByUsername(String username) {
            for (User user : savedEntities) {
                if(user.getUserName().equals(username)) return user;
            }
            return null;
        }
    }

    public static class TaskMockDAO extends GenericMockDAO<Task> implements TaskDAO{

    }

}
