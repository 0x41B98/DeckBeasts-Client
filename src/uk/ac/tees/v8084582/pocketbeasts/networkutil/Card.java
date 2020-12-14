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
public class Card implements Serializable {

    private final String id;
    private final String name;
    private final int manaCost;
    private final int attack;
    private int health;
    private String element;
    private String type;
    
    public Card(String id, String name, int manaCost, int attack, int health, String element, String type) {
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.attack = attack;
        this.health = health;
        this.element = element;
        this.type = type;
    }
    
    public Card(Card card) {
        this.id = card.id;
        this.name = card.name;
        this.manaCost = card.manaCost;
        this.attack = card.attack;
        this.health = card.health;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }

    public int getManaCost() {
        return this.manaCost;
    }
    
    public int getAttack() {
        return this.attack;
    }
    
    public int getHealth() {
        return this.health;
    }
    public String getType(){
        return this.type;
    }
    /*
    @Override
    public void recvDamage(int amount) {
        this.health -= amount;
    }*/

    @Override
    public String toString() {
        return this.name + " (" + this.id + ") Mana Cost/" + this.manaCost + 
                " Attack/" + this.attack + " Health/" + this.health;
    }

    /*
    @Override
    public int compareTo(serverCard o) {
        return Integer.compare(this.getManaCost(), o.getManaCost());
    }
    */
    public String getElement() {
        return this.element;
    }
}
