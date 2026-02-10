CREATE TABLE excluded_letter (
    game_settings_id VARCHAR(36) NOT NULL,
    letter VARCHAR(1) NOT NULL,
    PRIMARY KEY (game_settings_id, letter),
    FOREIGN KEY (game_settings_id) REFERENCES game_settings(id) ON DELETE CASCADE
);