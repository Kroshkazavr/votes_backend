package com.votes.games;

import com.votes.data.dto.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class GamesControllerUnitTest {

    @InjectMocks
    private GamesController gamesController = new GamesController();

    @Mock
    private GamesService gamesService;

    private CreateGameRequest request;

    @Before
    public void setUp() {
        request = new CreateGameRequest();
    }

    @Test
    public void whenServiceCreatesGame_createGameShouldCallService() throws Exception {
        when(gamesService.createGameWithPlayers(request)).thenReturn(new Game());
        gamesController.createGame(request);
        verify(gamesService, times(1)).createGameWithPlayers(request);
    }

    @Test
    public void whenServiceCreatesGame_createGameShouldCreateNewGame() throws Exception {
        Game game = new Game();
        when(gamesService.createGameWithPlayers(request)).thenReturn(game);
        Game returnedGame = gamesController.createGame(request);
        assertEquals(returnedGame, game);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenServiceThrowsException_createGameShouldThrowException() throws Exception {
        when(gamesService.createGameWithPlayers(request)).thenThrow(DuplicatedKeyWordException.class);
        gamesController.createGame(request);
    }

}
