/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts;

import java.util.Observable;

/**
 *
 * @author x
 */
public class ClientSubject extends Observable {

    public void changeStateTo(Object newCommand) {
        this.setChanged();
        this.notifyObservers(newCommand);
    }

}
