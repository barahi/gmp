CREATE TABLE excluded_letter (
    room_id VARCHAR(36) NOT NULL,
    letter VARCHAR(1) NOT NULL,
    PRIMARY KEY (room_id, letter),
    FOREIGN KEY (room_id) REFERENCES game_settings(room_id) ON DELETE CASCADE
);