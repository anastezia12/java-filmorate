package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import ru.yandex.practicum.filmorate.anotations.NoSpaces;

import java.time.LocalDate;

@Data
public class User {
    private Long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NoSpaces
    private String login;
    private String name;
    @NotNull
    @Past
    private LocalDate birthday;
}
