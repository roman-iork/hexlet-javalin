package org.example.dao;

import io.javalin.http.NotFoundResponse;
import lombok.AllArgsConstructor;
import org.example.User;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserRepository extends BaseRepository {

    public void save(User user) throws SQLException {
        if (user.getId() == null) {
            var sql = "INSERT INTO users (first_name, email, password) VALUES (?, ?, ?)";
            try (var connection = dataSource.getConnection();
                 var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.executeUpdate();
                var generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("no id from users returned");
                }
            }
        } else {
            var sql = "UPDATE users SET first_name = ?, email = ?, password = ? WHERE id = ?";
            try (var connection = dataSource.getConnection();
                 var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setLong(4, user.getId());
                preparedStatement.executeUpdate();
            }
        }
    }

    public List<User> receiveAll() throws SQLException {
        var users = new ArrayList<User>();
        var sql = "SELECT * FROM users";
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("first_name");
                var email = resultSet.getString("email");
                var password = resultSet.getString("password");
                var user = new User(name, email, password);
                user.setId(id);
                users.add(user);
            }
        }
        return users;
    }

    public void delete(String firstName) throws SQLException {
        var sql = "DELETE FROM users WHERE first_name = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();
        }
    }

    public User find(Long id) throws SQLException {
        User user = null;
        var sql = "SELECT * FROM users WHERE id = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var firstName = resultSet.getString("first_name");
                var email = resultSet.getString("email");
                var password = resultSet.getString("password");
                user = new User(firstName, email, password);
                user.setId(id);
            }
        }
        if (user == null) {
            throw new NotFoundResponse("No user with such name!");
        }
        return user;
    }

    public User find(String name) throws SQLException {
        User user = null;
        var sql = "SELECT * FROM users WHERE first_name = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var email = resultSet.getString("email");
                var password = resultSet.getString("password");
                user = new User(name, email, password);
                user.setId(id);
            }
        }
        if (user == null) {
            throw new NotFoundResponse("No user with such name!");
        }
        return user;
    }

    public boolean contains(String firstName) {
        var sql = "SELECT first_name FROM users";
        var names = new ArrayList<String>();
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                var name = resultSet.getString(1);
                names.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return names.contains(firstName);
    }
}
