CREATE TABLE categories (
    id VARCHAR(36) NOT NULL,
    game_settings_id VARCHAR(36) NOT NULL,
    category VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (game_settings_id) REFERENCES game_settings(id) ON DELETE CASCADE
);