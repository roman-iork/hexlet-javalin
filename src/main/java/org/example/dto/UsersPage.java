package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.User;

import java.util.List;

@AllArgsConstructor
@Getter
public class UsersPage {
    private List<User> users;
}
