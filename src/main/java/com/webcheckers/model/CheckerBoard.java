package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The model for the checkerboard
 */
public class CheckerBoard {
    /** the number of rows */
    public final static int ROWS = 8;
    /** the number of columns */
    public final static int COLS = 8;
    /** a boardView copy */
    private BoardView boardView = new BoardView();
    private Player     redPlayer;
    private Player     whitePLayer;
    private ArrayList<Piece> AllPieces = new ArrayList<>();
    private ArrayList<Piece>  redPlayerPiece;
    private ArrayList<Piece>  whitePlayerPiece;
    /**
     * Used to indicate what color the spot is
     */
    public enum Spot{WHITE,BLACK}

    public CheckerBoard(){
        initializeBoard();
    }
    public BoardView getBoardView(){
        return boardView;
    }
    /**
     * Initializes the board with black and white spaces
     */
    public void initializeBoard(){
        Row row1=new Row(0);
        Row row2=new Row(1);
        Row row3=new Row(2);
        Row row4=new Row(3);
        Row row5=new Row(4);
        Row row6=new Row(5);
        Row row7=new Row(6);
        Row row8=new Row(7);
        for (int i=0;i<COLS;i++){
            if (i%2==0){
                row1.addSpace(new Space(i, Spot.WHITE, 0));
                row2.addSpace(new Space(i, Spot.BLACK, 1));
                row3.addSpace(new Space(i, Spot.WHITE, 2));
                row4.addSpace(new Space(i, Spot.BLACK, 3));
                row5.addSpace(new Space(i, Spot.WHITE, 4));
                row6.addSpace(new Space(i, Spot.BLACK, 5));
                row7.addSpace(new Space(i, Spot.WHITE, 6));
                row8.addSpace(new Space(i, Spot.BLACK, 7));
            }
            else{
                row1.addSpace(new Space(i, Spot.BLACK, 0));
                row2.addSpace(new Space(i, Spot.WHITE, 1));
                row3.addSpace(new Space(i, Spot.BLACK, 2));
                row4.addSpace(new Space(i, Spot.WHITE, 3));
                row5.addSpace(new Space(i, Spot.BLACK, 4));
                row6.addSpace(new Space(i, Spot.WHITE, 5));
                row7.addSpace(new Space(i, Spot.BLACK, 6));
                row8.addSpace(new Space(i, Spot.WHITE, 7));
            }
        }
        boardView.addRow(row1);
        boardView.addRow(row2);
        boardView.addRow(row3);
        boardView.addRow(row4);
        boardView.addRow(row5);
        boardView.addRow(row6);
        boardView.addRow(row7);
        boardView.addRow(row8);
    }

    /**
     * Initialize all the pieces for redPlayer
     * The boardview is initialize from down to top which means index 0 is the bottom row
     */
    public ArrayList<Piece> makeRedPlayerPiece(){
        ArrayList<Piece> redPlayerPiece  = new ArrayList<>();
        ArrayList<Row> RowIterator = (ArrayList<Row>) boardView.iterator();
        for(int row = 0; row < 3; row++){
            List<Space> AllSpace = (List<Space>) RowIterator.get(row).iterator();
            for(int col =0; col < 8; col ++){
                if(AllSpace.get(col).isValid()){
                    //true is red false is white
                    //
                    Piece newPiece = new Piece(row,col,true, redPlayer);
                    boardView.iterator().get(row).iterator().get(col).addPiece(newPiece);
                    redPlayerPiece.add(newPiece);
                }
            }
        }
        redPlayer.setPiece(redPlayerPiece);
        return redPlayerPiece;
    }

    /**
     * Initialize all the pieces for whitePlayer
     */
    public ArrayList<Piece> makeWhitePlayerPiece(){
        ArrayList<Piece> whitePlayerPiece = new ArrayList<>();
        ArrayList<Row> RowIterator = boardView.iterator();
        for(int row=5; row < 8; row++){
            List<Space> AllSpace = RowIterator.get(row).iterator();
            for(int col = 0; col<8;col++){
                if(AllSpace.get(col).isValid()){
                    Piece newPiece = new Piece(row,col,false, whitePLayer);
                    boardView.iterator().get(row).iterator().get(col).addPiece(newPiece);
                    whitePlayerPiece.add(newPiece);
                }
            }

        }
        whitePLayer.setPiece(whitePlayerPiece);
        return whitePlayerPiece;
    }




    public boolean inBoard(Position position){
        return position.getRow()>=0 && position.getRow()<ROWS && position.getCell()>=0 && position.getCell()<COLS;
    }


    public void addPiece(int row, int col, Piece piece){
        boardView.addPiece(row, col, piece);
    }

    public boolean hasPieceAt(Position position){
        return boardView.hasPieceAt(position);
    }

    public Piece.Type getTypeAt(Position position){
        return boardView.getTypeAt(position);
    }

    public Piece.Color getColorAt(Position position){
        return boardView.getColorAt(position);
    }
}


