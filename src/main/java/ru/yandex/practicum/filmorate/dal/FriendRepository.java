package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;

import java.util.List;
import java.util.Optional;

@Repository
public class FriendRepository extends BaseRepository<Friend> {

    private static final String FIND_COMMON_FRIENDS_QUERY = " SELECT f1.friend_id " +
            " FROM friends f1 " +
            " JOIN friends f2 ON f1.friend_id = f2.friend_id " +
            " WHERE f1.user_id = ? " +
            " AND f2.user_id = ? " +
            " AND f1.status_id IN (?, ?) " +
            " AND f2.status_id IN (?, ?)";

    private static final String ADD_FRIEND_QUERY =
            "INSERT INTO friends(user_id, friend_id, status_id) VALUES (?, ?, ?)";

    private static final String DELETE_FRIEND_QUERY =
            "DELETE FROM friends WHERE id = ?";

    private static final String FIND_FRIENDSHIP_QUERY =
            "SELECT * FROM friends WHERE user_id=? AND friend_id=? AND status_id = ?";

    private static final String FIND_ALL_USER_FRIENDS =
            "SELECT * FROM friends WHERE user_id=? AND status_id = ?";
    private static final String UPDATE_FRIENDSHIP_STATUS =
            "UPDATE friends SET status_id = ? WHERE id = ?";

    public FriendRepository(JdbcTemplate jdbc, RowMapper<Friend> mapper) {
        super(jdbc, mapper);
    }

    public void addFriend(Long userId, Long friendId, Long status) {
        insert(ADD_FRIEND_QUERY, userId, friendId, status);
    }

    public boolean removeFriend(Long userId, Long friendId) {
        Optional<Friend> friendship = findOne(FIND_FRIENDSHIP_QUERY, userId, friendId, FriendshipStatus.CONFIRMED.getId());

        if (friendship.isEmpty()) {
            throw new IllegalArgumentException("Friendship not found for users: " + userId + " and " + friendId);
        }
        return delete(DELETE_FRIEND_QUERY, friendship.get().getId());
    }

    public List<Long> getAllFriendsIdFromUserConfirmed(Long userId) {
        List<Friend> friends = findMany(FIND_ALL_USER_FRIENDS, userId, FriendshipStatus.CONFIRMED.getId());
        return friends.stream()
                .map(Friend::getFriendId)
                .toList();
    }

    public List<Long> getAllFriendsIdFromUserUnconfirmed(Long userId) {
        List<Friend> friends = findMany(FIND_ALL_USER_FRIENDS, userId, FriendshipStatus.UNCONFIRMED.getId());
        return friends.stream()
                .map(Friend::getFriendId)
                .toList();
    }

    public Optional<Friend> findFriendshipConfirmed(Long userId, Long friendId) {
        return findOne(FIND_FRIENDSHIP_QUERY, userId, friendId, FriendshipStatus.CONFIRMED.getId());
    }

    public Optional<Friend> findFriendshipUNCONFIRMED(Long userId, Long friendId) {
        return findOne(FIND_FRIENDSHIP_QUERY, userId, friendId, FriendshipStatus.UNCONFIRMED.getId());
    }

    public void updateFriendshipStatus(Long userId, Long friendId, Long statusId) {
        update(UPDATE_FRIENDSHIP_STATUS, statusId, findFriendshipUNCONFIRMED(userId, friendId));
    }

    public List<Long> getCommonFriendsIds(Long userId1, Long userId2) {
        return jdbcTemplate.queryForList(FIND_COMMON_FRIENDS_QUERY,
                Long.class,
                userId1,
                userId2,
                FriendshipStatus.CONFIRMED.getId(),
                FriendshipStatus.UNCONFIRMED.getId(),
                FriendshipStatus.CONFIRMED.getId(),
                FriendshipStatus.UNCONFIRMED.getId());
    }
}