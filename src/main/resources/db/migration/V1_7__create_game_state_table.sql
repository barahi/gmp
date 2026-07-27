CREATE TABLE game_state (
    room_id VARCHAR(36) NOT NULL,
    phase VARCHAR(50) NOT NULL,
    current_round INT NOT NULL,
    current_letter CHAR(1),
    PRIMARY KEY (room_id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);