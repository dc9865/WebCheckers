package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents rows for checkers games.
 */
public class Row implements Iterable<Space> {
    private int index;
    /** the list of spaces in the row */
    private List<Space> row=new ArrayList<>();

    public Row(int index){
        this.index=index;
    }

    public int getIndex() {
        return this.index;
    }
    public Space getSpace(int index){
        return row.get(index);
    }
    /** returns an iterator for use in the html file*/

    /*
    public Iterator<Space> iterator(){
        return row.iterator();
    }
    */
    public Iterator<Space> iterator(){
        return (Iterator<Space>)this.row;
    }

    public void addSpace(Space space){
        row.add(space);
    }
    /** reverses the order of the spaces only if the player is white */

    public void addPiece(int col, Piece piece){
        row.get(col).addPiece(piece);
    }

    public Piece.Type getTypeAt(Position position){
        return row.get(position.getCell()).getTypeAt();
    }
    public Piece.Color getColorAt(Position position){
        return row.get(position.getCell()).getColorAt();
    }

    public void reverseOrder(){
        Collections.reverse(row);
    }
}
