package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;

public class PostSignoutRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final HashMap<String, Session> sessions;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /postsignout} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     * @param lobby a PlayerLobby containing the Players that are signed in
     * @param sessions a HashMap that allows for the addition of attributes to other threads
     */
    public PostSignoutRoute(TemplateEngine templateEngine, PlayerLobby lobby, HashMap<String,
            Session> sessions){
        this.templateEngine = templateEngine;
        this.playerLobby = lobby;
        this.sessions=sessions;
    }


    /**
     * Handles a request for a sign out
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML code for the sign out page
     */
    @Override
    public Object handle(Request request, Response response) {
        // Signs out the player
        playerLobby.signOut(((Player)request.session().attribute(GetHomeRoute.CURRENT_USER)).getName());

        // Removes the player's session
        sessions.remove(((Player)request.session().attribute(GetHomeRoute.CURRENT_USER)).getName());

        // Takes the player attribute out of the session attributes
        request.session().removeAttribute(GetHomeRoute.CURRENT_USER);

        // redirects back to the home page
        response.redirect("./");

        return null;
    }
}
