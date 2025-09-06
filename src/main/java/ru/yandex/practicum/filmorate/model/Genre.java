package ru.yandex.practicum.filmorate.model;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Genre {
    private Long id;
    private String name;
}
