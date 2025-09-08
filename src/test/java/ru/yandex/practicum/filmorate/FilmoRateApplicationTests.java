package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.BaseRepository;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.dal.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@JdbcTest
@AutoConfigureTestDatabase
@Import({UserDbStorage.class, UserRepository.class, BaseRepository.class, UserRowMapper.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmoRateApplicationTests {

    private final UserDbStorage userStorage;

    @BeforeEach
    void setUp() {
        userStorage.clear();
        for (int i = 0; i < 5; i++) {
            userStorage.addUser(new User(
                    "user" + i + "@email.com",
                    "login" + i,
                    "User " + i,
                    LocalDate.now().minusYears(20 + i)
            ));
        }
    }

    @Test
    void testFindUserById() {
        User user = userStorage.getById(1L);

        assertThat(user)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("login", "login0")
                .hasFieldOrPropertyWithValue("name", "User 0");
    }

    @Test
    void testAddUser() {
        userStorage.addUser(new User("email@gmail.com", "login", "newUser", LocalDate.now().minusDays(2)));

        User user = userStorage.getById(6L);
        assertThat(user)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 6L)
                .hasFieldOrPropertyWithValue("login", "login")
                .hasFieldOrPropertyWithValue("name", "newUser");

    }

    @Test
    void testFindAll() {
        List<User> users = userStorage.getModel();
        User user1 = users.getFirst();
        User userLast = users.getLast();
        assertThat(user1)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("login", "login0")
                .hasFieldOrPropertyWithValue("name", "User 0");
        assertThat(userLast)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 5L)
                .hasFieldOrPropertyWithValue("login", "login4")
                .hasFieldOrPropertyWithValue("name", "User 4");


    }

    @Test
    void testDeleteById() {
        userStorage.deleteById(1L);

        assertFalse(userStorage.containsUserWithKey(1L));
    }

    @Test
    void testUpdateUser() {
        User user = new User("changed@gmail.com", "changed", "changed", LocalDate.now().minusDays(1));
        user.setId(1L);
        userStorage.updateUser(user);
        User changed = userStorage.getById(1L);
        assertThat(changed)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("login", "changed")
                .hasFieldOrPropertyWithValue("name", "changed")
                .hasFieldOrPropertyWithValue("email", "changed@gmail.com");
    }


}
