package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI=TIER")
public class GetHomeRouteTest {

    private GetGameRoute CuT;

    private TemplateEngine template;
    private PlayerLobby playerLobby;

    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    void testSetup() {

        template = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);

        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);

        when(request.session()).thenReturn(session);
        CuT = new GetGameRoute(template, playerLobby);
    }

    @Test
    public void testInitialRender() {
        final TemplateEngineTester tester = new TemplateEngineTester();
        when(template.render(any(ModelAndView.class))).thenAnswer(tester.makeAnswer());

        CuT.handle(request, response);

        tester.assertViewModelExists();
    }
}
