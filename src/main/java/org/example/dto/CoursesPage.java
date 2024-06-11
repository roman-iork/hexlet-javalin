package org.example.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.Course;

import java.util.List;

@AllArgsConstructor
@Getter
public class CoursesPage extends BasePage {
    private List<Course> courses;
}
