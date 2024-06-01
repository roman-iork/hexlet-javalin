package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {
    private Long id;
    private String name;
    private String description;
    private String body;

    public Course(String name, String description, String body) {
        this.name = name;
        this.description = description;
        this.body = body;
    }
}
