/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Command;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;
/**
 *
 * @author x
 */
public class GameClient {
    private static InputStream cIn;
    private static OutputStream cOut;
    private static Socket cSocket;
    public String serverResponse;
    static ObjectInputStream objectInputStream;
    static ObjectOutputStream objectOutputStream;
    
    //logging to console
    private static void log(String msg){
        System.out.println(msg);
    }
    
    //connects to server via constructing the client
    public GameClient(String host, int port) throws IOException{
        log("Connecting to " + host + ":" + port);
        cSocket = new Socket(host, port);
        log("Connection to " + cSocket.getRemoteSocketAddress() + " has successfully initalized.");
        
        //input and output stream initialization
       
        cIn = cSocket.getInputStream();
        cOut = cSocket.getOutputStream();


    }
    
    public Boolean isConnectionReset(){
        return cSocket.isClosed();
    }
    
    public void closeClient() throws IOException {
        cSocket.close();
    }
    
    //get msg from server
    public Object receiveMessage() throws IOException, ClassNotFoundException{
        Object sResponse = null;
        objectInputStream = new ObjectInputStream(cIn);
        try{
        sResponse = objectInputStream.readObject();
        } catch(IOException eof){
            log("quitting connection" + eof);
            objectInputStream.close();
        }
        return sResponse;
    }
    
    public void sendMessage(String cMsg) throws IOException{
        List<Message> msg = new ArrayList<>();
        msg.add(new Message(cMsg));
        objectOutputStream = new ObjectOutputStream(cOut);
        objectOutputStream.writeObject(msg);
    }
    public static void sendCommand(String cmd, Player sentFrom){
        try {
            Command command = new Command(cmd, sentFrom);
            objectOutputStream = new ObjectOutputStream(cOut);
            objectOutputStream.writeObject(command);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
