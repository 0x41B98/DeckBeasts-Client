/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.networkutil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author x
 */
public class NetworkGameList implements Serializable{
    
    private static NetworkGameList INSTANCE;
    
    //for sending game room lists over the network
    
    public List<String> networkGRList = new ArrayList<>();
    
    public void clearGRL(){
        networkGRList = null;
    }
    
    public void addToGRL(String[] gameRoomAtt){
        networkGRList.addAll(Arrays.asList(gameRoomAtt));
    }
    
    public static NetworkGameList getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkGameList();
        }
        return INSTANCE;
    }
    
}
