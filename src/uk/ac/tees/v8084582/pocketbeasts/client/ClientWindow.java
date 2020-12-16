/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import uk.ac.tees.v8084582.pocketbeasts.client.game.objects.Card;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Message;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;

/**
 *
 * @author V8084582
 */
public class ClientWindow extends JFrame {

    private static ClientWindow INSTANCE;

    private static ServerCardDirectory ccd = ServerCardDirectory.getInstance();

    private JPanel contentPane;
    private JTextField usrText;
    private JPasswordField passText;
    private JTextField fusrSignup;
    private JTextField lusrSignup;
    private JPasswordField passSignup;
    private JPasswordField newPass;
    private JPasswordField confirmPass;
    private JButton[][] buttons;
    private static JButton resetBtn;

    private static CardLayout windows;

    //Client/Server logic
    public static GameClient client;
    public static ClientSubject obClientSubject = new ClientSubject();
    public static ClientHandler obClientHandler = new ClientHandler();
    private static Object serverResponse = null;

    //game logic
    private static int moveCount = 0;
    private static boolean gameWon = false;
    private static int playersTurn = 1;

    private static void log(String msg) {
        System.out.println(msg);
    }

    private static void initialConnect() {
        int port = 11223;
        String host = JOptionPane.showInputDialog("Enter server IP, or if localhost type \"127.0.0.1\"");
        try {
            client = new GameClient(host, port);
            obClientSubject.addObserver(obClientHandler);
        } catch (UnknownHostException e) {
            log(e.getMessage());
        } catch (IOException e) {
            log(e.getMessage());
        }
    }

    /*
    * Run the game launcher
     */
    public static void main(String[] args) {
        initialConnect();
        EventQueue.invokeLater(() -> {
            try {
                ClientWindow frame = new ClientWindow();
                frame.setVisible(true);
                clientListener();
                //serverResponse = client.receiveMessage();
                //log("Server response:" + serverResponse);
            } catch (IOException ex) {
                log("Error loading frame: " + ex.toString());
            }
        });
    }

    private static String sendCommand() throws IOException {
        String clientSend = JOptionPane.showInputDialog("Enter command to send to server");
        client.sendMessage(clientSend);

        //initiate GUI when connected to server
        //ClientWindow frame = new ClientWindow();
        //frame.setVisible(true);
        return "Done";

    }

    private static void clientListener() {
        boolean isConnected = true;
        log("Server Response Thread Started");
        new Thread() {
            @Override
            public void run() {
                while (isConnected) {
                    while (serverResponse == null) {
                        try {
                            serverResponse = client.receiveMessage();
                        } catch (IOException | ClassNotFoundException ex) {
                            try {
                                client.objectInputStream.close();
                            } catch (IOException ex1) {
                                log("End of file error: " + ex1.toString());
                            }
                        }
                    }
                    if (serverResponse != null) {
                        log("\nServer: " + serverResponse);
                        obClientSubject.changeStateTo(serverResponse);
                        serverResponse = null;
                    }
                }

            }
        }.start();
    }

    private ClientWindow() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);

        setTitle("Deck Beasts");
        JPanel userPanel = new JPanel();
        JPanel loginPanel = new JPanel();
        JPanel changePanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel scoresPanel = new JPanel();
        JPanel signupPanel = new JPanel();

        userPanel.setVisible(false);
        loginPanel.setVisible(false);
        changePanel.setVisible(false);
        gamePanel.setVisible(false);
        scoresPanel.setVisible(false);
        signupPanel.setVisible(false);

        contentPane = new JPanel(new CardLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        windows = (CardLayout) contentPane.getLayout();

        contentPane.add(loginPanel, "Login");
        contentPane.add(userPanel, "Userpanel");
        contentPane.add(changePanel, "Change");
        contentPane.add(signupPanel, "Signup");
        contentPane.add(gamePanel, "gamePanel");
        contentPane.add(scoresPanel, "Scores");
        scoresPanel.setLayout(null);

        JLabel lblDeckBeastsTitle = new JLabel("Deck Beasts");

        setContentPane(contentPane);
        userPanel.setLayout(null);

        //global game list
        JPanel gameSelectPanel = new JPanel();
        gameSelectPanel.setBounds(5, 5, 400, 400);
        userPanel.add(gameSelectPanel);
        gameSelectPanel.setLayout(null);

        JScrollPane gameSelectPane = new JScrollPane();
        gameSelectPane.setBounds(5, 5, 322, 350);
        gameSelectPanel.add(gameSelectPane);
        JLabel lblGameSelect = new JLabel("Global Games");
        lblGameSelect.setHorizontalAlignment(SwingConstants.CENTER);
        gameSelectPane.setColumnHeaderView(lblGameSelect);
        JList gameList = new JList();
        gameSelectPane.setViewportView(gameList);

        JButton btnJoinGame = new JButton("Join Game");
        btnJoinGame.setBounds(7, 366, 100, 30);
        gameSelectPanel.add(btnJoinGame);

        JButton btnRefreshGlobalGames = new JButton("Refresh");
        btnRefreshGlobalGames.setBounds(225, 366, 100, 30);
        gameSelectPanel.add(btnRefreshGlobalGames);

        /*
        debugging buttons
         */
        JButton btnSendCommandServer = new JButton("Send Server Command");
        btnSendCommandServer.setBounds(200, 200, 100, 30);
        userPanel.add(btnSendCommandServer);

        JButton btnSendCommandClient = new JButton("Send Client Command");
        btnSendCommandClient.setBounds(200, 320, 100, 30);
        userPanel.add(btnSendCommandClient);

        //button actions
        btnRefreshGlobalGames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //client.sendMessage("");
            }
        });

        windows.show(contentPane, "Userpanel");
        log("loaded gui");
        client.sendMessage("COMMAND:sendcards");

    }

    public static void setCardDirectory(ArrayList<Card> c) {
        //ccd.cardList = c;

    }

    public static ClientWindow getInstance() throws IOException {
        if (INSTANCE == null) {
            INSTANCE = new ClientWindow();
        }
        return INSTANCE;
    }
}
