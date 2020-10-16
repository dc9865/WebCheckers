package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostValidateMoveRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final Gson gson;
    private final PlayerLobby playerLobby;

    public PostValidateMoveRoute(TemplateEngine templateEngine, PlayerLobby playerLobby, Gson gson) {
        //validation
        Objects.requireNonNull(templateEngine, "template engine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
        this.gson = gson;

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        System.out.println("PostValidateMove");
        LOG.finer("PostValidateMoveRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        //TODO
        // store the proposed move somewhere
        // check if valid -> check the current board
        // if valid -> Message.info
        // if invalid -> error with reason why.

        Player temp = request.session().attribute("currentPlayer");

        vm.put("redPlayer", request.session().attribute("redPlayer"));
        vm.put("whitePlayer", request.session().attribute("whitePlayer"));
        vm.put("viewMode", "PLAY");

        vm.put("board", temp.getBoard());
        vm.put("users", request.session().attribute("users"));

        String JsonBody = request.body();
        Move move = gson.fromJson(JsonBody, Move.class);
        ValidateMove vMove = gson.fromJson(JsonBody, ValidateMove.class);
        Position pos = gson.fromJson(JsonBody, Position.class);

        if (vMove.validate(move, (CheckerBoard) request.session().attribute("currentPlayer"), piece.getColor())) {

            temp.getBoard().initializeBoard();
            Piece piece = null;
            for(Piece c : temp.getMyPiece())
            {
                if(c.getRow() == move.getStart().getRow() && c.getColumn() == move.getStart().getCell())
                {
                    piece = c;
                    piece = temp.getBoard().JumpTempMoves(m.getEnd(), theOne);
                    c.overRide(theOne);
                    c.addPastLocation(m.getStart());

                    if(!(Math.abs(m.getStart().getRow()- m.getEnd().getRow()) == 1))
                    {
                        c.addPastLocation(new Position((m.getStart().getRow() + m.getEnd().getRow())/2, (m.getStart().getCell() + m.getEnd().getCell())/2));
                        c.setMadeAJump(true);
                    }
                    else
                    {
                        c.setMadeAMove(true);
                        c.setMadeAJump(true);
                    }

                    temp.setCurrentPiece(c);
                }
            }


        return null;
    }
}
