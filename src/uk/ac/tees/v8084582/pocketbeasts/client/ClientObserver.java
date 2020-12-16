/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import java.util.Observer;

/**
 *
 * @author x
 */
public abstract class ClientObserver implements Observer {

    public static void log(String msg) {
        System.out.println(msg);
    }

}
