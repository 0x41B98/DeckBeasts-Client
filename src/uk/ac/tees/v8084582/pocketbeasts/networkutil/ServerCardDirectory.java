/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.networkutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author x
 */
public class ServerCardDirectory implements Serializable {
    
    public ArrayList<Card> cardList;
    private static ServerCardDirectory INSTANCE;

    private ServerCardDirectory() {
        cardList = new ArrayList<>();
        
    }
    
    public static ServerCardDirectory getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ServerCardDirectory();
        }
        return INSTANCE;
    }
    
    public void removeAll(List<Card> cList){
        this.cardList.removeAll(cList);
    }
    
    public void add(Card c){
        this.cardList.add(c);
    }
    
    public void remove(Card c){
       this.cardList.remove(c);
    }
    
}
