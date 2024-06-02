package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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
        } else {}
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
}
