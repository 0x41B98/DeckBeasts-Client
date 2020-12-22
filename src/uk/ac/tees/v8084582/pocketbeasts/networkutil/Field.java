/*
 * This file is part of PocketBeasts.
 *
 * PocketBeasts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * PocketBeasts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */
package uk.ac.tees.v8084582.pocketbeasts.networkutil;

import java.io.Serializable;


/**
 *
 * @author James Fairbairn
 * @author Steven Mead
 */
public class Field implements Serializable {

    private Card[] playerSide;

    public Field() {
        this.playerSide = new Card[6];
    }

    public Card[] getCards() {
        return this.playerSide;
    }

    public Card getCard(int index) {
        return this.playerSide[index];
    }

    public boolean add(char side, int index, Card card) {
        if (this.playerSide[index] == null) {
            this.playerSide[index] = card;
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(int index) {
        if(this.playerSide[index] != null){
            this.playerSide[index] = null;
            return true;
        } else {
            return false;
        }
    }
    
    

    public boolean removeAll() {
        for (Card card : playerSide) {
            card = null;
            return true;
        }
        return false;
    }

    public int count() {
        int notEmpty = 0;
        for (Card card : playerSide) {
            if(card != null){
                notEmpty++;
            }
        }
        return notEmpty;
    }
}
