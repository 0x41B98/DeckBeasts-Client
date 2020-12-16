/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import java.util.Observable;

/**
 *
 * @author x
 */
public class ClientSubject extends Observable {

    public void changeStateTo(Object newMove) {
        this.setChanged();
        this.notifyObservers(newMove);
    }

}
