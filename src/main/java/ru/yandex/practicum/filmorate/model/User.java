package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[^\\s]+$", message = "Login must not contain spaces")
    private String login;
    private String name;
    @NotNull
    @Past
    private LocalDate birthday;
    private Map<Long, FriendshipStatus> idOfFriends;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = (name == null || name.isBlank()) ? login : name;
        this.birthday = birthday;
        idOfFriends = new HashMap<>();
    }
}
