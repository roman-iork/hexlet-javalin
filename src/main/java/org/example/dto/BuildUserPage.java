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
public class BuildUserPage extends BasePage {
    private String first_name;
    private String email;
    private Map<String, List<ValidationError<Object>>> errors;
}
