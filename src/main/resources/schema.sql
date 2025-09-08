-- Table: mpa
CREATE TABLE IF NOT EXISTS mpa (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table: film
CREATE TABLE IF NOT EXISTS film (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    release_date DATE NOT NULL,
    duration INT NOT NULL,
    mpa_id BIGINT,
    CONSTRAINT fk_film_mpa FOREIGN KEY (mpa_id) REFERENCES mpa(id)
);

-- Table: users
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100),
    birthday DATE NOT NULL
);

-- Table: genre
CREATE TABLE IF NOT EXISTS genre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table: film_genres (many-to-many film ↔ genre)
CREATE TABLE IF NOT EXISTS film_genres (
    film_id BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    CONSTRAINT fk_fg_film FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE,
    CONSTRAINT fk_fg_genre FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE,
    CONSTRAINT uq_fg UNIQUE (film_id, genre_id)
);

-- Table: film_likes (many-to-many film ↔ user)
CREATE TABLE IF NOT EXISTS film_likes (
    film_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_fl_film FOREIGN KEY (film_id) REFERENCES film(id) ON DELETE CASCADE,
    CONSTRAINT fk_fl_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT uq_fl UNIQUE (film_id, user_id)
);

-- Table: friendship_status
CREATE TABLE IF NOT EXISTS friendship_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

-- Table: friends
CREATE TABLE IF NOT EXISTS friends (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    friend_id BIGINT NOT NULL,
    status_id BIGINT NOT NULL,
    CONSTRAINT fk_f_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_f_friend FOREIGN KEY (friend_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_f_status FOREIGN KEY (status_id) REFERENCES friendship_status(id),
    CONSTRAINT uq_friends UNIQUE (user_id, friend_id)
);

