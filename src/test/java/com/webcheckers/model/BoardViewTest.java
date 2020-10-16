package com.webcheckers.model;

import static com.webcheckers.model.Piece.Type.SINGLE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class BoardViewTest {

    public final static int MAX_ROWS = 8;
    public final static int NO_ROWS = 0;

    //friendly object
    private CheckerBoard checkerBoard = new CheckerBoard();

    //Component under test
    private BoardView CuT;

    @BeforeEach
    public void setup(){
        CuT=checkerBoard.getBoardView();
    }

    @Test
    public void testRowCountAllRows(){
        int rowCount = CuT.getBoard().size();
        assertEquals(MAX_ROWS, rowCount);
    }

    @Test
    public void testIterator(){
        assertNotNull(CuT.iterator());
    }

    @Test
    public void testRowCountNoRows(){
        CuT.getBoard().clear();
        int rowCount=CuT.getBoard().size();
        assertEquals(NO_ROWS, rowCount);
    }

    @Test
    public void testOrientationNotFlipped(){
        Row firstRow = CuT.getBoard().get(7);
        Space firstSpace = firstRow.getSpace(0);
        Piece firstPiece = firstSpace.getPiece();
        Piece testPiece = new Piece(SINGLE, Piece.Color.RED);
        assertEquals(firstPiece, testPiece);
    }

    @Test
    public void testOrientationFlipped(){
        CuT.flipOrientation();
        Row firstRow = CuT.getBoard().get(7);
        Space firstSpace = firstRow.getSpace(0);
        Piece firstPiece = firstSpace.getPiece();
        Piece testPiece = new Piece(SINGLE, Piece.Color.WHITE);
        assertEquals(firstPiece, testPiece);
    }

}
