package com.webcheckers.model;

import java.util.ArrayList;

public class Jump extends Move{


    private ArrayList<Position> jumpedPositions;

    /**
     * Constructor for a jump move.
     *
     * @param startPosition the start position
     * @param endPosition the end position
     * @param jumpedPositions the positions that were jumped
     */
    public Jump(Position startPosition, Position endPosition, ArrayList<Position> jumpedPositions){
        super(startPosition, endPosition);
        this.jumpedPositions=jumpedPositions;
    }

    /**
     * Returns whether or not the move is a jump.
     * Jumps are characterized by a change in row or col of greater than 1
     * Jumps have a higher priority than regular moves
     *
     * @return whether or not the move is a jump
     */
    public boolean isJump(){
        return true;
    }

    /**
     * Returns the positions that were jumped
     * @return the positions that were jumped
     */
    public ArrayList<Position> getJumpedPositions() {
        return jumpedPositions;
    }

    public boolean containsJump(Position jumped){
        if(jumpedPositions.contains(jumped)){
            return true;
        }
        return false;
    }

    public boolean equals(Object obj){
        if(obj instanceof Jump) {
            Jump jumpCmp=(Jump)(obj);
            for (Position jumped : jumpedPositions) {
                if(!jumpCmp.containsJump(jumped)){
                   return false;
                }
            }
        }

        return super.equals(obj);
    }

    public void SetJumpMoves(Piece pl){
        /**If the piece is a king
         *
         */
        if (pl.getType().equals(Piece.Type.KING)){
            ArrayList<Piece> ForwardJP = FindForwardJump(pl,this.BV,pl.getPlayer());
            ArrayList<Piece> BackJP  = FindBackJump(pl,this.BV,pl.getPlayer());
            ForwardJP.addAll(BackJP);
            pl.setJumpMove(ForwardJP);
        }

        /**
         * if players is red**/
        else if(pl.getColor().equals(Piece.color.RED)){
            //System.out.println("Red");
            ArrayList<Piece> ForwardJP = FindForwardJump(pl,this.BV,pl.getPlayer());
            pl.setJumpMove(ForwardJP);
        }

        /**
         * Last case if player is white
         */
        else {
            ArrayList<Piece> BackJP = FindBackJump(pl,this.BV,pl.getPlayer());
            for(Piece K: BackJP){

            }
            pl.setJumpMove(BackJP);
        }
    }

    public Piece JumpTempMoves(Position Pos, Piece Pl){
        //this.DeleteHighLight();
        Pl.setRow(Pos.row);
        Pl.setColumn(Pos.cell);
        this.SetJumpMoves(Pl);
        Pl.simpleMove(this.BV);
        return Pl;
    }

    @Override
    public String toString() {
        return "StartPosition="+startPosition+"; EndPosition="+endPosition+"; Jumps="+jumpedPositions;
    }
}
