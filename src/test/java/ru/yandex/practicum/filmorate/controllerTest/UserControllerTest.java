package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserControllerTest {
    private User user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
    @Autowired
    private UserController userController;
    @Autowired
    private UserStorage userStorage;

    @BeforeEach
    public void clear() {
        userStorage.clear();
    }

    @Test
    public void canAddCorrectUser() {
        User postedUser = userController.addUser(user);
        user.setId(1L);
        assertEquals(user, postedUser);
    }

    @Test
    public void canUpdateExistingUser() {
        userController.addUser(user);
        user.setId(1L);
        user.setName("New name");
        assertEquals(user, userController.updateUser(user));
        assertEquals(1, userController.getAll().size());
    }

    @Test
    public void canReturnEmptyListWhenNoUsers() {
        assertTrue(userController.getAll().isEmpty());
    }

    @Test
    public void canReturnUsersWithMultipleUsers() {
        userController.addUser(user);
        userController.addUser(user);
        userController.addUser(user);
        assertEquals(3, userController.getAll().size());
        assertTrue(userController.getAll().stream().allMatch(x -> x.getName().equals("name")));
    }
}
