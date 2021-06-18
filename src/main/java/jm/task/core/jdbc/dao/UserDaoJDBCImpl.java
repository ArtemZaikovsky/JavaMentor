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
        Connection connection = new Util().getConnection();
        Statement statement = connection.createStatement();
        try  {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement.execute("CREATE TABLE Users(" +
                    "id BIGINT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(45)," +
                    "lastName VARCHAR(45)," +
                    "age BINARY," +
                    "PRIMARY KEY(id)" +
                    ");");
            connection.commit();
        } catch (SQLException e) {

        } finally {
            statement.close();
            connection.close();
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = new Util().getConnection();
        Statement statement = connection.createStatement();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement.execute("DROP TABLE Users;");
            connection.commit();
        } catch (SQLException e) {

        } finally {
            statement.close();
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String query = "INSERT INTO Users(name, lastName, age) VALUES(?, ?, ?)";
        Connection connection = new Util().getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            connection.close();
        }
    }

    public void removeUserById(long id) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        Connection connection = new Util().getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            ps.setLong(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ps.close();
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> result = new ArrayList<>();
        String query = "SELECT * FROM Users;";
        Connection connection = new Util().getConnection();
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            rs = statement.executeQuery(query);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                result.add(user);
            }
            connection.commit();
        } catch (SQLException e) {

        } finally {
            rs.close();
            statement.close();
            connection.close();
        }
        return result;
    }

    public void cleanUsersTable() throws SQLException {
        String query = "TRUNCATE TABLE Users";
        Connection connection = new Util().getConnection();
        Statement statement = connection.createStatement();
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            statement.execute(query);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            statement.close();
            connection.close();
        }
    }
}