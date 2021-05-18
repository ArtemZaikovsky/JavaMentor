package jm.task.core.jdbc.dao;

import java.sql.*;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = new Util().getConnection();Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE Users(" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(45)," +
                    "lastName VARCHAR(45)," +
                    "age BINARY," +
                    "PRIMARY KEY(id)" +
                    ");");
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = new Util().getConnection();Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE Users;");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?)";
        try (Connection connection = new Util().getConnection();PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            System.out.println("user с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = new Util().getConnection();PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> result = new ArrayList<>();
        String query = "SELECT * FROM Users;";
        try (Connection connection = new Util().getConnection();Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                result.add(user);
            }
        } catch (SQLException e) {

        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        String query = "TRUNCATE TABLE Users";
        try (Connection connection = new Util().getConnection();Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
