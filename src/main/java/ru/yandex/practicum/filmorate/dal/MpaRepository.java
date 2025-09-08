package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.MPA;

import java.util.List;
import java.util.Optional;

@Repository
public class MpaRepository extends BaseRepository<MPA> {
    private static final String GET_ALL_MPA =
            "SELECT * FROM  mpa ";

    private static final String GET_MPA_BY_ID = "SELECT * FROM  mpa WHERE id = ?";


    public MpaRepository(JdbcTemplate jdbc, RowMapper<MPA> mapper) {
        super(jdbc, mapper);
    }

    public Optional<MPA> findById(Long id) {
        return findOne(GET_MPA_BY_ID, id);
    }

    public List<MPA> getAllMpa() {
        return findMany(GET_ALL_MPA);
    }

}
