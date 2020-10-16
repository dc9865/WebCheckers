package com.webcheckers.model;

import com.webcheckers.model.Move;

import java.util.ArrayList;
import java.util.Collections;

public class ValidateMove {

    /*
     * The moves
     */

    public static ArrayList<Move> getRegularMoves(CheckerBoard board, Piece.Color color){
        ArrayList<Move> moves=new ArrayList<Move>();

        // Loops through each space
        for(int r=0; r<CheckerBoard.ROWS; r++){
            for(int c=0; c<CheckerBoard.COLS; c++){

                Space s=board.getBoardView().getBoard().get(r).getSpace(c);

                Position position=new Position(r, c);

                // If there is a piece in the space
                if(board.hasPieceAt(position) && board.getColorAt(position)==color){

                    // If it is a king
                    if(board.getTypeAt(position)== Piece.Type.KING){
                        getKingMovesForPosition(new Position(r, c), moves, board, color);
                    }

                    // If it is a regular piece
                    else{
                        getRegularMovesForPosition(new Position(r, c), moves, board, color);
                    }
                }
            }
        }
        return moves;
    }

    private static void getRegularMovesForPosition(Position startPosition, ArrayList<Move> moves, CheckerBoard board, Piece.Color color){

        // The rowChange for a move
        int rowChange=1;
        if(color == Piece.Color.WHITE){
            rowChange=-1;
        }

        Position endPosition=new Position(startPosition.getRow()+rowChange, startPosition.getCell()+1);

        if(board.inBoard(endPosition)){
            if(!board.hasPieceAt(endPosition)){
                moves.add(new RegularMove(startPosition, endPosition));
            }
        }

        endPosition=new Position(startPosition.getRow()+rowChange, startPosition.getCell()-1);

        if(board.inBoard(endPosition)){
            if(!board.hasPieceAt(endPosition)){
                moves.add(new RegularMove(startPosition, endPosition));
            }
        }
    }

    private static void getKingMovesForPosition(Position position, ArrayList<Move> moves, CheckerBoard board, Piece.Color color){
        // Gets the regular moves
        getRegularMovesForPosition(position, moves, board, color);

        //Gets the extended move set
        if(color== Piece.Color.RED){
            getRegularMovesForPosition(position, moves, board, Piece.Color.WHITE);
        }
        else{
            getRegularMovesForPosition(position, moves, board, Piece.Color.RED);
        }
    }

    /*
     * Gets the jumps
     */

    private static void recursiveJumpsHandeler(Position jumpedPosition, Position endPosition,
                                               ArrayList<Move> moves, Position startPosition,
                                               ArrayList<Position> jumps, CheckerBoard board, Piece.Color color,
                                               Piece.Type type){



        // If it is within the board range
        if(board.inBoard(jumpedPosition) && board.inBoard(endPosition)) {

            // If there is a piece to jump
            if (board.hasPieceAt(jumpedPosition)) {

                // If there is another jump possible
                if (board.getColorAt(jumpedPosition) != color && !jumps.contains(jumpedPosition)) {
                    ArrayList<Position> currentJumps= new ArrayList();

                    // currentJumps has to be at least as long as jumps to copy
                    for(int jump=0; jump<jumps.size(); jump++){
                        currentJumps.add(null);
                    }

                    // Copies the jump into a new ArrayList for recursive calling
                    Collections.copy(currentJumps, jumps);

                    // Adds in the possible jump
                    currentJumps.add(jumpedPosition);

                    // Recursive helper
                    getJumpsHelper(moves, startPosition, endPosition, currentJumps, board, color, type);
                }
            }
        }
    }

    private static void getJumpsHelper(ArrayList<Move> moves, Position startPosition, Position currentPosition,
                                       ArrayList<Position> jumps, CheckerBoard board, Piece.Color color,
                                       Piece.Type type){
        // Gets the possible row change
        int rowChange=1;
        if(color== Piece.Color.WHITE){
            rowChange=-1;
        }

        // Adds the current jump into the moves list
        if(jumps.size()>0){
            moves.add(new Jump(startPosition, currentPosition, jumps));
        }

        //If the piece is a king, loop through it again but with the opposite rowChange
        for(int king = 0; king<1+((type== Piece.Type.KING)? 1:0); king++){

            // Loops through the regular moves
            for(int regular=0; regular<2; regular++){
                Position jumpedPosition=new Position(currentPosition.getRow()+rowChange,
                        currentPosition.getCell()+(int)(Math.pow(-1, regular)));

                Position endPosition=new Position(currentPosition.getRow()+rowChange*2,
                        currentPosition.getCell()+(int)(Math.pow(-1, regular))*2);

                recursiveJumpsHandeler(jumpedPosition, endPosition, moves,
                        startPosition, jumps, board, color, type);
            }
            rowChange*=-1;
        }
    }

    public static ArrayList<Move> getJumps(CheckerBoard board, Piece.Color color){
        ArrayList<Move> jumps=new ArrayList<Move>();

        // Loops through the spaces
        for(int r=0; r<CheckerBoard.ROWS; r++){
            for(int c=0; c<CheckerBoard.COLS; c++){

                Position position=new Position(r, c);

                // If there is a piece
                if(board.hasPieceAt(position)){

                    // Get the jumps for the king
                    getJumpsHelper(jumps, position, position, new ArrayList<Position>(),
                            board, color, board.getTypeAt(position));
                }
            }
        }
        return jumps;
    }

    public static boolean validate(Move move, CheckerBoard board, Piece.Color color){
        // Gets the moves
        ArrayList<Move> moves=getRegularMoves(board, color);
        moves.addAll(getJumps(board, color));

        if(!move.isJump()){
            for(Move m:moves){
                if(m.isJump()){
                    return false;
                }
            }
        }
        return moves.contains(move);
    }
}
