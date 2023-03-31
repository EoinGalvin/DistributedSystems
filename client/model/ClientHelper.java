package client.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ClientHelper {
   static final String endMessage = "807";
   static final String downloadMessage = "808";
   static final String loginMessage = "801";
   static final String logoutMessage = "802";
   static final String isLoggedInTrue = "810";
   static final String isLoggedInFalse = "811";
   private ClientStreamSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;
   ClientHelper(String hostName, int portNum) throws SocketException, UnknownHostException, IOException {
      this.serverHost = InetAddress.getByName(hostName);
      this.serverPort = portNum;
      this.mySocket = new ClientStreamSocket(this.serverHost, this.serverPort);
   }
   public String sendMessage(String message) throws SocketException,IOException{
      mySocket.sendMessage(message);
      String returned = mySocket.receiveMessage();
      return returned;
   }
   public String disconnect() throws SocketException, IOException{
      mySocket.sendMessage(endMessage);
      String returned = mySocket.receiveMessage();
      mySocket.disconnect();
      return returned;
   }
   public String getDownloadedMessages() throws IOException {
      mySocket.sendMessage(downloadMessage);
      String message = mySocket.receiveMessage();
      return message;
   }
   public String login(String username,String password) throws IOException {
      mySocket.sendMessage(loginMessage);
      mySocket.sendMessage(username);
      mySocket.sendMessage(password);

      return mySocket.receiveMessage();
   }
   public String logout(String username) throws IOException{
      mySocket.sendMessage(logoutMessage);
      mySocket.sendMessage(username);
      return mySocket.receiveMessage();
   }
   public Boolean isLoggedIn(String query) throws IOException {
      mySocket.sendMessage(query);
      String returned = mySocket.receiveMessage();
      if(returned.equals(isLoggedInTrue)){
         return true;
      }
      else{ // isLoggedInFalse
         return false;
      }
   }
}
