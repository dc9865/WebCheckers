package com.webcheckers.appl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerLobbyTest {
    private PlayerLobby CuT;
    private String username = "Player1";
    private String badUsername = "@Player1";

    @BeforeEach
    public void setup(){
        /* create component under test */
        CuT = new PlayerLobby();
    }

    @Test
    public void testNormalSignIn(){
        CuT.signIn(username);
        assertTrue(CuT.getPlayers().containsKey(username), username+ "couldn't sign in normally.");
    }

    @Test
    public void testBadSignIn(){
        CuT.signIn(badUsername);
        assertTrue(!CuT.getPlayers().containsKey(badUsername), username+"signed in with a bad username.");
    }
    @Test
    public void testSignOut(){
        /* sign in first to validate sign out */
        CuT.signIn(username);
        CuT.signOut(username);
        assertTrue(!CuT.getPlayers().containsKey(username), username+"couldn't sign out normally.");
    }

    @Test
    public void testIsValidUsernameNormal(){
        assertTrue(PlayerLobby.isValidUsername(username), "a normal username was invalid.");
    }

    @Test
    public void testIsValidUsernameBad(){
        assertTrue(!PlayerLobby.isValidUsername(badUsername), "a bad username was valid.");
    }

    @Test
    public void testCanPlay(){
        /* a newly signed in player is always available to play */
        CuT.signIn(username);
        assertTrue(CuT.canPlay(username), "a new player wasn't considered available.");
    }
}
