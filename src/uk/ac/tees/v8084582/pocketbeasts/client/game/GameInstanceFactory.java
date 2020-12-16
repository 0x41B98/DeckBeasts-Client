/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

/**
 *
 * @author x
 */
public class GameInstanceFactory {
    
    public static GameInstance createInstance(String type){
            switch(type){
                case("SP"):
                    //create singleplayer game instance
                    return new SinglePlayerGame();
                case("MP"):
                    //create multiplayer game instance
                    return new NetworkGame();
            }
        return null;
    }
    
}
