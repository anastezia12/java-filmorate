package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.anotations.AfterDate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {
    private Long id;

    @NotBlank
    private String name;

    @Size(max = 200)
    private String description;

    @NotNull
    @AfterDate("1895-12-08")
    private LocalDate releaseDate;

    @NotNull
    @Positive
    private Integer duration;

    private Set<Long> likes = new HashSet<>();
}
