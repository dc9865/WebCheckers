package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player CuT;

    @BeforeEach
    public void setUp() {
        CuT = new Player("Player 1");
    }

    @Test
    public void testStartGame() {
        CuT.startGame();
        assertEquals(true, CuT.inGame());
    }

    @Test
    public void testSpectate() {
        CuT.spectate();
        assertEquals(false, CuT.isAvailable());
        assertEquals(false, CuT.inGame());
    }

    @Test
    public void testLeaveGame() {
        CuT.leaveGame();
        assertEquals(true, CuT.isAvailable());
    }
}