package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dal.FriendRepository;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Getter
    private final UserStorage userStorage;
    private final FriendRepository friendRepository;

    public UserService(@Qualifier("userDbStorage") UserStorage userStorage, FriendRepository friendRepository) {
        this.userStorage = userStorage;
        this.friendRepository = friendRepository;
    }

    public void addFriend(Long userId, Long friendId) {
        userContainsInStorage(userId);
        userContainsInStorage(friendId);

        friendRepository.addFriend(userId, friendId, FriendshipStatus.CONFIRMED.getId());
        friendRepository.addFriend(friendId, userId, FriendshipStatus.UNCONFIRMED.getId());
    }

    public void removeFriend(Long userId, Long friendId) {
        userContainsInStorage(userId);
        userContainsInStorage(friendId);
        if (friendRepository.getAllFriendsIdFromUserConfirmed(userId).contains(friendId)) {
            friendRepository.removeFriend(userId, friendId);
        }

    }

    public void userContainsInStorage(Long id) {
        if (!userStorage.containsUserWithKey(id)) {
            throw new IllegalArgumentException("User with id " + id + " is not exists");
        }
    }

    public List<User> getCommonFriend(Long firstUserId, Long secondUserId) {
        List<Long> friends1 = friendRepository.getCommonFriendsIds(firstUserId, secondUserId);
        return friends1.stream()
                .map(userStorage::getById)
                .filter(Objects::nonNull)
                .toList();
    }

    public List<User> getAllFriends(Long id) {
        userContainsInStorage(id);
        List<Long> friends = friendRepository.getAllFriendsIdFromUserConfirmed(id);
        return friends.stream()
                .map(userStorage::getById)
                .filter(Objects::nonNull)
                .toList();
    }
}
