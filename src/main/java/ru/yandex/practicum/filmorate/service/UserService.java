package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {

    @Getter
    private final UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long userId, Long friendId) {
        userContainsInStorage(userId);
        userContainsInStorage(friendId);
        if (userStorage.getById(userId).getIdOfFriends().containsKey(friendId)) {
            throw new IllegalArgumentException("User with id " + friendId + " is already in friends");
        }
        if (Objects.equals(userId, friendId)) {
            throw new IllegalArgumentException("User can not add himself to friends");
        }
        userStorage.getById(userId).getIdOfFriends().put(friendId, FriendshipStatus.CONFIRMED );
        userStorage.getById(friendId).getIdOfFriends().put(userId, FriendshipStatus.UNCONFIRMED);
    }

    public void removeFriend(Long userId, Long friendId) {
        userContainsInStorage(userId);
        userContainsInStorage(friendId);
        Set<Long> friends = userStorage.getById(userId).getIdOfFriends().keySet();
        friends.remove(friendId);
        userStorage.getById(friendId).getIdOfFriends().remove(userId);
    }

    public void userContainsInStorage(Long id) {
        if (!userStorage.containsUserWithKey(id)) {
            throw new IllegalArgumentException("User with id " + id + " is not exists");
        }
    }

    public List<User> getCommonFriend(Long firstUserId, Long secondUserId) {
        userContainsInStorage(firstUserId);
        userContainsInStorage(secondUserId);
        Set<Long> firstUserFriends = userStorage.getById(firstUserId).getIdOfFriends().keySet();
        Set<Long> secondUserFriends = userStorage.getById(secondUserId).getIdOfFriends().keySet();

        firstUserFriends.retainAll(secondUserFriends); // теперь мы работаем с копией
        return firstUserFriends.stream()
                .map(userStorage::getById)
                .toList();
    }

    public List<User> getAllFriends(Long id) {
        userContainsInStorage(id);
        Set<Long> friends = userStorage.getById(id).getIdOfFriends().keySet();
        return friends.stream()
                .map(userStorage::getById)
                .toList();
    }
}
