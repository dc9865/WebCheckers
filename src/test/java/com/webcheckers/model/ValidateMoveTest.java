package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class ValidateMoveTest {

    // The component under test: ValidateMove

    // Friendly objects
    CheckerBoard board;


    /**
     * Creates the general setup for the checkerboard and deletes the
     * pieces in each space (rough game).
     */
    @BeforeEach
    public void setup(){
        // Creates a board
        board=new CheckerBoard();

        // Loops through the spaces and removes all pieces
        for (Row r:board.getBoardView().getBoard()){
            for(Space s:r){
                s.removePiece();
            }
        }
    }

    @Test
    public void NoMoveTest(){
        // Adds pieces at 0, 0; 1, 1; and 1, 3
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));
        board.addPiece(1, 3, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        // Gets all regular moves
        ArrayList<Move> moves=ValidateMove.getRegularMoves(board, Piece.Color.RED);

        assertTrue(moves.size()==0);
    }

    @Test
    public void OneMoveTest(){
        // Adds pieces at 0, 0 and 1, 1
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        // Gets all regular moves
        ArrayList<Move> moves=ValidateMove.getRegularMoves(board, Piece.Color.RED);

        assertTrue(moves.size()==1 && moves.contains(new RegularMove(new Position(0, 2),
                new Position(1, 3))));
    }

    @Test
    public void TwoMoveTest(){
        // Adds a piece at 1, 1
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        // Gets all regular moves
        ArrayList<Move> moves=ValidateMove.getRegularMoves(board, Piece.Color.WHITE);

        assertTrue(moves.size()==2 &&
                moves.contains(new RegularMove(new Position(1,1 ), new Position(0, 0))) &&
                moves.contains(new RegularMove(new Position(1, 1), new Position(0,2))));
    }

    @Test
    public void KingMoveTest(){
        board.addPiece(1, 1, new Piece(Piece.Type.KING,
                Piece.Color.WHITE));


        ArrayList<Move> moves=ValidateMove.getRegularMoves(board, Piece.Color.WHITE);

        assertTrue(moves.size()==4 &&
                moves.contains(new RegularMove(new Position(1, 1), new Position(0, 0))) &&
                moves.contains(new RegularMove(new Position(1, 1), new Position(0, 2))) &&
                moves.contains(new RegularMove(new Position(1, 1), new Position(2, 0))) &&
                moves.contains(new RegularMove(new Position(1, 1), new Position(2, 2))));
    }

    @Test
    public void outOfBoundTest(){

        board.addPiece(0,0, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        ArrayList<Move> moves=ValidateMove.getRegularMoves(board, Piece.Color.WHITE);

        assertTrue(moves.size()==0);
    }

    @Test
    public void noJumpTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.RED);

        assertTrue(moves.size()==0);

    }

    @Test
    public void singleJumpTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.RED);

        ArrayList<Position> testMoves=new ArrayList<>();
        testMoves.add(new Position(1, 1));

        assertTrue(moves.size()==1 &&
                moves.contains(new Jump(new Position(0, 2),
                                        new Position(2, 0),
                                        testMoves)));
    }

    @Test
    public void twoJumpTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));
        board.addPiece(1, 3, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.RED);

        ArrayList<Position> testMoves1=new ArrayList<>();
        testMoves1.add(new Position(1, 1));
        ArrayList<Position> testMoves2=new ArrayList<>();
        testMoves2.add(new Position(1, 3));

        assertTrue(moves.size()==2 &&
                moves.contains(new Jump(new Position(0, 2),
                        new Position(2, 0),
                        testMoves1))&&
                moves.contains(new Jump(new Position(0, 2),
                        new Position(2, 4),
                        testMoves2)));
    }

    @Test
    public void doubleJumpTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));
        board.addPiece(3, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.RED);

        ArrayList<Position> testMoves1=new ArrayList<>();
        testMoves1.add(new Position(1, 1));
        ArrayList<Position> testMoves2=new ArrayList<>();
        testMoves1.add(new Position(1, 1));
        testMoves2.add(new Position(3, 1));

        assertTrue(moves.size()==2 &&
                moves.contains(new Jump(new Position(0, 2),
                        new Position(2, 0),
                        testMoves1))&&
                moves.contains(new Jump(new Position(0, 2),
                        new Position(4, 2),
                        testMoves2)));
    }

    @Test
    public void kingJumpTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.KING,
                Piece.Color.WHITE));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.WHITE);

        ArrayList<Position> testMoves=new ArrayList<>();
        testMoves.add(new Position(1, 1));

        assertTrue(moves.size()==1 &&
                moves.contains(new Jump(new Position(0, 2),
                        new Position(2, 0),
                        testMoves)));
    }


    @Test
    public void outOfBoundsTest(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE,
                Piece.Color.WHITE));

        ArrayList<Move> moves=ValidateMove.getJumps(board, Piece.Color.WHITE);

        ArrayList<Position> testMoves=new ArrayList<>();
        testMoves.add(new Position(1, 1));

        assertTrue(moves.size()==0);
    }

    @Test
    public void validMoveVerificationTest(){
        board.addPiece(0, 0, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));

        assertTrue(ValidateMove.validate(new RegularMove(new Position(0, 0),
                new Position(1, 1)), board, Piece.Color.RED));
        assertFalse(ValidateMove.validate(new RegularMove(new Position(0, 0),
                new Position(1, -1)), board, Piece.Color.RED));
    }

    @Test
    public void jumpMoveVerification(){
        board.addPiece(0, 2, new Piece(Piece.Type.SINGLE,
                Piece.Color.RED));
        board.addPiece(1, 1, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));

        assertFalse(ValidateMove.validate(new RegularMove(new Position(0, 2),
                new Position(1, 3)), board, Piece.Color.RED));

        ArrayList<Position> jumped=new ArrayList<>();
        jumped.add(new Position(1, 1));

        assertTrue(ValidateMove.validate(new Jump(new Position(0, 2),
                new Position(2, 0), jumped), board, Piece.Color.RED));
    }

}
