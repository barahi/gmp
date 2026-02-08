CREATE TABLE categories (
    room_id VARCHAR(36) NOT NULL,
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (room_id, category),
    FOREIGN KEY (room_id) REFERENCES game_settings(room_id) ON DELETE CASCADE
);