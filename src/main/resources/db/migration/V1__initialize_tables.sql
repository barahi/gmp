CREATE TABLE player (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE
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
    id VARCHAR(36) NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    max_players INT NOT NULL,
    round_duration INT NOT NULL,
    number_of_rounds INT NOT NULL,
    language VARCHAR(20) NOT NULL,
    password VARCHAR(100),
    PRIMARY KEY (id),
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
    game_settings_id VARCHAR(36) NOT NULL, 
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (game_settings_id),
    FOREIGN KEY (game_settings_id) REFERENCES game_settings(id) ON DELETE CASCADE
);

CREATE TABLE EXCLUDED_LETTERS (
    game_settings_id VARCHAR(36) NOT NULL, 
    letters VARCHAR(50) NOT NULL,
    PRIMARY KEY (game_settings_id),
    FOREIGN KEY (game_settings_id) REFERENCES game_settings(id) ON DELETE CASCADE
);