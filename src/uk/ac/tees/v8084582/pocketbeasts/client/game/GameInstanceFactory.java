/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

import java.io.IOException;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;

/**
 *
 * @author x
 */
public class GameInstanceFactory {
    
    public GameInstance createInstance(String type, Player player1) throws IOException{
            switch(type){
                case("SP"):
                    //create singleplayer game instance
                    //return new SinglePlayerGame();
                case("MP"):
                    //create multiplayer game instance
                    NetworkGame ng = NetworkGame.getInstance();
                    ng.resetGame();
                    ng.addPlayer(0, player1);
                    return NetworkGame.getInstance();
            }
        return null;
    }
    
}
