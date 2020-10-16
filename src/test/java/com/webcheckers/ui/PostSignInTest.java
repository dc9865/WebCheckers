package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Tag("UI=TIER")
public class PostSignInTest {

    private static final String username="User";
    private static final String erroneousUsername="#User";

    private Request request;
    private Response response;
    private Session session;

    private TemplateEngine engine;
    private PlayerLobby lobby;
    private HashMap<String, Session> sessions;

    private PostSigninRoute CuT;

    @BeforeEach
    public void setup(){
        /* create mock objects */
        request=mock(Request.class);
        session=mock(Session.class);
        response=mock(Response.class);
        engine = mock(TemplateEngine.class);
        when(request.session()).thenReturn(session);
        /* create friendly */
        lobby = new PlayerLobby();
        /* create component under test object */
        CuT = new PostSigninRoute(engine, lobby, sessions);
    }

    @Test
    public void normalSignIn(){
        /* specify query parameter */
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(username);
        /* attempt to add to game */
        CuT.handle(request, response);
        /* check if the player was added successfully */
        assertTrue(lobby.getPlayers().containsKey(username), username + "was not added to the game.");
    }

    @Test
    public void badSignInAlphaNumeric(){
        /* specify query parameter (with bad username) */
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(erroneousUsername);
        /* attempt to add to game */
        CuT.handle(request, response);
        /* check if the player was added successfully */
        assertTrue(!lobby.getPlayers().containsKey(erroneousUsername), erroneousUsername + "was added to the game.");
    }

    @Test
    public void badSignInUsernameChosenAlready(){
        /* specify query parameter */
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(username);
        /* attempt to add to game */
        CuT.handle(request, response);
        /* attempt to add same name again */
        when(request.queryParams(eq(PostSigninRoute.USERNAME_PARAM))).thenReturn(username);
        CuT.handle(request, response);
        /* check if still only one player is in the lobby */
        assertTrue(lobby.getPlayers().size() == 1, "A second player with the same name joined.");
    }

}
