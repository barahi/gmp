CREATE TABLE lobby (
    id VARCHAR(36) PRIMARY KEY,
    game_settings_id VARCHAR(36) NOT NULL,
    player_ids VARCHAR ARRAY NOT NULL,
    is_game_started BOOLEAN NOT NULL,
    FOREIGN KEY (game_settings_id) REFERENCES game_settings(id)
);
