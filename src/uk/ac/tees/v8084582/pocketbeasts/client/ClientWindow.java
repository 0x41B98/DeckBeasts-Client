/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.tees.v8084582.pocketbeasts.client;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
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
import uk.ac.tees.v8084582.pocketbeasts.networkutil.NetworkGameList;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.Player;
import uk.ac.tees.v8084582.pocketbeasts.networkutil.ServerCardDirectory;

/**
 *
 * @author V8084582
 */
public class ClientWindow extends JFrame {

    private static ClientWindow INSTANCE;

    private static Container contextFrame = null;

    private static ServerCardDirectory ccd = ServerCardDirectory.getInstance();

    private static JPanel contentPane;
    private static JList gameList;
    private static DefaultListModel<String> model;

    private static CardLayout windows;
    
    private static JScrollPane gameSelectPane;

    //Client/Server logic
    public static GameClient client;
    public static ClientSubject obClientSubject = new ClientSubject();
    public static ClientHandler obClientHandler = new ClientHandler();
    private static Object serverResponse = null;

    //profile
    private static Player usrPlayer;

    public Player getPlayer() {
        return usrPlayer;
    }

    public static void setPlayer(Player player) {
        usrPlayer = player;
    }

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
        clientListener();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientWindow frame = new ClientWindow();
                    contextFrame = frame.getContentPane();
                    frame.setVisible(true);
                    //serverResponse = client.receiveMessage();
                    //log("Server response:" + serverResponse);
                } catch (IOException ex) {
                    log("Error loading frame: " + ex.toString());
                }
            }
        });
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
                                GameClient.objectInputStream.close();
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

    public ClientWindow() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 600);

        setTitle("Deck Beasts");
        JPanel homePanel = new JPanel();
        JPanel loginPanel = new JPanel();
        JPanel changePanel = new JPanel();
        JPanel gamePanel = new JPanel();
        JPanel scoresPanel = new JPanel();

        homePanel.setVisible(false);
        loginPanel.setVisible(true);
        changePanel.setVisible(false);
        gamePanel.setVisible(false);
        scoresPanel.setVisible(false);

        contentPane = new JPanel(new CardLayout());
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        windows = (CardLayout) contentPane.getLayout();

        contentPane.add(loginPanel, "Login");
        contentPane.add(homePanel, "homePanel");
        contentPane.add(changePanel, "Change");
        contentPane.add(gamePanel, "gamePanel");
        contentPane.add(scoresPanel, "Scores");
        scoresPanel.setLayout(null);

        JLabel lblDeckBeastsTitle = new JLabel("Deck Beasts");

        setContentPane(contentPane);

        //home panel
        homePanel.setLayout(null);

        //global game list
        JPanel gameSelectPanel = new JPanel();
        gameSelectPanel.setBounds(5, 5, 400, 400);
        homePanel.add(gameSelectPanel);
        gameSelectPanel.setLayout(null);

        gameSelectPane = new JScrollPane();
        gameSelectPane.setBounds(5, 5, 322, 350);
        gameSelectPanel.add(gameSelectPane);
        JLabel lblGameSelect = new JLabel("Global Games");
        lblGameSelect.setHorizontalAlignment(SwingConstants.CENTER);
        gameSelectPane.setColumnHeaderView(lblGameSelect);
        model = new DefaultListModel<>();
        gameList = new JList(model);
        gameSelectPane.setViewportView(gameList);

        JButton btnJoinGame = new JButton("Join Game");
        btnJoinGame.setBounds(7, 366, 100, 30);
        gameSelectPanel.add(btnJoinGame);

        JButton btnCreateGlobalGame = new JButton("Create");
        btnCreateGlobalGame.setBounds(116, 366, 100, 30);
        gameSelectPanel.add(btnCreateGlobalGame);

        JButton btnRefreshGlobalGames = new JButton("Refresh");
        btnRefreshGlobalGames.setBounds(225, 366, 100, 30);
        gameSelectPanel.add(btnRefreshGlobalGames);

        //button actions
        //get game list
        btnRefreshGlobalGames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameClient.sendCommand("getroomlist", usrPlayer);
            }
        });

        btnCreateGlobalGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String choice = null;
                int option = JOptionPane.showConfirmDialog(null, "Would you like to allow observers?", "Allow Observers?", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    choice = "true";
                } else {
                    choice = "false";
                }
                GameClient.sendCommand(("createroom:" + choice), usrPlayer);
            }
        });

        //user panel button
        JButton btnUserProfile = new JButton("Profile");
        btnUserProfile.setBounds(850, 5, 114, 27);
        homePanel.add(btnUserProfile);

        //user panel
        changePanel.setLayout(null);

        JLabel lblChangeName = new JLabel("Change Name");
        lblChangeName.setBounds(5, 5, 159, 16);
        changePanel.add(lblChangeName);
        lblChangeName.setFont(new Font("Comic Sans", Font.PLAIN, 18));

        JTextField changeNameField = new JTextField();
        changeNameField.setBounds(5, 28, 120, 28);
        changePanel.add(changeNameField);
        changeNameField.setColumns(16);

        JButton btnBackProfile = new JButton("Back");
        btnBackProfile.setBounds(6, 490, 90, 28);
        changePanel.add(btnBackProfile);

        //button actions
        //open user profile
        btnUserProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windows.show(contentPane, "Change");
            }
        });

        //exit user panel
        btnBackProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windows.show(contentPane, "homePanel");
            }
        });

        //login panel
        loginPanel.setLayout(null);
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(403, 140, 40, 40);
        loginPanel.add(lblLogin);

        JLabel lblLoginUsr = new JLabel("Username:");
        lblLoginUsr.setBounds(330, 165, 70, 40);
        loginPanel.add(lblLoginUsr);

        JTextField txtUsernameLogin = new JTextField();
        txtUsernameLogin.setBounds(403, 174, 130, 26);
        loginPanel.add(txtUsernameLogin);
        txtUsernameLogin.setColumns(10);

        JLabel lblLoginPass = new JLabel("Password:");
        lblLoginPass.setBounds(330, 201, 70, 40);
        loginPanel.add(lblLoginPass);

        JPasswordField txtPasswordLogin = new JPasswordField();
        txtPasswordLogin.setBounds(403, 212, 130, 26);
        loginPanel.add(txtPasswordLogin);
        txtPasswordLogin.setColumns(10);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(403, 250, 130, 29);
        loginPanel.add(btnLogin);

        JLabel lblRegister = new JLabel("Registration:");
        lblRegister.setBounds(403, 280, 75, 60);
        loginPanel.add(lblRegister);

        JTextField txtUsernameReg = new JTextField();
        txtUsernameReg.setBounds(403, 330, 130, 26);
        loginPanel.add(txtUsernameReg);
        txtUsernameReg.setColumns(10);

        JLabel lblRegUsr = new JLabel("Username:");
        lblRegUsr.setBounds(330, 321, 70, 40);
        loginPanel.add(lblRegUsr);

        JPasswordField txtPasswordReg = new JPasswordField();
        txtPasswordReg.setBounds(403, 365, 130, 26);
        loginPanel.add(txtPasswordReg);
        txtPasswordReg.setColumns(10);

        JLabel lblRegPass = new JLabel("Password:");
        lblRegPass.setBounds(330, 356, 70, 40);
        loginPanel.add(lblRegPass);

        JPasswordField txtPasswordConfirmReg = new JPasswordField();
        txtPasswordConfirmReg.setBounds(403, 400, 130, 26);
        loginPanel.add(txtPasswordConfirmReg);
        txtPasswordConfirmReg.setColumns(10);

        JLabel lblRegPassConfirm = new JLabel("Confirm:");
        lblRegPassConfirm.setBounds(330, 391, 70, 40);
        loginPanel.add(lblRegPassConfirm);

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(403, 436, 130, 29);
        loginPanel.add(btnRegister);

        //login panel buttons
        btnLogin.addActionListener((ActionEvent e) -> {
            String username = txtUsernameLogin.getText();
            char[] password = txtPasswordLogin.getPassword();

            // check empty string case
            if (username.equals("") || password.length == 0) {
                String errorMsg = "Please enter both username and password";
                String errorTitle = "Login Error";
                showDialogBox(errorTitle, errorMsg, "error");
            } else {
                try {
                    String hashedpw = convertPassToSHA256(password);
                    String loginCommand = "login:" + username + ":" + hashedpw;
                    //awaitLoginConfirmation(loginCommand);
                    GameClient.sendCommand(loginCommand, null);
                    txtUsernameLogin.setText("");
                    txtPasswordLogin.setText("");
                } catch (NoSuchAlgorithmException ex) {
                    log("Error @ login, failed to hash password: " + ex);
                }
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            String hashedpw;

            public void actionPerformed(ActionEvent e) {
                String username = txtUsernameReg.getText();
                char[] password = txtPasswordReg.getPassword();
                char[] passwordconfirm = txtPasswordConfirmReg.getPassword();

                //basic registration validation
                if (username.equals("") || password.length == 0 || passwordconfirm.length == 0) {
                    String errorMsg = "Please enter username, password & password comfirmation!";
                    String errorTitle = "Registration Error";
                    showDialogBox(errorTitle, errorMsg, "error");
                } else if (username.length() <= 3 || username.length() >= 17) {
                    String errorMsg = "Username error, usernames must be between 4 and 16 characters long!";
                    String errorTitle = "Registration Error";
                    showDialogBox(errorTitle, errorMsg, "error");
                } else if (!Arrays.equals(password, passwordconfirm)) {
                    String errorMsg = "Passwords do not match!";
                    String errorTitle = "Registration Error";
                    showDialogBox(errorTitle, errorMsg, "error");
                } else if (password.length <= 7 || password.length >= 33) {
                    String errorMsg = "Passwords must be between 8 and 32 characters long!";
                    String errorTitle = "Registration Error";
                    showDialogBox(errorTitle, errorMsg, "error");
                } else {
                    try {
                        hashedpw = convertPassToSHA256(txtPasswordReg.getPassword());
                    } catch (NoSuchAlgorithmException ex) {
                        log("Error hashing password: " + ex.toString());
                    }
                    String cmd = "reguser:" + txtUsernameReg.getText() + ":" + hashedpw;
                    GameClient.sendCommand(cmd, null);
                    txtUsernameReg.setText("");
                    txtPasswordReg.setText("");
                    txtPasswordConfirmReg.setText("");

                }
            }
        });

        //adds enter-able action to login password btn & confirm registration password
        txtPasswordLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });

        txtPasswordConfirmReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnRegister.doClick();
            }
        });

    }
    
    public static void populateGlobalGames(){
        NetworkGameList ngl = NetworkGameList.getInstance();
        model.clear();
        ngl.networkGRList.forEach((networkGRList) -> {
            //String row = "Game #" + networkGRList[0] + " | " + networkGRList[1] + " | " + networkGRList[2];
            model.addElement(networkGRList);
        });
        
    }

    public static void setWindow(String panelID) {
        windows.show(contentPane, panelID);

    }

    public static void showDialogBox(String title, String message, String type) {
        switch (type.toLowerCase()) {
            case ("error"):
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
                break;
            case ("info"):
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
                break;
        }

    }

    public static void postLoginCommands(String username) {
        GameClient.sendCommand("sendcards", null);
        GameClient.sendCommand(("reqprofile:" + username), null);
    }

    private String convertPassToSHA256(char[] pw) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String generatedPassword;
        String toConvert = new String(pw);
        byte[] pwbytes = md.digest(toConvert.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pwbytes.length; i++) {
            sb.append(Integer.toString((pwbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        return generatedPassword;
    }

}
