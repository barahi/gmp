CREATE TABLE player (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE game_settings (
    id VARCHAR(36) PRIMARY KEY,
    room_id VARCHAR(36) NOT NULL,        -- Required identifier
    player_count INTEGER NOT NULL,
    categories VARCHAR ARRAY NOT NULL,
    round_duration INTEGER NOT NULL,
    number_of_rounds INTEGER NOT NULL,
    password VARCHAR(255),               -- Nullable (optional admin password)
    excluded_letters VARCHAR ARRAY       -- Nullable (optional list of letters)
);
