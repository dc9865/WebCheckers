package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import java.awt.*;

public class PostResignRoute implements Route {
    PostResignRoute(final TemplateEngine templateEngine) {

    }
    @Override
    public Object handle(Request request, Response response) {
        final Gson gson = new Gson();
        final String gameOverJSON = request.body();
        Container gameOver = gson.fromJson(gameOverJSON, Container.class);
        //TODO
        /*if() {
            response.body(gson.toJson(Message.error("Resignation unsuccessful.")));
        }
        else{
            response.body(gson.toJson(Message.info("Resignation successful.")));
        }*/
        return null;
    }
}
