package server;

import javax.swing.*;
import java.awt.*;

public class ServerPanel extends JPanel {
    public ServerPanel(){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Server ready");
        this.add(label);
    }
    public void displayMessage(String message){
        System.out.println("adding message to panel " + message);
        JLabel label = new JLabel(message + "\n");
        this.add(label);
        this.revalidate();
        this.repaint();
    }
}
