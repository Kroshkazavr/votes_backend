package com.votes.games;

import com.votes.data.Status;
import com.votes.data.dao.Game;
import com.votes.data.dao.Player;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for game DB-interacting services.
 */
@Service
@CommonsLog
public class GamesService {

    @Autowired
    private GameRepository gameRepository;

    /**
     * The method returns DTO-object created according to CreateGameRequest with payload
     * for new game creating.
     *
     * @param createGameRequest CreateGameRequest with payload for new game creating.
     * @return Game DTO-object with new game.
     */
    public com.votes.data.dto.Game createGameWithPlayers(CreateGameRequest createGameRequest) throws DuplicatedKeyWordException {
        Game savedGame = saveGame(createGame(createGameRequest));
        log.info("The game was saved in DB:" + savedGame);

        com.votes.data.dto.Game gameDto = new com.votes.data.dto.Game();
        gameDto.setId(savedGame.getId());
        gameDto.setName(savedGame.getName());
        gameDto.setStatus(savedGame.getStatus());
        gameDto.setRoundNumber(savedGame.getRoundNumber());
        gameDto.setCreated(savedGame.getCreated());
        gameDto.setPlayers(transformPlayersDaoToDto(savedGame.getPlayers()));
        return gameDto;
    }

    private Game saveGame(Game game) throws DuplicatedKeyWordException {
        try {
            return gameRepository.save(game);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedKeyWordException();
        }
    }

    /**
     * The method returns Game DAO-object created according to CreateGameRequest
     * with payload for new game creating.
     *
     * @param createGameRequest CreateGameRequest with payload for new game creating.
     * @return Game object with new game.
     */
    private Game createGame(CreateGameRequest createGameRequest) {
        Game game = Game.builder()
                .keyWord(createGameRequest.getKeyWord())
                .name(createGameRequest.getGameName())
                .amount(createGameRequest.getPlayersNames().size())
                .roundNumber(1)
                .status(Status.ACTIVE)
                .build();
        game.setPlayers(createGamePlayers(game, createGameRequest));
        return game;
    }

    /**
     * The method will create DAO-object players for the current game according to the
     * playersNames field from GamesRequest and initiate player ID for each player,
     * after it return the set of players.
     *
     * @param game              Game object with new game.
     * @param createGameRequest CreateGameRequest with payload for new game creating.
     * @return list of created players.
     */
    private List<Player> createGamePlayers(Game game, CreateGameRequest createGameRequest) {
        int playerNumber = 1;
        List<Player> players = new ArrayList<>();
        for (String playerName : createGameRequest.getPlayersNames()) {
            Player player = Player.builder()
                    .playerName(playerName)
                    .playerNumber(playerNumber)
                    .score(0)
                    .assigned(false)
                    .game(game)
                    .build();
            playerNumber++;
            players.add(player);
        }
        return players;
    }

    /**
     * The method will create DTO-player object for each DAO-player object.
     *
     * @param players list of players from the saved game in DB.
     * @return list of players transformed from DAO-players object to DTO-players object.
     */
    private List<com.votes.data.dto.Player> transformPlayersDaoToDto(List<Player> players) {
        return players.stream()
                .map(player -> {
                    com.votes.data.dto.Player playerDto = new com.votes.data.dto.Player();
                    playerDto.setId(player.getId());
                    playerDto.setPlayerName(player.getPlayerName());
                    playerDto.setAssigned(player.getAssigned());
                    playerDto.setScore(player.getScore());
                    playerDto.setPlayerNumber(player.getPlayerNumber());
                    playerDto.setSelectedNumber(player.getSelectedNumber());
                    playerDto.setOwnNumber(player.getOwnNumber());
                    return playerDto;
                })
                .collect(Collectors.toList());
    }

}
