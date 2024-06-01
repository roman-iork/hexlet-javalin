package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.Course;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CourseDAO {
    private Connection connection;

    public void save(Course course) throws SQLException {
        if (course.getId() == null) {
            var sql = "INSERT INTO courses (name, description, body) VALUES (?, ?, ?)";
            try (var preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, course.getDescription());
                preparedStatement.setString(3, course.getBody());
                preparedStatement.executeUpdate();
                var generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()) {
                    course.setId(generatedKey.getLong(1));
                } else {
                    throw new SQLException("no id from courses returned");
                }
            }
        } else {}
    }

    public List<String> receiveAll() throws SQLException {
        var courses = new ArrayList<String>();
        var sql = "SELECT * FROM courses";
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                courses.add(resultSet.getString("name"));
            }
        }
        return courses;
    }

    public void delete(String name) throws SQLException {
        var sql = "DELETE FROM courses WHERE name = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        }
    }
}
