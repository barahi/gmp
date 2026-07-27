
CREATE TABLE player_vote (
    id VARCHAR(36) NOT NULL,
    room_id VARCHAR(36) NOT NULL,
    player_answer_id VARCHAR(36) NOT NULL,
    voter_id VARCHAR(36) NOT NULL,
    is_valid BOOLEAN NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(player_answer_id, voter_id),
    FOREIGN KEY(player_answer_id) REFERENCES player_answer(id) ON DELETE CASCADE,
    FOREIGN KEY(voter_id) REFERENCES player(id) ON DELETE CASCADE

);