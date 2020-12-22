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
public class Command implements Serializable{
    private final String command;
    private final Player sentFrom;

    public Command(String command, Player sentFrom) {
        this.command = command;
        this.sentFrom = sentFrom;
        
    }
    
    public Player getSentFrom(){
        return sentFrom;
    }

    public String getCommand() {
        return command;
    }
}
