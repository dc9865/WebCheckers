package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import spark.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignoutTest {
    private static final String username="User";
    private static final String erroneousUser="erroneousUser";

    // The component under test
    private PostSignoutRoute CuT;

    // Friendly objects
    private PlayerLobby lobby;
    private HashMap<String, Session> sessions;

    // Attributes holding mock objects
    private TemplateEngine engine;
    private Request request;
    private  Session session;
    private Response response;

    @BeforeEach
    public void setup(){
        // Creates the mock objects
        request=mock(Request.class);
        session=mock(Session.class);
        response=mock(Response.class);

        // When this method is called, return session
        when(request.session()).thenReturn(session);

        engine=mock(TemplateEngine.class);


        // Creates the friendly objects
        lobby=new PlayerLobby();
        lobby.signIn(username);
        sessions=new HashMap<String, Session>();
        sessions.put(username, session);

        CuT=new PostSignoutRoute(engine, lobby, sessions);
    }

    @Test
    public void regularSignOutTest(){
        // Returns a player with the correct name
        when(request.session().attribute(GetHomeRoute.CURRENT_USER)).thenReturn(new Player(username));

        // Run the handel function
        CuT.handle(request, response);

        // Checks to make sure the player has been removed
        assertTrue(!lobby.getPlayers().containsKey(username), username+" is still in the game. ");
    }

    @Test
    public void erroneousSignoutTest(){
        // Returns a player with the erroneous name
        when(request.session().attribute(GetHomeRoute.CURRENT_USER)).thenReturn(new Player(erroneousUser));

        // Make sure that there is one person in the playerLobby called user
        assertTrue(lobby.getPlayers().containsKey(username) && lobby.getPlayers().size()==1);

        // Run the handel function
        CuT.handle(request, response);

        // Nothing should change in the lobby
        assertTrue(lobby.getPlayers().containsKey(username) && lobby.getPlayers().size()==1);
    }

    @Test
    public void multipleSignoutTest(){
        // Returns a player with the correct name
        when(request.session().attribute(GetHomeRoute.CURRENT_USER)).thenReturn(new Player(username));

        // Make sure that there is one person in the playerLobby caller user
        assertTrue(lobby.getPlayers().containsKey(username) && lobby.getPlayers().size()==1);

        // Run the handle function a first time
        CuT.handle(request, response);
        // Checks to make sure the player has been removed
        assertTrue(!lobby.getPlayers().containsKey(username) && lobby.getPlayers().size()==0);

        // Run the handle function a second time
        CuT.handle(request, response);
        // There should now be no change in the playerLobby now
        assertTrue(!lobby.getPlayers().containsKey(username) && lobby.getPlayers().size()==0);

        CuT.handle(request, response);


    }
}
