package client.view;

import javax.swing.JFrame;
import client.controller.Controller;
public class ClientFrame extends JFrame{
    private Controller controller;
    private ClientPanel panel;

    public ClientFrame(Controller app){
        super();
        this.controller = app;
        this.panel = new ClientPanel(this.controller);

        setupFrame();
    }
    private void setupFrame(){
        this.setContentPane(panel);
        this.setSize(800,600);
        this.setResizable(false);
        this.setTitle("Client UI");
        this.setVisible(true);
    }
}
