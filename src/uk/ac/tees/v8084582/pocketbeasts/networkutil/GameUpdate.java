/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.networkutil;

import java.io.Serializable;

/**
 *
 * @author x
 */
public class GameUpdate implements Serializable {
    public Player[] players;
    
    public GameUpdate(Player[] players){
        this.players[0] = players[0];
        this.players[1] = players[1];
    }
}
