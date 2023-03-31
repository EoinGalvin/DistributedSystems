package client.controller;
import client.model.Client;
import client.view.ClientFrame;
public class Controller {
    private Client client;
    private ClientFrame frame;
    public Controller(){
    }
    public void start(){
        this.frame = new ClientFrame(this);
    }
    public void connect(){
        this.client = new Client();
        this.client.connect("localhost",40);
    }
    public String sendMessage(String message){
        return this.client.sendMessage(message);
    }
    public String disconnect(){
        return this.client.disconnect();
    }
    public String downloadMessages(){
        return this.client.downloadMessages();
    }
    public String login(String username,String password){
        return this.client.sendLogin(username,password);
    }

    public String logout(String username){return this.client.sendLogout(username);}
    public boolean isLoggedIn(String query){
        return this.client.isLoggedIn(query);
    }
}
