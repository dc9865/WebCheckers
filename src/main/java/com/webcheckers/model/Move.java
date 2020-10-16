package com.webcheckers.model;


import java.util.ArrayList;

/** Class that represents a move */
public abstract class Move {

    protected Position startPosition;
    protected Position endPosition;

    public Move(Position startPosition, Position endPosition){
        this.startPosition=startPosition;
        this.endPosition=endPosition;
    }


    /**
     * Returns whether or not this is a jump.
     * Jumps are characterized by not changing by exactly one row and one cell.
     * Jumps have a higher priority than other moves.
     *
     * @return whether or not this is a jump
     */
    public abstract boolean isJump();

    public Position getStart() {
        return startPosition;
    }

    public Position getEnd(){
        return endPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Move){
            Move compMove=(Move)(obj);
            return compMove.getStart().equals(this.getStart()) && compMove.getEnd().equals(this.getEnd());
        }
        return false;
    }
}
