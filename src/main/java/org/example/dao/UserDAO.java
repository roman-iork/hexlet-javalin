package org.example.dao;

import io.javalin.http.NotFoundResponse;
import lombok.AllArgsConstructor;
import org.example.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserDAO {
    private Connection connection;

    public void save(User user) throws SQLException {
        if (user.getId() == null) {
            var sql = "INSERT INTO users (first_name, email, password) VALUES (?, ?, ?)";
            try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3, user.getPassword());
                preparedStatement.setLong(4, user.getId());
                preparedStatement.executeUpdate();
            }
        }
    }

    public List<String> receiveAll() throws SQLException {
        var users = new ArrayList<String>();
        var sql = "SELECT * FROM users";
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                users.add(resultSet.getString("first_name"));
            }
        }
        return users;
    }

    public void delete(String firstName) throws SQLException {
        var sql = "DELETE FROM users WHERE first_name = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.executeUpdate();
        }
    }

    public User find(String name) throws SQLException {
        User user = null;
        var sql = "SELECT * FROM users WHERE first_name = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var id = resultSet.getLong(1);
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
}
