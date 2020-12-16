/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client.game;

import java.io.IOException;
import uk.ac.tees.v8084582.pocketbeasts.client.ClientWindow;
import uk.ac.tees.v8084582.pocketbeasts.client.GameClient;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.GameUpdate;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;


/**
 *
 * @author x
 */
public class NetworkGame extends GameInstance {
    
    public NetworkGame INSTANCE;
    
    public NetworkGame getInstance() throws IOException {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recvGameUpdate(GameUpdate gu) {
        gu.players[0] = this.players[0];
        gu.players[1] = this.players[1];
    }

    @Override
    public void getField() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GameUpdate createGameUpdate() {
        return new GameUpdate(this.players);
    }
    
}
