package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;

    private String firstName;
    private String email;
    private String password;

    public User(String firstName, String email, String password) {
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }
}
