CREATE TABLE room (
    id VARCHAR(36) PRIMARY KEY,
    host_player_id VARCHAR(36) NOT NULL,
    player_ids VARCHAR ARRAY NOT NULL,
    max_players INTEGER NOT NULL,
    round_count INTEGER NOT NULL,
    language VARCHAR(50),
    status VARCHAR(20) NOT NULL,
    current_round INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
