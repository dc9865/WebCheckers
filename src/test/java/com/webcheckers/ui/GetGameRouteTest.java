package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class GetGameRouteTest {

    private GetGameRoute CuT;

    private TemplateEngine template;
    private PlayerLobby playerLobby;

    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    void testSetup() {

        template = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);

        when(request.session()).thenReturn(session);
        CuT = new GetGameRoute(template, playerLobby);

    }

    @Test
    void boardTest() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        when(playerLobby.getPlayer("red")).thenReturn(redPlayer);
        when(playerLobby.getPlayer("white")).thenReturn(whitePlayer);

        assertNotNull(redPlayer);
        assertNotNull(whitePlayer);

        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        assertFalse(redPlayer.inGame());
        assertFalse(whitePlayer.inGame());

        CuT.handle(request, response);

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();

        tester.assertViewModelAttribute("title", "Game page");
    }

    @Test
    void messageTest() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");

        assertNotNull(redPlayer);
        assertNotNull(whitePlayer);

        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        assertFalse(redPlayer.inGame());
        assertFalse(whitePlayer.inGame());

        CuT.handle(request, response);

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();

        //tester.assertViewModelAttribute("message", Message.info("Hi"));
        tester.assertViewModelAttribute("title", "Game page");

    }

    @Test
    void opponentTest() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        when(playerLobby.getPlayer("red")).thenReturn(redPlayer);
        when(playerLobby.getPlayer("white")).thenReturn(whitePlayer);

        assertNotNull(redPlayer);
        assertNotNull(whitePlayer);

        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        when(request.queryParams(ArgumentMatchers.eq("key"))).thenReturn("whitePlayer");
        when(request.session().attribute("whitePlayer")).thenReturn(whitePlayer);

        assertFalse(redPlayer.inGame());
        assertFalse(whitePlayer.inGame());

        CuT.handle(request, response);

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();

        tester.assertViewModelAttribute("currentUser", redPlayer);
        tester.assertViewModelAttribute("whitePlayer", whitePlayer);
        tester.assertViewModelAttribute("title", "Game page");
    }

    @Test
    void playTest() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        final Player redPlayer = new Player("red");
        final Player whitePlayer = new Player("white");
        when(playerLobby.getPlayer("red")).thenReturn(redPlayer);
        when(playerLobby.getPlayer("white")).thenReturn(whitePlayer);

        assertNotNull(redPlayer);
        assertNotNull(whitePlayer);

        when(request.session().attribute("currentUser")).thenReturn(redPlayer);
        when(request.session().attribute("redPlayer")).thenReturn(redPlayer);
        when(request.session().attribute("whitePlayer")).thenReturn(whitePlayer);
        when(request.session().attribute("activeColor")).thenReturn(Piece.Color.RED);

        redPlayer.startGame();
        whitePlayer.startGame();
        assertTrue(redPlayer.inGame());
        assertTrue(whitePlayer.inGame());

        CuT.handle(request, response);

        tester.assertViewModelExists();
        tester.assertViewModelIsaMap();

        tester.assertViewModelAttribute("currentUser", redPlayer);
        tester.assertViewModelAttribute("redPlayer", redPlayer);
        tester.assertViewModelAttribute("whitePlayer", whitePlayer);
        tester.assertViewModelAttribute("activeColor", Piece.Color.RED);
        tester.assertViewModelAttribute("viewMode", "PLAY");
        tester.assertViewModelAttribute("title", "Game page");
    }
}