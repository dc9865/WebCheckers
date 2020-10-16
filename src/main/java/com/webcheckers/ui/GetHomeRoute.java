package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  static final String VIEW_NAME="home.ftl";
  static final String CURRENT_USER="currentUser";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  // Key in the session attribute map for the player who started the session
  static final String PLAYERLOBBY_KEY = "playerLobby";

  static final String ERROR="message";

  static final String VM_TITLE_KEY = "game";

  static final String SESSION_NAME_ATTRIBUTE = "";
  private final TemplateEngine templateEngine;

  private final PlayerLobby playerLobby;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   * @param playerLobby
   *   the PlayerLobby object that is in charge of manipulating the various Player Objects
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerLobby) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
   *
   * @param request
   *   the HTTP request
   *
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    final Session httpSession = request.session();
    LOG.finer("GetHomeRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    // display a user message in the Home page
    if(request.session().attribute(ERROR)==null){
      vm.put("message", WELCOME_MSG);
    }
    // display an error
    else{
      vm.put(ERROR, (Message)request.session().attribute(ERROR));
      request.session().removeAttribute(ERROR);
    }

    // If the player is signed in
    if(httpSession.attribute(CURRENT_USER) != null){
      vm.put(CURRENT_USER, (httpSession.attribute(CURRENT_USER)));
      vm.put(CURRENT_USER+".name", ((Player)(httpSession.attribute(CURRENT_USER))).getName());
    }

    if(httpSession.attribute(GetGameRoute.WHITE_PLAYER)!=null){
      response.redirect(WebServer.GAME_URL);
    }

    // Adds the list of users to the home page
    vm.put(PLAYERLOBBY_KEY, playerLobby.getPlayers());

    // render the View
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}
