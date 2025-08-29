package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    private User user;
    private UserController userController;
    private UserStorage userStorage;

    @BeforeEach
    public void setUp() {
        userStorage = new InMemoryUserStorage();
        userController = new UserController(userStorage, new UserService(userStorage));
        user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
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
