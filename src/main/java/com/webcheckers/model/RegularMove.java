package com.webcheckers.model;

public class RegularMove extends Move{
    public RegularMove(Position startPosition, Position endPosition){
        super(startPosition, endPosition);
    }
    @Override
    public boolean isJump() {
        return false;
    }
}
