package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;

public class PostGameRoute implements Route {

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final HashMap<String, Session> sessions;

    /**
     * Create the Spark Route (UI controller) to handle all {@code POST /postgame} HTTP requests.
     * @param templateEngine the HTML template rendering engine
     * @param lobby a PlayerLobby containing the Players that are signed in
     * @param sessions a hashmap that allows for the addition of attributes to other threads
     */
    public PostGameRoute(TemplateEngine templateEngine, PlayerLobby lobby,
                         HashMap<String, Session> sessions){
        this.templateEngine=templateEngine;
        this.lobby=lobby;
        this.sessions=sessions;
    }

    /**
     * Handles a request for a Game being initiated
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML code for the game page
     */
    @Override
    public Object handle(Request request, Response response){

        // The opponent that was clicked on
        Player opponent=lobby.getPlayer(request.queryParams("key"));

        // If the person you are trying to play with is not in game
        if(opponent.isAvailable()){

            // Adds the current player as the red side to the attributes
            request.session().attribute(GetGameRoute.RED_PLAYER,
                    (Player)request.session().attribute(GetHomeRoute.CURRENT_USER));
            sessions.get(opponent.getName()).attribute(GetGameRoute.RED_PLAYER,
                    (Player)request.session().attribute(GetHomeRoute.CURRENT_USER));

            // Adds the white side to the attributes
            request.session().attribute(GetGameRoute.WHITE_PLAYER, opponent);
            sessions.get(opponent.getName()).attribute(GetGameRoute.WHITE_PLAYER, opponent);

            // Change the states to IN_GAME
            opponent.startGame();
            ((Player)request.session().attribute(GetHomeRoute.CURRENT_USER)).startGame();

            // Redirects the player
            response.redirect(WebServer.GAME_URL);
        }

        // Redirects back to the home page with an error
        else{

            request.session().attribute(GetHomeRoute.ERROR, Message.error("That person is currently in game. Try another person."));
            response.redirect(WebServer.HOME_URL);
        }
        return null;
    }
}
