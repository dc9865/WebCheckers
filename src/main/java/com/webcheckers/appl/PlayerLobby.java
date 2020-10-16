package com.webcheckers.appl;
import com.webcheckers.model.Player;

import java.util.HashMap;

public class PlayerLobby {
    /* collection of logged in players */
    private HashMap<String, Player> players;

    /* playerLobby constructor that allocates the HashMap */
    public PlayerLobby(){
        players = new HashMap<>();
    }

    /**
     * Signs into the server
     * @param username the username to sign in under
     * @return a new player representing that player
     */
    public Player signIn(String username){
        if(players != null && !(isLoggedIn(username)) && (isValidUsername(username))){
            Player newPlayer = new Player(username);
            players.put(username, newPlayer);
            return  newPlayer;
        }
        else return null;
    }

    /**
     * Signs out of the server
     * @param username the username of the player to sign out
     */
    public void signOut(String username){
        players.remove(username);
    }

    /**
     * Checks if a certain user is logged in
     * @param username the username to check
     * @return a boolean representing whether the user is logged in
     */
    private boolean isLoggedIn(String username){
        // If the player is already in the player lobby
        if(players.containsKey(username)){
            return true;
        }
        return false;
    }

    /**
     * Authenticates a username by checking that it is only alphanumeric
     * @param username The username String
     * @return whether or not the username is valid
     */
    public static boolean isValidUsername(String username){
        // Goes character by character checking the character against the
        // range of the ascii characters
        for (char character : username.toCharArray()){
            int ascii=(int)(character);
            if(!((ascii>=48 && ascii<=57) || (ascii >=65 && ascii <= 90) ||
                    (ascii >=97 && ascii <=122))){
                return false;
            }
        }
        return true;
    }

    /**
     *  Checks the state of a certain player to see if they can join a game.
     * @param username the name of the player
     * @return whether or not the player can play a game currently
     */
    public boolean canPlay(String username){
        if(players.containsKey(username)){
           return players.get(username).isAvailable();
        }
        return false;
    }

    /**
     * Returns an error depending on what the problem is.
     * @param username the name of the Player
     * @return the error message as a String
     */
    public String playError(String username){
        if(!players.containsKey(username)){
            return "That player is not logged in.";
        }
        else if(players.get(username).inGame()){
            return "That player is in a game.";
        }
        else{
            return "That player is currently spectating.";
        }
    }

    /**
     * Gets a Player from the players HashMap by username
     * @param username the username of the Player to get
     * @return the Player associated with the username
     */
    public Player getPlayer(String username){
        return players.get(username);
    }

    /**
     * Return the player hashmap.
     * @return the hashmap of logged in players
     */
    public HashMap<String, Player> getPlayers() {
        return players;
    }
}
