package com.webcheckers.ui;

import spark.*;

import java.util.HashMap;
import java.util.Map;

public class GetSigninRoute implements Route {

    static final String TITLE="title";

    static final String VIEW_NAME="signin.ftl";

    private TemplateEngine templateEngine;

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /getsignin} HTTP requests.
     * @param templateEngine an HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine){
        this.templateEngine=templateEngine;
    }


    /**
     * Handles a request for a sign in
     * @param request the HTTP request
     * @param response the HTTP response
     * @return the rendered HTML code for the sign in page
     */
    @Override
    public Object handle(Request request, Response response){
        // Build the View-Model
        final Map<String, Object> vm = new HashMap<>();

        // Displays an error if one exists
        if(request.session().attribute(GetHomeRoute.ERROR) != null){
            vm.put(GetHomeRoute.ERROR, request.session().attribute(GetHomeRoute.ERROR));
            request.session().removeAttribute(GetHomeRoute.ERROR);
        }

        vm.put(TITLE, "Sign in page");

        // Render the view model
        return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
    }
}
