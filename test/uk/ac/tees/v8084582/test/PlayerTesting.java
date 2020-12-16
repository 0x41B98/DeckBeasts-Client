/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.test;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;
import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Card;
import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Deck;
/**
 *
 * @author v8084582
 */
public class PlayerTesting {
    Player[] players;
    public static final Card[] TEST_CARDS = new Card[] {
        new Card ("X1", "Test Card 1", 1, 1, 1, "fire", "beast"),
        new Card ("X2", "Test Card 2", 2, 2, 2, "earth", "magic"),
        new Card ("X3", "Test Card 3", 3, 3, 3, "water", "rouge"),
        new Card ("X3", "Test Card 4", 4, 4, 4, "fire", "warrior")
        };
    public static ArrayList<Card> getTestDeck(){
        ArrayList<Card> testDeck = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for(Card card : TEST_CARDS) {
                testDeck.add(new Card(card));
            }
        }
        return testDeck;
    }
    @Before
    public void setUp(){
        players = new Player[]{
            new Player("Alex", new Deck(getTestDeck())),
            new Player("Summer", new Deck(getTestDeck()))
        };
        for(Player player : players){
            player.newGame();
        }
    }
    @Test
    public void playerNameTest(){
        assertTrue(players[0].getName().equals("Alex"));
    }
    @Test
    public void manaTest(){
        int player1Mana = players[0].getManaAvailable();
        players[0].addMana();
        assertTrue(player1Mana < players[0].getManaAvailable());
        
    }
    
    public void main(){
        setUp();
    }
}
