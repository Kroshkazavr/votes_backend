package com.votes.games;

import com.votes.data.Status;
import com.votes.data.dto.Game;
import com.votes.data.dto.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateGameIntegrationTest extends BaseIntegrationTest {

    private static final String GAME_NAME = "game1";

    private static final String OTHER_GAME_NAME="game2";

    private static final List<String> PLAYERS_NAMES = Arrays.asList("one", "two");

    private static final List<String> OTHER_PLAYERS_NAMES = Arrays.asList("one2", "two2");

    private static final String KEY_WORD = "someKey";

    private static final String OTHER_KEY_WORD = "someOtherKey";

    private CreateGameRequest request;

    @Before
    public void setUp() {
        request = new CreateGameRequest();
        request.setGameName(GAME_NAME);
        request.setKeyWord(KEY_WORD);
        request.setPlayersNames(PLAYERS_NAMES);
    }

    @Test
    public void createGame_withAllFields_shouldCreateGameWithAllFields() throws Exception {
        String content = createGame().andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        Game game = objectMapper.readValue(content, Game.class);
        verifyGameFields(game);
        verifyPlayersFields(game.getPlayers());
    }

    @Test
    public void createGame_withNullName_shouldReturnBadRequest() throws Exception {
        request.setGameName(null);
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withTooSmallName_shouldReturnBadRequest() throws Exception {
        request.setGameName("kk");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withEmptyName_shouldReturnBadRequest() throws Exception {
        request.setGameName("");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withTooBigName_shouldReturnBadRequest() throws Exception {
        request.setGameName("SomeVeryLongGameNameWhichIsTooLong");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withNullKeyWord_shouldReturnBadRequest() throws Exception {
        request.setKeyWord(null);
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withTooSmallKeyWord_shouldReturnBadRequest() throws Exception {
        request.setKeyWord("kk");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withEmptyKeyWord_shouldReturnBadRequest() throws Exception {
        request.setKeyWord("");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withTooBigKeyWord_shouldReturnBadRequest() throws Exception {
        request.setKeyWord("SomeVeryLongKeyWordWhichIsTooLong");
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withOnePlayerName_shouldReturnBadRequest() throws Exception {
        request.setPlayersNames(Arrays.asList("one"));
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withNullPlayersNames_shouldReturnBadRequest() throws Exception {
        request.setPlayersNames(null);
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withEmptyPlayersNames_shouldReturnBadRequest() throws Exception {
        request.setPlayersNames(Collections.emptyList());
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withRepetitionsInPlayersNames_shouldReturnBadRequest() throws Exception {
        request.setPlayersNames(Arrays.asList("one", "one"));
        createGame().andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withDuplicateKeyWord_shouldReturnBadRequest() throws Exception {
        createGame().andExpect(status().isOk());
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName(OTHER_GAME_NAME);
        request.setKeyWord(KEY_WORD);
        request.setPlayersNames(OTHER_PLAYERS_NAMES);
        createGame(request).andExpect(status().isBadRequest());
    }

    @Test
    public void createGame_withSameGameName_shouldBeCreated() throws Exception {
        createGame().andExpect(status().isOk());
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName(GAME_NAME);
        request.setKeyWord(OTHER_KEY_WORD);
        request.setPlayersNames(OTHER_PLAYERS_NAMES);
        createGame(request).andExpect(status().isOk());
    }

    @Test
    public void createGame_withSamePlayersNames_shouldBeCreated() throws Exception {
        createGame().andExpect(status().isOk());
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName(OTHER_GAME_NAME);
        request.setKeyWord(OTHER_KEY_WORD);
        request.setPlayersNames(PLAYERS_NAMES);
        createGame(request).andExpect(status().isOk());
    }

    private void verifyPlayersFields(List<Player> players) {
        assertEquals(PLAYERS_NAMES.size(), players.size());
        int playerNumber = 1;
        for (String name : PLAYERS_NAMES) {
            verifyPlayer(playerNumber, name, players.get(playerNumber - 1));
            playerNumber++;
        }
    }

    private void verifyPlayer(int playerNumber, String name, Player player) {
        assertNotNull(player.getId());
        assertEquals(playerNumber, player.getPlayerNumber().intValue());
        assertEquals(name, player.getPlayerName());
        assertEquals(0, player.getScore().intValue());
        assertEquals(Boolean.FALSE, player.getAssigned());
        assertNull(player.getOwnNumber());
        assertNull(player.getSelectedNumber());
    }

    private void verifyGameFields(Game game) {
        assertEquals(GAME_NAME, game.getName());
        assertNotNull(game.getId());
        assertEquals(Status.ACTIVE, game.getStatus());
        assertEquals(1, game.getRoundNumber().intValue());
        assertNotNull(game.getCreated());
    }

    private ResultActions createGame() throws Exception {
        return createGame(request);
    }

    private ResultActions createGame(CreateGameRequest request) throws Exception {
        return mockMvc.perform(post("/games")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

}
