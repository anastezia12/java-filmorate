package ru.yandex.practicum.filmorate.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserStorage userStorage = new InMemoryUserStorage();
    private UserService userService = new UserService(userStorage);


    @BeforeEach
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            userStorage.addUser(new User("new@email.com", "login" + i, "name", LocalDate.now().minusDays(10)));
        }
    }

    @Test
    public void canAddFriendToUser() {
        userService.addFriend(1L, 2L);
        assertEquals(1, userStorage.getById(1L).getIdOfFriends().size());
        assertTrue(userStorage.getById(1L).getIdOfFriends().contains(2L));
    }

    @Test
    public void canAddFriendToUserAndBack() {
        userService.addFriend(1L, 2L);
        assertEquals(1, userStorage.getById(1L).getIdOfFriends().size());
        assertTrue(userStorage.getById(1L).getIdOfFriends().contains(2L));
        assertEquals(1, userStorage.getById(2L).getIdOfFriends().size());
        assertTrue(userStorage.getById(2L).getIdOfFriends().contains(1L));
    }

    @Test
    public void canNotAddUserWithSameIdToFriends() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.addFriend(4L, 4L)
        );

        assertEquals("User can not add himself to friends", exception.getMessage());
    }

    @Test
    public void canNotAddFriendToFriends() {
        userService.addFriend(1L, 2L);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> userService.addFriend(2L, 1L)
        );

        assertEquals("User with id 1 is already in friends", exception.getMessage());
    }

    @Test
    public void canDeleteFriend() {
        userService.addFriend(1L, 2L);
        userService.removeFriend(2L, 1L);
        assertEquals(0, userStorage.getById(2L).getIdOfFriends().size());
        assertEquals(0, userStorage.getById(1L).getIdOfFriends().size());
    }

    @Test
    public void canGet1CommonFriends() {
        userService.addFriend(1L, 2L);
        userService.addFriend(3L, 2L);

        assertEquals(1, userService.getCommonFriend(1L, 3L).size());
        assertEquals(2L, userService.getCommonFriend(1L, 3L).getFirst().getId());
    }

    @Test
    public void canGetMultipleCommonFriends() {
        userService.addFriend(1L, 2L);
        userService.addFriend(3L, 2L);


        userService.addFriend(1L, 4L);
        userService.addFriend(3L, 4L);


        userService.addFriend(1L, 5L);
        userService.addFriend(3L, 5L);
        assertEquals(3, userService.getCommonFriend(1L, 3L).size());
    }

    @Test
    public void canGetAllFriends() {
        userService.addFriend(1L, 2L);
        userService.addFriend(1L, 3L);

        assertEquals(2, userService.getAllFriends(1L).size());
    }
}
