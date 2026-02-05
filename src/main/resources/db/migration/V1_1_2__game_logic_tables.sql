CREATE TABLE player_answer  (
    id VARCHAR(36) NOT NULL UNIQUE,
    player_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    answer VARCHAR(50),
    score INT,
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

CREATE TABLE cumulative_score (
    player_id VARCHAR(36) NOT NULL UNIQUE,
    room_id VARCHAR(36) NOT NULL,
    score INT,
    PRIMARY KEY (player_id),
    FOREIGN KEY (player_id) REFERENCES player(id),
    FOREIGN KEY (room_id) REFERENCES room(id),
);

CREATE TABLE game_state_phases (
    id VARCHAR(36) NOT NULL UNIQUE,
    phase VARCHAR(50) NOT NULL UNIQUE,
);

CREATE TABLE game_state (
    room_id VARCHAR(36) NOT NULL UNIQUE,
    game_phase_id VARCHAR(36) NOT NULL,
    current_round INTEGER NOT NULL,
    PRIMARY KEY(room_id),
    FOREIGN KEY(game_phase_id) REFERENCES game_state_phases(id)
);




