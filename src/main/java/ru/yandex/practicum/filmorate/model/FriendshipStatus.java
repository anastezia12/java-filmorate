package ru.yandex.practicum.filmorate.model;

public enum FriendshipStatus {
    UNCONFIRMED(1L),
    CONFIRMED(2L);

    private final Long id;

    FriendshipStatus(Long id) {
        this.id = id;
    }

    public static FriendshipStatus fromId(Long id) {
        for (FriendshipStatus status : values()) {
            if (status.id == id) return status;
        }
        throw new IllegalArgumentException("Unknown status id: " + id);
    }

    public Long getId() {
        return id;
    }
}
