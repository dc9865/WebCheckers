package com.webcheckers.ui;

import com.webcheckers.model.*;
import com.webcheckers.util.Message;
import spark.*;

import com.webcheckers.appl.PlayerLobby;

import java.nio.file.StandardWatchEventKinds;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static com.webcheckers.ui.PostSigninRoute.USERNAME_PARAM;
import static spark.Spark.halt;

/**
 * The {@code GET /game} route handler.
 *
 */
public class GetGameRoute implements Route {

    // Values used in the view-model map for rendering the game view.
    static final String VIEW_MODE = "viewMode";
    static final String MODE_OPTIONS = "modeOptionsAsJSON";
    static final String RED_PLAYER = "redPlayer";
    static final String WHITE_PLAYER = "whitePlayer";
    static final String ACTIVE_COLOR = "activeColor";
    static final String CURRENT_USER="currentUser";
    static final String SPACE = "space";
    static final String PIECE = "piece";
    static final String ROW = "row";
    static final String BOARD="board";

    static final String GAME_TEMPLATE = "game.ftl";

    private static final Logger LOG = Logger.getLogger(GetGameRoute.class.getName());

    private Piece piece = new Piece(Piece.Type.SINGLE, Piece.Color.RED);
    private PlayerLobby playerLobby;
    private BoardView board;

    private final TemplateEngine templateEngine;

    /**
     * The constructor for the {@code GET /game} route handler.
     *
     * @param templateEngine
     *    the HTML template rendering engine
     */
    GetGameRoute(final TemplateEngine templateEngine, PlayerLobby  playerLobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.playerLobby = playerLobby;
        this.templateEngine = templateEngine;
        this.board = new CheckerBoard().getBoardView();
    }

    /**
     * Handle a request for a game
     *
     * @param request the HTTP request; request for user, game ID
     * @param response the HTTP response
     * @return rendered game
     */
    @Override
    public Object handle(Request request, Response response) {

        LOG.finer("GetGameRoute is invoked.");
        final Session httpSession = request.session();
        final Player redPlayer = (Player) httpSession.attribute(RED_PLAYER);
        final Player currentPlayer = (Player) httpSession.attribute(CURRENT_USER);
        final Player whitePlayer = (Player) httpSession.attribute(WHITE_PLAYER);
        if (httpSession.attribute(CURRENT_USER).equals(whitePlayer)){
            board.flipOrientation();
        }
        // TODO
        final Map<String, Object> modeOptionsAsJSON = new HashMap(2);
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game page");
        vm.put("message", Message.info("Hi"));

        if (currentPlayer.inGame() == true)
        {

        }
        vm.put(VIEW_MODE, "PLAY");
        vm.put(CURRENT_USER, currentPlayer);
        vm.put(RED_PLAYER, redPlayer);
        vm.put(WHITE_PLAYER, whitePlayer);
        vm.put(ACTIVE_COLOR, piece.getColor());
        vm.put(BOARD, board.iterator());
        return templateEngine.render(new ModelAndView(vm, GAME_TEMPLATE));
    }
}
