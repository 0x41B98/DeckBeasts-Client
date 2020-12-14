/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;

/**
 *
 * @author x
 */
public class ClientObserver implements Observer {

    String newCommand;
    int obj;

    @Override
    public void update(Observable o, Object arg) {
            log("New command recieved to Observer: " + arg);
            
            if(arg instanceof List){
                List list = (List) arg;
                for(Object e: list){
                    if(e instanceof Message){
                        ClientHandler.parseMessage(list);
                    }
                    else if(e instanceof ServerCardDirectory){
                        
                    }
                }
            }
            
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

}
