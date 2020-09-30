package com.votes.games;

import com.votes.data.dto.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

/**
 * Controller for game processing.
 */
@RestController
public class GamesController {

    @Autowired
    private GamesService gamesService;

    /**
     * Create game with players.
     *
     * @param createGameRequest CreateGameRequest request object with new game info.
     * @return created game.
     */
    @PostMapping(path = "/games")
    public Game createGame(@Valid @RequestBody CreateGameRequest createGameRequest) {
        try {
            return gamesService.createGameWithPlayers(createGameRequest);
        } catch (DuplicatedKeyWordException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Change keyword");
        }
    }

}
