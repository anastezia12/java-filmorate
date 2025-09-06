package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.mappers.GenreRowMapper;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Repository
public class FilmGenreRepository {
    private static final String INSERT_FILM_GENRE = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";
    private static final String EXISTS_FILM_GENRE = "SELECT COUNT(*) FROM film_genres WHERE film_id = ? AND genre_id = ?";
    private static final String DELETE_ALL_GENRES_FROM_FILM = "DELETE FROM film_genres WHERE film_id = ?";
    private static final String ADD_FILM_GENRE = "INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)";


    private final JdbcTemplate jdbcTemplate;

    public FilmGenreRepository(JdbcTemplate jdbcTemplate, GenreRowMapper genreRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public void addGenresToFilm(Long filmId, List<Long> genres) {
        jdbcTemplate.update(DELETE_ALL_GENRES_FROM_FILM, filmId); // remove old genres

        for (Long genreId : genres) {
            jdbcTemplate.update(ADD_FILM_GENRE, filmId, genreId);
        }
    }

    public List<Genre> getGenresForFilm(Long filmId) {
        String sql = """
                SELECT g.id, g.name
                FROM genre g
                JOIN film_genres fg ON g.id = fg.genre_id
                WHERE fg.film_id = ?
                ORDER BY g.id
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Genre(rs.getLong("id"), rs.getString("name")), filmId);
    }

}
