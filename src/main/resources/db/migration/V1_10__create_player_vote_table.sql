CREATE TABLE player_vote (
    room_id VARCHAR(36) NOT NULL,
    round INT NOT NULL,
    category_id VARCHAR(36) NOT NULL,
    target_player_id VARCHAR(36) NOT NULL,
    voter_id VARCHAR(36) NOT NULL,
    is_valid BOOLEAN,
    PRIMARY KEY(room_id, round, category_id, target_player_id, voter_id),
    FOREIGN KEY(room_id) REFERENCES room(id),
    FOREIGN KEY(category_id) REFERENCES categories(id)
);