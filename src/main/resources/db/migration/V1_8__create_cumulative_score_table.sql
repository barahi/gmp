CREATE TABLE cumulative_score (
    player_id VARCHAR(36) NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    score INT NOT NULL DEFAULT 0,
    PRIMARY KEY (player_id, room_id),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);