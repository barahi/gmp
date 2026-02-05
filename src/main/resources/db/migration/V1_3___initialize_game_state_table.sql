CREATE TABLE game_state (
    room_id VARCHAR(36) NOT NULL,
    game_phase_id VARCHAR(36) NOT NULL,
    current_round INT NOT NULL,
    PRIMARY KEY (room_id),
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
    FOREIGN KEY (game_phase_id) REFERENCES game_state_phases(id) ON DELETE CASCADE
);