CREATE TABLE player (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    is_priviledged BOOLEAN DEFAULT FALSE
);

CREATE TABLE room (
    id VARCHAR(36) NOT NULL,
    host_player_id VARCHAR(36) NOT NULL,
    is_game_started BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (host_player_id) REFERENCES player(id) ON DELETE CASCADE
);

CREATE TABLE game_settings (
    room_id VARCHAR(36) NOT NULL,
    max_players INT NOT NULL,
    round_duration INT NOT NULL,
    number_of_rounds INT NOT NULL,
    language VARCHAR(20) NOT NULL,
    password VARCHAR(100),
    PRIMARY KEY(room_id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE room_player (
    room_id VARCHAR(36) NOT NULL,
    player_id VARCHAR(36) NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (room_id, player_id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);
CREATE TABLE categories (
    room_id VARCHAR(36) NOT NULL,
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (room_id, category),
    FOREIGN KEY (room_id) REFERENCES game_settings(room_id) ON DELETE CASCADE
);

CREATE TABLE excluded_letter (
    room_id VARCHAR(36) NOT NULL,
    letter VARCHAR(1) NOT NULL,
    PRIMARY KEY (room_id, letter),
    FOREIGN KEY (room_id) REFERENCES game_settings(room_id) ON DELETE CASCADE
);
