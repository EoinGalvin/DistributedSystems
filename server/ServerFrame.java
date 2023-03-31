package server;

import javax.swing.*;

import client.controller.Controller;
public class ServerFrame extends JFrame{
    private ServerPanel panel;

    public ServerFrame(){
        super();
        this.panel = new ServerPanel();
        setupFrame();
    }
    private void setupFrame(){
        this.setContentPane(panel);
        this.setSize(800,600);
        this.setResizable(false);
        this.setTitle("Server UI");
        this.setVisible(true);
    }

    public void sendMessageToPanel(String message){
        this.panel.displayMessage(message);
    }
}