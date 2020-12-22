/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

import java.io.IOException;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Field;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.GameUpdate;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;


/**
 *
 * @author x
 */
public class NetworkGame extends GameInstance {
    
    public static NetworkGame INSTANCE;
    
    public static NetworkGame getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new NetworkGame();
        }
        return INSTANCE;
    }
    
    private NetworkGame(){
    }
    
    @Override
    public void startGame(Player player1, Player player2) {
        this.players[0] = player1;
        this.players[1] = player2;
        for(Player player: players){
            player.newGame();
        }
    }

    @Override
    public void resetGame() {
        players[0] = null;
        players[1] = null;
        whosTurn = 0;
        turnNo = 0;
    }

    @Override
    public void recvGameUpdate(GameUpdate gu) {
        gu.players[0] = this.players[0];
        gu.players[1] = this.players[1];
    }

    @Override
    public Field[] getFields() {
        Field[] fArry = {
            players[0].getInPlay(),
            players[1].getInPlay()
        };
        return fArry;
    }

    @Override
    public GameUpdate createGameUpdate() {
        return new GameUpdate(this.players);
    }
    
}
