package com.webcheckers.model;

import java.util.ArrayList;

public class Player {

    // The possible states that a Player could be in at any point
    private enum playerState{AVAILABLE, IN_GAME, SPECTATING}

    // The username of the Player
    private String name;
    // The current state that the Player is in
    private playerState state;
    public CheckerBoard checkerBoard;
    public boolean isMyTurn;
    public ArrayList<Piece> myPiece = new ArrayList<>();
    public Piece currentPiece = null;

    /**
     * Constructs a new Player object
     * @param name the name of the player
     */
    public Player (String name){
        this.name = name;
        this.state=playerState.AVAILABLE;
    }

    /**
     * Sets the state to IN_GAME after joining a game
     */
    public void startGame(){
        state=playerState.IN_GAME;
    }

    public boolean getMyTurn()
    {
        return isMyTurn;
    }

    public void setTurn(boolean a)
    {
        isMyTurn = a;
    }

    public CheckerBoard getBoard() {
        return checkerBoard;
    }

    public void setCurrentPiece(Piece c)
    {
        currentPiece = c;
    }

    public ArrayList<Piece> getMyPiece() {return this.myPiece;}
    public Piece getCurrentPiece()
    {
        return currentPiece;
    }

    /**
     * Sets the state to SPECTATING after starting to spectate
     */
    public void spectate(){
        state=playerState.SPECTATING;
    }

    /**
     * Sets the state to AVAILABLE after leaving a game
     */
    public void leaveGame(){
        state=playerState.AVAILABLE;
    }

    /**
     * Returns whether or not the Player is available to play a game
     * @return
     */
    public boolean isAvailable(){
        return state==playerState.AVAILABLE;
    }

    /**
     * Checks whether or not the Player is in-game
     * @return whether or not the Player is in-game
     */
    public boolean inGame(){
        return state==playerState.IN_GAME;
    }

    /**
     * Gets an error message when rejected from joining a game with this person
     * @return the error message
     */
    public String getError(){
        if(state==playerState.IN_GAME){
            return "This player is already in a game.";
        }
        else{
            return "This player is already spectating another game.";
        }
    }

    /**
     * Gets the name of the Player
     * @return the name of the Player
     */
    public String getName(){
        return name;
    }

    /**
     * Checks to see if an object is equal to the player
     * @param obj The object to see if this is equal to
     * @return whether or not the two Players are equal (if they have the same name)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player){
            if(this.name.equals(((Player)obj).getName())){
                return true;
            }
        }
        return false;
    }
}
