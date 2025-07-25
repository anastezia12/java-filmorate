package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserControllerTest {
    private User user;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
        user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
    }

    @Test
    public void canAddCorrectUser() {
        User postedUser = userController.post(user);
        user.setId(1L);
        assertEquals(user, postedUser);
    }

    @Test
    public void canUpdateExistingUser() {
        userController.post(user);
        user.setId(1L);
        user.setName("New name");
        assertEquals(user, userController.update(user));
        assertEquals(1, userController.getAll().size());
    }

    @Test
    public void canReturnEmptyListWhenNoUsers() {
        assertTrue(userController.getAll().isEmpty());
    }

    @Test
    public void canReturnUsersWithMultipleUsers() {
        userController.post(user);
        userController.post(user);
        userController.post(user);
        assertEquals(3, userController.getAll().size());
        assertTrue(userController.getAll().stream().allMatch(x -> x.getName().equals("name")));
    }
}
