CREATE TABLE player_answer (
    player_id VARCHAR(36) NOT NULL,
    category VARCHAR(36) NOT NULL,
    round INT NOT NULL,
    answer VARCHAR(50),
    score INT,
    PRIMARY KEY (player_id, round, category),
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);
