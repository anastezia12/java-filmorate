package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

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

    @JsonCreator
    public User(
            @JsonProperty("email") String email,
            @JsonProperty("login") String login,
            @JsonProperty("name") String name,
            @JsonProperty("birthday") LocalDate birthday
    ) {
        this.email = email;
        this.login = login;
        this.name = (name == null || name.isBlank()) ? login : name;
        this.birthday = birthday;
    }
}
