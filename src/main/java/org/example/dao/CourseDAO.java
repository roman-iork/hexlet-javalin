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
        } else {
            var sql = "UPDATE courses SET name = ?, description = ?, body = ? WHERE id = ?";
            try (var preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, course.getName());
                preparedStatement.setString(2, course.getDescription());
                preparedStatement.setString(3, course.getBody());
                preparedStatement.setLong(4, course.getId());
                preparedStatement.executeUpdate();
            }
        }
    }

    public List<Course> receiveAll() throws SQLException {
        var courses = new ArrayList<Course>();
        var sql = "SELECT * FROM courses";
        try (var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                var id = resultSet.getLong("id");
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var body = resultSet.getString("body");
                var course = new Course(name, description, body);
                course.setId(id);
                courses.add(course);
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

    public Course find(Long id) throws SQLException {
        Course course = null;
        var sql = "SELECT * FROM courses WHERE id = ?";
        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var description = resultSet.getString("description");
                var body = resultSet.getString("body");
                course = new Course(name, description, body);
                course.setId(id);
            }
        }
        return course;
    }
}
