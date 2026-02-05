CREATE TABLE player_answer (
    id VARCHAR(36) NOT NULL,
    player_id VARCHAR(36) NOT NULL,
    category VARCHAR(36) NOT NULL,
    answer VARCHAR(50),
    score INT,
    PRIMARY KEY (id),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (category) REFERENCES categories(category) ON DELETE CASCADE
);

CREATE TABLE cumulative_score (
    player_id VARCHAR(36) NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    score INT,
    PRIMARY KEY (player_id),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE
);

CREATE TABLE game_state_phases (
    id VARCHAR(36) NOT NULL,
    phase VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

