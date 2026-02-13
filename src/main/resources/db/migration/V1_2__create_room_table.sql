CREATE TABLE room (
    id VARCHAR(36) NOT NULL,
    host_player_id VARCHAR(36) NOT NULL,
    is_game_started BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (host_player_id) REFERENCES player(id)
);