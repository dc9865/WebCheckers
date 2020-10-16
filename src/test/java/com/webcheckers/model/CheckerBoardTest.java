package com.webcheckers.model;

import static com.webcheckers.model.CheckerBoard.Spot.BLACK;
import static com.webcheckers.model.CheckerBoard.Spot.WHITE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class CheckerBoardTest {

    public final static int MAX_ROWS = 8;

    //Friendly object
    private BoardView boardView;

    //Component under test
    private CheckerBoard CuT;

    @BeforeEach
    public void setup(){
        CuT = new CheckerBoard();
        boardView = CuT.getBoardView();
    }

    @Test
    public void testCheckerBoardConstructor(){
        assertNotNull(CuT);
    }

    @Test
    public void testTopLeftSpaceWhite(){
        Space space = boardView.getBoard().get(0).getSpace(0);
        Space testSpace = new Space(0, WHITE, 0);
        assertEquals(space, testSpace);
    }

    @Test
    public void testTopLeftSpaceEmpty(){
        Space space = boardView.getBoard().get(0).getSpace(0);
        Piece piece = space.getPiece();
        assertNull(piece);
    }

    @Test
    public void testTopRightSpaceBlack(){
        Space space = boardView.getBoard().get(0).getSpace(7);
        Space testSpace = new Space(7, BLACK, 0);
        assertEquals(space, testSpace);
    }

    @Test
    public void testTopRightSpaceNotEmpty(){
        Space firstSpace = boardView.getBoard().get(0).getSpace(7);
        Piece piece = firstSpace.getPiece();
        assertNotNull(piece);
    }

    //lsajmdflksajmdksamd

    @Test
    public void testBottomLeftSpaceBlack(){
        Space space = boardView.getBoard().get(7).getSpace(0);
        Space testSpace = new Space(0, BLACK, 7);
        assertEquals(space, testSpace);
    }

    @Test
    public void testBottomLeftSpaceNotEmpty(){
        Space space = boardView.getBoard().get(7).getSpace(0);
        Piece piece = space.getPiece();
        assertNotNull(piece);
    }

    @Test
    public void testBottomRightSpaceWhite(){
        Space space = boardView.getBoard().get(7).getSpace(7);
        Space testSpace = new Space(7, WHITE, 7);
        assertEquals(space, testSpace);
    }

    @Test
    public void testBottomRightSpaceEmpty(){
        Space firstSpace = boardView.getBoard().get(7).getSpace(7);
        Piece piece = firstSpace.getPiece();
        assertNull(piece);
    }
}
