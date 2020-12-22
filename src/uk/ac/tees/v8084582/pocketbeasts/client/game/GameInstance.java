/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.Field;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.GameUpdate;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;



/**
 *
 * @author x
 */
public abstract class GameInstance {
    public int gameID;
    public int whosTurn = 0;
    public int turnNo = 0;
    public Player[] players;

    public void addPlayer(int playerNo, Player player){
         if(players.length <= 2){
            players[playerNo] = player;
        }
    }
    
    public abstract void startGame(Player player1, Player player2);
    public abstract void resetGame();
    public abstract void recvGameUpdate(GameUpdate gu);
    public abstract GameUpdate createGameUpdate();
    public abstract Field[] getFields();
    
}
