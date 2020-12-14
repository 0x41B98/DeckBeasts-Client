/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import java.net.*;
import java.io.*;
import java.util.List;
/**
 *
 * @author x
 */
public class GameClient {
    private InputStream cIn;
    private OutputStream cOut;
    private Socket cSocket;
    public String serverResponse;
    ObjectInputStream objectInputStream;
    
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
        
        InputStream inputStream = cSocket.getInputStream();
        OutputStream outputStream = cSocket.getOutputStream();
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
            objectInputStream.close();
        }
        log("Recieved messages from server");
        return sResponse;
    }
    
    public void sendMessage(Object cMsg) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(cOut);
        objectOutputStream.writeObject(cMsg);
        objectOutputStream.flush();
    }
}
