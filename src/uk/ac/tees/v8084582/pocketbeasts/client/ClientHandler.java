/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import java.util.List;
import java.util.Observable;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Command;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.NetworkGameList;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;

/**
 *
 * @author x
 */
public class ClientHandler extends ClientObserver  {

    static GameClient client = ClientWindow.client;
    static ServerCardDirectory ccd = ServerCardDirectory.getInstance();

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
        } else if (recvObject instanceof Command) {
            parseCommand((Command) recvObject);
        } else if (recvObject instanceof Player) {
            setPlayer((Player) recvObject);
        } else if (recvObject instanceof NetworkGameList) {
            setGameRoomList((NetworkGameList) recvObject);
        }
    }
    
    private static void setGameRoomList(NetworkGameList networkGRL){
        log("updating game room list");
        NetworkGameList ngl = NetworkGameList.getInstance();
        ngl.clearGRL();
        for (String networkGRList : networkGRL.networkGRList) {
            log(networkGRList);
        }
        
        ClientWindow.populateGlobalGames();
        
    }

    private static void setPlayer(Player player) {
        log("recv profile");
        ClientWindow.setPlayer(player);
    }

    private static void parseCommand(Command c) {
        String[] cmd = c.getCommand().split(":");
        switch (cmd[0]) {
            case ("showdialogbox"): {
                ClientWindow.showDialogBox(cmd[1], cmd[2], cmd[3]);
            }
            break;
            case ("loginack"):
                if (cmd[1].equals("success")) {
                    ClientWindow.setWindow("homePanel");
                    ClientWindow.postLoginCommands(cmd[2]);
                } else if (cmd[1].equals("failed")) {
                    ClientWindow.showDialogBox("Login Failed", "Login incorrect!\nPlease try again.", "error");
                }
                break;
        }
    }

    private static void parseMessage(List<Message> messages) {
        log("recv messages");
        messages.forEach((msg) -> log(msg.getText()));
    }

    private static void parseCardDirectory(ServerCardDirectory scd) {
        ccd.cardList = scd.cardList;
        log("Added cardlist");
    }

}
