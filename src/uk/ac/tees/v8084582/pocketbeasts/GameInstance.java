/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts;

/**
 *
 * @author x
 */
public class GameInstance {
    
    private Field p1;
    private Field p2;
    private Field[] inPlay;
    private Player[] players;
    

    public GameInstance(Boolean isSinglePlayer) {
        Field[] inPlay = new Field[] {
            p1 = new Field(),
            p2 = new Field()
        };
    }
    public void addPlayer(int playerNo, String playerName, Deck deck){
        if(players.length <= 2){
            //players[playerNo] = new Player(playerName);
        }
    }
    
    public void buildDeck(int playerNo, Card[] cards){
        
    }
    
}
