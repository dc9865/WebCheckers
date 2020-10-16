package com.webcheckers.model;

import java.util.Objects;

/**Class that represents a space*/
public class Space {
    /**The location in the row for the space*/
    private int cellIdx;
    /**what type of spot the space is*/
    private CheckerBoard.Spot spot;
    /** The piece in the spot*/
    private Piece piece;
    /** The number row that the space is in*/
    private int row;
    public Space(int id, CheckerBoard.Spot spot, int row){
        this.cellIdx=id;
        this.spot=spot;
        this.row=row;
        if (spot == CheckerBoard.Spot.BLACK){
            if (row>4){
                piece=new Piece(Piece.Type.SINGLE, Piece.Color.RED);
            }
            if (row<3){
                piece=new Piece(Piece.Type.SINGLE, Piece.Color.WHITE);
            }
        }

    }
    public int getCellIdx(){
        return cellIdx;
    }
    /** Checks if a cell is valid. Only valid if it's black and there's no piece on it */
    public boolean isValid(){
        return spot == CheckerBoard.Spot.BLACK && piece==null;
    }
    public Piece getPiece(){
        return piece;
    }
    public boolean hasPiece(Piece.Color color){
        if(hasPiece()) {
            return piece.getColor()==color;
        }
        return false;
    }
    public boolean hasPiece(){
        return piece!=null;
    }
    public boolean hasKing(){
        if(hasPiece()){
            return piece.getType() == Piece.Type.KING;
        }
        return false;
    }
    public void addPiece(Piece piece){
        this.piece=piece;
    }
    public void removePiece(){
        piece=null;
    }
    public Piece.Color getColorAt(){
        return piece.getColor();
    }

    public Piece.Type getTypeAt(){
        return piece.getType();
    }

    public CheckerBoard.Spot getSpot(){
        return spot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Space space = (Space) o;
        return cellIdx == space.cellIdx &&
                row == space.row &&
                spot == space.spot &&
                Objects.equals(piece, space.piece);
    }

}
