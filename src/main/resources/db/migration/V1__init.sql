CREATE TABLE IF NOT EXISTS game
(
    id           INT                       NOT NULL AUTO_INCREMENT,
    name         VARCHAR(15)               NOT NULL COMMENT 'Game name.',
    amount       INT                       NOT NULL COMMENT 'Players amount for current game.',
    key_word     VARCHAR(15)               NOT NULL COMMENT 'Key word is used by players when they are joining to the game.',
    status       ENUM ('ACTIVE', 'CLOSED') NOT NULL COMMENT 'Game status.',
    round_number INT                       NOT NULL COMMENT 'Represents current round number.',
    created      TIMESTAMP                 NOT NULL COMMENT 'Creation timestamp.',
    PRIMARY KEY (id),
    UNIQUE key_word_unique (key_word, status)
);

CREATE TABLE IF NOT EXISTS player
(
    id              INT         NOT NULL AUTO_INCREMENT,
    game_id         INT         NOT NULL COMMENT 'Field for connection to table game.',
    player_name     VARCHAR(15) NOT NULL COMMENT 'Text value for player name.',
    player_number   INT         NOT NULL COMMENT 'It is value from 1 up to players amount in current game',
    score           INT         NOT NULL COMMENT 'Current score for player.',
    assigned        BOOLEAN     NOT NULL COMMENT 'This field shows that player has selected his name from the list of players.',
    selected_number INT COMMENT 'This field will be updated every round and will contain selected picture number.',
    own_number      INT COMMENT 'This field represents card number, which belongs to this player in current round.',
    PRIMARY KEY (id),
    INDEX score_in_game (game_id, score),
    UNIQUE player_in_game (game_id, player_name),
    FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE
);
