package ru.yandex.practicum.filmorate.controllerTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class UserControllerTest {
    private User user = new User("new@email.com", "login", "name", LocalDate.now().minusDays(10));
    private UserController userController;

    @Autowired
    public void setUp(UserController userController) {
        this.userController = userController;
    }

    @Test
    @Order(3)
    public void canAddCorrectUser() {
        int size = userController.getAll().size();
        userController.addUser(user);
        assertEquals(++size, userController.getAll().size());
    }

    @Test
    @Order(4)
    public void canUpdateExistingUser() {
        userController.addUser(user);
        user.setId(1L);
        user.setName("New name");
        assertEquals(user, userController.updateUser(user));
    }

    @Test
    @Order(1)
    public void canReturnEmptyListWhenNoUsers() {
        assertTrue(userController.getAll().isEmpty());
    }

    @Test
    @Order(2)
    public void canReturnUsersWithMultipleUsers() {
        userController.addUser(user);
        userController.addUser(user);
        userController.addUser(user);
        assertEquals(3, userController.getAll().size());
        assertTrue(userController.getAll().stream().allMatch(x -> x.getName().equals("name")));
    }
}
