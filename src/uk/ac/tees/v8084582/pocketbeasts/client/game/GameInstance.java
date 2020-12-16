/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Deck;
import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Field;
import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Hand;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.GameUpdate;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;



/**
 *
 * @author x
 */
public abstract class GameInstance {
    public int gameID;
    public Player[] players;

    public void addPlayer(int playerNo, String playerName, Deck deck){
         if(players.length <= 2){
            players[playerNo] = new Player(playerName, deck);
        }
    }
    
    public abstract void startGame(Player player1, Player player2);
    public abstract void resetGame();
    public abstract void recvGameUpdate(GameUpdate gu);
    public abstract GameUpdate createGameUpdate();
    public abstract void getField();
    
}
