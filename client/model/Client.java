package client.model;

public class Client {
   ClientHelper clientHelper;

   public void connect(String hostName,int portNum){
      try {
         this.clientHelper = new ClientHelper(hostName, portNum);
      }
      catch (Exception ex) {
         ex.printStackTrace( );
      }
   }
   public String sendMessage(String message) {
      String returned = "";
      try {
         returned = this.clientHelper.sendMessage(message);
         System.out.println("Sending message : " + message);
      } catch (Exception ex) {
         System.out.println("You are not connected to the server");
         //ex.printStackTrace();
      }
      return returned;
   }
   public String disconnect(){
      String returned = "";
      try {
         returned = this.clientHelper.disconnect();
         System.out.println("You have disconnected from the server");
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      return returned;
   }
   public String downloadMessages(){
      String message = "";
      try {
         message = this.clientHelper.getDownloadedMessages();
         System.out.println("The downloaded messages are : " + message);
      } catch (Exception ex) {
         System.out.println("You are not connected to the server");
         //ex.printStackTrace();
      }
      return message;
   }
   public String sendLogin(String username,String password){
      String returned = "";
      try {
         returned = this.clientHelper.login(username,password);
      } catch (Exception ex) {
         System.out.println("Login request failed");
         //ex.printStackTrace();
      }
      return returned;
   }
   public String sendLogout(String username){
      String returned = "";
      try{
         returned = this.clientHelper.logout(username);
      } catch(Exception ex){
         System.out.println("Logout request failed");
      }
      return returned;
   }
   public boolean isLoggedIn(String query){
      boolean isLoggedIn = false;
      try {
         isLoggedIn = this.clientHelper.isLoggedIn(query);
      } catch(Exception ex){
         ex.printStackTrace();
      }
      return isLoggedIn;
   }
}

