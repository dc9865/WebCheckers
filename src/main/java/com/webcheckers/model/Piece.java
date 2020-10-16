package com.webcheckers.model;

import java.util.Objects;

/**
 * The model for the checkers piece
 */
public class Piece {

    /** an enum for if the piece is normal or king */
    public enum Type {
        SINGLE, KING
    }
    /** an enum for what color piece is on the spot */
    public enum Color {
        RED, WHITE
    }
    /** the type of piece*/
    private Type type;
    /** the color of the piece */
    private final Color color;
    private int row;
    private int column;

    public Piece(Type type, Color color) {
        this.type = type;
        this.color = color;
    }

    public int getRow(){
        return this.row;
    }

    public void setRow(int r) {this.row = r;}

    public int getColumn(){
        return this.column;
    }

    public void setColumn(int c) {this.column = c;}
    public Type getType() {return this.type;}

    public Color getColor() {return this.color;}

    public void kingMe() {
        this.type = Type.KING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type &&
                color == piece.color;
    }

}
