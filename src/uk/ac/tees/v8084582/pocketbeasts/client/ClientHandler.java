/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import uk.ac.tees.v8084582.pocketbeasts.client.game.GameSubject;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;

/**
 *
 * @author x
 */
public class ClientHandler extends ClientObserver {

    @Override
    public void update(Observable o, Object recvObject) {
        if (recvObject instanceof List) {
            List list = (List) recvObject;
            for (Object e : list) {
                if (e instanceof Message) {
                    parseMessage(list);
                }
            }
        } else if (recvObject instanceof ServerCardDirectory) {
            parseCardDirectory((ServerCardDirectory) recvObject);
        }
    }

    static GameClient client = ClientWindow.client;
    static ServerCardDirectory ccd = ServerCardDirectory.getInstance();
    private static int recvObjectID = 0;

    public static void parseMessage(List<Message> messages) {
        log("recv messages");
        messages.forEach((msg) -> log(msg.getText()));
    }

    public static void parseCardDirectory(ServerCardDirectory scd) {
        ccd.cardList = scd.cardList;
        log("Added cardlist");
    }

    public static void parseResponse(Object resp) {
        log("Recieved obj: " + resp);
        switch (recvObjectID) {
            case 1:
            //ccd.cardList = (ServerCardDirectory) resp;
        }
        if ("eoo".equals((String) resp)) {
            recvObjectID = 0;
        }
    }

    public static void parseResponse(String resp) throws IOException {
        int opCode = -1;
        boolean isValidOpCode = true;
        String[] parsedResp = resp.split(":");
        try {
            opCode = Integer.parseInt(parsedResp[0]);
        } catch (NumberFormatException nfe) {
            isValidOpCode = false;
        }
        if (isValidOpCode) {
            switch (opCode) {
                //confirm connection
                case 1:
                    log("Recieved Connection Ack");
                    break;
                //
                case 2:
                    log("Recieved Login Ack: " + parsedResp[1]);
                    client.sendMessage("99:Client Ack");
                    break;
                //open/close Observer to accept Serialized objects
                case 4:

            }
        }
    }

}
