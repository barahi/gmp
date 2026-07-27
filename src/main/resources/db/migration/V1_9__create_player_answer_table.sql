CREATE TABLE player_answer (
    id VARCHAR(36) NOT NULL,
    player_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    game_settings_id VARCHAR(36) NOT NULL,
    round INT NOT NULL,
    answer VARCHAR(50),
    score INT,
    PRIMARY KEY(id),
    UNIQUE(player_id, category_id, round),
    FOREIGN KEY(player_id) REFERENCES player(id) ON DELETE CASCADE,
    FOREIGN KEY(category_id) REFERENCES categories(id) ON DELETE CASCADE,
    FOREIGN KEY(game_settings_id) REFERENCES game_settings(id) ON DELETE CASCADE
);
