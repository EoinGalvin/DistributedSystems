package server;

import java.io.IOException;
class ServerThread implements Runnable {
   static final String endMessage = "807";
   static final String downloadMessage = "808";
   static final String loginMessage = "801";
    static final String logoutMessage = "802";
   static final String loginSuccessMessage = "803";
    static final String loginFailMessage = "804";
    static final String logoutSuccessMessage = "805";
    static final String logoutFailMessage = "806";
    static final String isLoggedInMessage = "809";
    static final String isLoggedInTrue = "810";
    static final String isLoggedInFalse = "811";
    static final String messageReceivedTrue = "812";
    static final String disconnectSuccess = "813";
    String username = null;
    String password = null;
    boolean loggedIn = false;
    ServerStreamSocket myDataSocket;
    Server server;

   ServerThread(ServerStreamSocket myDataSocket,Server server) {
      this.myDataSocket = myDataSocket;
      this.server = server;
   }
   public void run() {
       runThread();
   }

   private void runThread(){
       boolean completed = false;
       String message;
       try {
           while (!completed) {
               message = myDataSocket.receiveMessage();
               switch (message) {
                   case endMessage -> {endSession(); completed = true;}
                   case downloadMessage -> downloadMessagesRequest();
                   case loginMessage -> loginRequest();
                   case isLoggedInMessage -> isLoggedInRequest();
                   case logoutMessage -> logoutRequest();
                   default -> messageReceived(message);
               }
           }
       }
       catch (Exception ex) {
           System.out.println("Exception caught in thread: " + ex);
       }
   }
   private void loginRequest() throws IOException {
       this.server.addThreadMessages("Login request received");
       this.username = myDataSocket.receiveMessage();
       this.password = myDataSocket.receiveMessage();

       if(this.password != null && this.username != null){
           if(!this.server.getUserLogins().containsKey(this.username)){
               this.server.addThreadMessages("LOGIN FAILED: Invalid Username Provided");
               myDataSocket.sendMessage(loginFailMessage);
           }
           else{
               if(this.server.getUserLogins().get(this.username).equals(this.password)){
                   this.server.addThreadMessages("USER LOGGED IN : " + this.username);
                   this.server.userLoggedIn(this.username);
                   this.loggedIn = true;
                   myDataSocket.sendMessage(loginSuccessMessage);
                   this.server.addThreadMessages("USERS CONNECTED : " + String.join(", ", server.getUsers()));
               }
               else{
                   this.server.addThreadMessages("LOGIN FAILED: Incorrect Password Provided");
                   myDataSocket.sendMessage(loginFailMessage);
               }
           }
       }
       else{
           myDataSocket.sendMessage(loginFailMessage);
       }
   }
   private void logoutRequest() throws IOException {
       this.server.addThreadMessages("Logout request received ");
       this.username = myDataSocket.receiveMessage();

       if(server.getUsers().contains(this.username)){
           server.userLoggedOut(this.username);
           this.loggedIn = false;
           myDataSocket.sendMessage(logoutSuccessMessage);

       }else{
           System.out.println("User " + this.username + " was not logged in");
           myDataSocket.sendMessage(logoutFailMessage);
       }
   }
   private void downloadMessagesRequest(){
       this.server.addThreadMessages("Download Messages Request Received");
       this.server.addThreadMessages("Sending all messages : " + String.join(", ", server.getMessages()));
       myDataSocket.sendMessage(String.join(", ", server.getMessages()));
   }
   private void isLoggedInRequest(){
       this.server.addThreadMessages("Logged In Query Received");
       if(loggedIn){
           myDataSocket.sendMessage(isLoggedInTrue);
       }else{
           myDataSocket.sendMessage(isLoggedInFalse);
       }
   }
   private void messageReceived(String message){
       this.server.addThreadMessages("MESSAGE RECEIVED: "+ message);
       server.addMessage(message);
       myDataSocket.sendMessage(messageReceivedTrue);
   }
   private void endSession() throws IOException {
       this.server.addThreadMessages("SESSION TERMINATED");
       myDataSocket.sendMessage(disconnectSuccess);
       myDataSocket.close( );
       Thread.currentThread().interrupt();
   }
}