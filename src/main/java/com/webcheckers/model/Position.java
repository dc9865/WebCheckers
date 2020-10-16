package com.webcheckers.model;
/** Class that represents a position */
public class Position {
    int row;
    int cell;

    /**
     * Constructor for a position on the game board
     *
     * @param row the row on the game board
     * @param cell the col on the game board
     */
    public Position(int row, int cell){
        this.row=row;
        this.cell=cell;
    }

    public int getRow(){
        return row;
    }
    public int getCell(){
        return cell;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Position){
            Position posCmp=(Position)(obj);
            return posCmp.getRow()==this.getRow() && posCmp.getCell()==this.getCell();
        }
        return false;
    }

    public String toString(){
        return this.row+","+this.cell;
    }
}
