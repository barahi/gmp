CREATE TABLE game_settings (
    id VARCHAR(36) NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    max_players INT NOT NULL,
    round_duration INT NOT NULL,
    number_of_rounds INT NOT NULL,
    language VARCHAR(20) NOT NULL,
    password VARCHAR(100),
    PRIMARY KEY(id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);