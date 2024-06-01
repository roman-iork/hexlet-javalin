package org.example.dto;

import io.javalin.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BuildCoursePage {
    private String name;
    private String description;
    private String body;
    private Map<String, List<ValidationError<Object>>> errors;
}
