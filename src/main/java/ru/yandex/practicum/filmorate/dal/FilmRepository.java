package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> {
    private static final String FIND_ALL_QUERY =
            "SELECT f.*, r.name AS mpa_name " +
                    "FROM film f " +
                    "LEFT JOIN mpa r ON f.mpa_id = r.id";

    private static final String FIND_BY_ID_QUERY =
            "SELECT f.*, r.name AS mpa_name " +
                    "FROM film f " +
                    "LEFT JOIN mpa r ON mpa_id = r.id " +
                    "WHERE f.id = ?";
    private static final String INSERT_QUERY = "INSERT INTO film(name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE film SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM film WHERE id = ?";
    private static final String GET_FILM_LIKES = "SELECT user_id FROM film_likes WHERE film_id = ?";
    private static final String ADD_LIKE = "INSERT INTO film_likes(film_id, user_id) VALUES (? , ?)";
    private static final String DELETE_LIKE = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public List<Film> findAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Optional<Film> findById(Long id) {
        return findOne(FIND_BY_ID_QUERY, id);
    }

    public Film save(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(id);
        return film;
    }

    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa() != null ? film.getMpa().getId() : null,
                film.getId()
        );

        return film;
    }

    public void clear() {
        jdbcTemplate.execute("DELETE FROM film");
        jdbcTemplate.execute("ALTER TABLE film ALTER COLUMN id RESTART WITH 1");

    }

    public void deleteById(Long id) {
        delete(DELETE_BY_ID_QUERY, id);
    }

    public List<Long> getFilmLikes(Long id) {
        return jdbcTemplate.queryForList(GET_FILM_LIKES, Long.class, id);
    }

    public void addLike(Long idFilm, Long userId) {
        update(ADD_LIKE, idFilm, userId);
    }

    public void deleteLike(Long idFilm, Long userId) {
        jdbcTemplate.update(DELETE_LIKE, idFilm, userId);
    }
}
