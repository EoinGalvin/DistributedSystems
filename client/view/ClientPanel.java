package client.view;

import client.controller.Controller;
import javax.swing.*;
import java.awt.*;
public class ClientPanel extends JPanel {
    private final Controller controller;
    private final JButton connectButton;
    private final JButton disconnectButton;
    private final JButton downloadButton;
    private final JTextField messageField;

    private final JTextField usernameField;
    private final JTextField passwordField;
    private final JTextPane messagesField;
    private final JButton sendMessageButton;
    private final JButton loginButton;
    private final JButton logoutButton;
    private final JTextPane errorText;

    private String username = null;
    private Boolean isConnected = false;
    static final String loginSuccessMessage = "803";
    static final String loginFailMessage = "804";
    static final String isLoggedInMessage = "809";
    static final String logoutSuccessMessage = "805";
    static final String logoutFailMessage = "806";
    static final String messageReceivedTrue = "812";
    static final String disconnectSuccess = "813";
    public ClientPanel(Controller app){
        super();
        this.controller = app;

        this.connectButton = new JButton("Connect");
        this.disconnectButton = new JButton("Disconnect");
        this.downloadButton = new JButton("Download Messages");
        this.sendMessageButton = new JButton("Send Message");
        this.loginButton = new JButton("Login");
        this.logoutButton = new JButton("Logout");

        this.messageField = new JTextField();
        this.messageField.setPreferredSize(new Dimension(300,40));

        this.usernameField = new JTextField();
        this.usernameField.setPreferredSize(new Dimension(300,40));

        this.passwordField = new JTextField();
        this.passwordField.setPreferredSize(new Dimension(300,40));

        this.messagesField = new JTextPane();

        this.errorText = new JTextPane();

        setupPanel();
        setupListeners();
        setupLayout();
    }
    private void setupPanel(){
        this.setBackground(Color.lightGray);
        this.add(connectButton);
        this.add(disconnectButton);
        this.add(messageField);
        this.add(sendMessageButton);
        this.add(downloadButton);
        this.add(new JLabel("Username:"));
        this.add(usernameField);
        this.add(new JLabel("Password:"));
        this.add(passwordField);
        this.add(loginButton);
        this.add(logoutButton);
        this.add(messagesField);
        this.add(errorText);
    }
    private void setupListeners(){
        connectButton.addActionListener(click -> connect());
        sendMessageButton.addActionListener(click -> sendMessage());
        disconnectButton.addActionListener(click -> disconnect());
        downloadButton.addActionListener(click -> getMessages());
        loginButton.addActionListener(click -> login());
        logoutButton.addActionListener(click -> logout());
    }
    private void setupLayout(){

    }
    private void connect(){
        controller.connect();
        this.isConnected = true;
    }
    private void login(){
        if(isConnected){
            String returned = controller.login(usernameField.getText(),passwordField.getText());

            System.out.println(returned);

            if(returned.equals(loginSuccessMessage)){
                errorText.setText("Login Successful");
                this.username = usernameField.getText();
            }
            if(returned.equals(loginFailMessage)){
                errorText.setText("Login Failed");
            }
        }
        else{
            errorText.setText("Not connected to server");
        }
    }
    private void logout(){
        if(isConnected){
            String returned = controller.logout(this.username);
            System.out.println(returned);

            if(returned.equals(logoutSuccessMessage)){
                errorText.setText("Logout successful");
                this.username = null;
            }
            if(returned.equals(logoutFailMessage)){
                errorText.setText("Logout Failed");
            }
        }
        else{
            errorText.setText("Not connected to server");
        }


    }
    private void disconnect(){
        if(isConnected){
            if(isUserLoggedIn()){
                logout();
            }
            String returned = controller.disconnect();
            this.isConnected = false;

            if(returned.equals(disconnectSuccess)){
                errorText.setText("You have disconnected from the server");
            }else{
                errorText.setText("Failed to disconnect from server");
            }

        }
        else{
            errorText.setText("You are not connected to the server");
        }
    }

    private void getMessages(){
        if(isUserLoggedIn()){
            String messages = controller.downloadMessages();
            messagesField.setText(messages);
        }
    }
    private boolean isUserLoggedIn(){
        boolean isLoggedIn = controller.isLoggedIn(isLoggedInMessage);
        if(!isLoggedIn){
            errorText.setText("USER NOT LOGGED IN");
        }
        return isLoggedIn;
    }
    private void sendMessage(){
            if(isUserLoggedIn()){
                if(messageField.getText().equals("")){
                    errorText.setText("You cannot send an empty message to the server");
                }else{
                    System.out.println("Message sent : " + messageField.getText());
                    String returned = controller.sendMessage(messageField.getText());

                    if(returned.equals(messageReceivedTrue)){
                        errorText.setText("Message Received");
                    }else{
                        errorText.setText("Message Not Received");
                    }
                }
            }
    }
}
