package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        User mark = new User("Marko", "Polo", (byte) 5);
        User ben = new User("Benjamin", "Franklin", (byte) 7);
        User john = new User("John", "Smith", (byte) 4);
        User ivan = new User("Ivan", "Ivanov", (byte) 6);
        UserServiceImpl service = new UserServiceImpl();

        service.createUsersTable();
        service.saveUser(mark.getName(), mark.getLastName(), mark.getAge());
        service.saveUser(ben.getName(), ben.getLastName(), ben.getAge());
        service.saveUser(john.getName(), john.getLastName(), john.getAge());
        service.saveUser(ivan.getName(), ivan.getLastName(), ivan.getAge());
        List<User> data = service.getAllUsers();
        for (User user : data) {
            System.out.println(user);
        }
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}
