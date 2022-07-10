package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Statement statement;

        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.executeUpdate("USE UsersDB");
        } catch (SQLException e) {

        }
        try {
            statement.executeUpdate("CREATE TABLE Users (id BIGINT AUTO_INCREMENT, name  VARCHAR(255),lastName  VARCHAR(255),age TINYINT UNSIGNED, PRIMARY KEY (id)  )");
        } catch (SQLException e) {

        }
    }

    public void dropUsersTable() {
        Statement statement;
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement.executeUpdate("USE usersdb");
           statement.executeUpdate("DROP table Users");
        } catch (SQLException e) {

        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement pStatement;
        try {
            pStatement = Util.getConnection().prepareStatement("INSERT users(name,lastname,age) values (?,?,?)");
            pStatement.setString(1,name);
            pStatement.setString(2,lastName);
            pStatement.setByte(3,age);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("User с именем - " + name + " добавлен в базу данных");
       // pStatement.executeUpdate("USE UsersDB");
    }

    public void removeUserById(long id) {
        PreparedStatement pStatement;
        try {
            pStatement = Util.getConnection().prepareStatement("DELETE FROM users where id = ?");
            pStatement.setLong(1,id);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Statement statement;
        ResultSet resultSet;
        User user;

        try {
            statement = Util.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        Statement statement;
        try {
            statement = Util.getConnection().createStatement();
            statement.executeUpdate("USE UsersDB");
            statement.executeUpdate("DELETE FROM users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
