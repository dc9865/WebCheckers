package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** A class that represents the UI for the checkerboard */
public class BoardView implements Iterable<Row> {
    /** The list of rows in the baord */
    List<Row> board = new ArrayList<>();

    public BoardView(){
    }

    public List<Row> getBoard(){
        return board;
    }
    /** Creates an iterable object for the html file to use*/
    public Iterator<Row> iterator(){
        return board.iterator();
    }

    public void addRow(Row row){
        board.add(row);
    }
    /** Reverses the orientation of the board for the white player */

    public void flipOrientation(){
        for (Row row:board){
            row.reverseOrder();
        }
        Collections.reverse(board);
    }

    public void addPiece(int row, int col, Piece piece){
        board.get(row).addPiece(col, piece);
    }

    public boolean hasPieceAt(Position position){
        return board.get(position.getRow()).getSpace(position.getCell()).hasPiece();
    }
    public Piece.Color getColorAt(Position position){
        return board.get(position.getRow()).getColorAt(position);
    }
    public Piece.Type getTypeAt(Position position){
        return board.get(position.getRow()).getTypeAt(position);
    }
}
