package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;

public class PostSigninRoute implements Route {

    static final String USERNAME_PARAM= "Username";
    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final HashMap<String, Session> sessions;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /postsignin} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     * @param lobby a PlayerLobby containing the Players that are signed in
     * @param sessions a hashmap that allows for the addition of attributes to other threads
     */
    public PostSigninRoute(final TemplateEngine templateEngine, final PlayerLobby lobby,
                           final HashMap<String, Session> sessions){
        this.templateEngine = templateEngine;
        this.lobby = lobby;
        this.sessions=sessions;
    }

    /**
     * Handles a request for a sign in
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML code for the signin page
     */
    @Override
    public Object handle(Request request, Response response){

        // Gets the username
        final String name = request.queryParams(USERNAME_PARAM);

        // attempt to sign in player
        Player player = lobby.signIn(name);

        // check if the sign in was successful
        if(player != null) {

            // Adds the current session to the sessions so that attributes can be added to the session
            sessions.put(name, request.session());

            // Adds the current player to the session
            request.session().attribute(GetHomeRoute.CURRENT_USER, player);

            response.redirect("./");

            return null;
        } else {

            //Generates the error to display as an attribute for the GetSigninRoute
            if(!PlayerLobby.isValidUsername(name)) {
                request.session().attribute(GetHomeRoute.ERROR, Message.error("Please only use alpha-numeric characters"));
            }
            else{
                request.session().attribute(GetHomeRoute.ERROR, Message.error("Already signed in"));
            }

            // Redirects back to the signin page
            response.redirect("./getsignin");

            // Reroutes back to the signin route with an error message
            return null;
        }
    }
}
