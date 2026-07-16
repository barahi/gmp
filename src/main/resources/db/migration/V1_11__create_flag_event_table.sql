CREATE TABLE flag_event (
    id VARCHAR(36) PRIMARY KEY,
    player_answer_id VARCHAR(36) NOT NULL,
    flagger_player_id VARCHAR(36) NOT NULL,
    FOREIGN KEY(player_answer_id) REFERENCES player_answer(id),
    FOREIGN KEY(flagger_player_id) REFERENCES player(id),
    UNIQUE(player_answer_id, flagger_player_id)
);