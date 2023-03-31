package server;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
   private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> users = new ArrayList<>();
    private static HashMap<String,String> userLogins = new HashMap<>();
    ServerFrame frame;
    public void runServer(){
        this.frame = new ServerFrame();
        String ksName = "C:\\PeterProject\\server\\herong.jks";
        char[] ksPass = "password".toCharArray();
        char[] ctPass = "password".toCharArray();

       //instantiate a few dummy users
       userLogins.put("admin","admin");
       userLogins.put("user1","password1");
       userLogins.put("user2","password2");
       int serverPort = 40;

       try {
           //ServerSocket myConnectionSocket = new ServerSocket(serverPort);
           KeyStore keystore = KeyStore.getInstance("JKS");
           keystore.load(new FileInputStream(ksName),ksPass);

           KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
           keyManagerFactory.init(keystore,ctPass);

           TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
           trustManagerFactory.init(keystore);

           SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
           sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),null);

           SSLServerSocketFactory sslServerSocketFactory = sslContext.getServerSocketFactory();
           SSLServerSocket sslServerSocket = (SSLServerSocket) sslServerSocketFactory.createServerSocket(serverPort);
           sslServerSocket.setEnabledProtocols(new String[] {"TLSv1.2"});


           String[] ciphers = sslServerSocket.getSupportedCipherSuites();
           sslServerSocket.setEnabledCipherSuites(ciphers);

           this.frame.sendMessageToPanel("Echo server ready");
           while (true) {
               this.frame.sendMessageToPanel("Waiting for a connection");
               ServerStreamSocket myDataSocket = new ServerStreamSocket((SSLSocket) sslServerSocket.accept());
               this.frame.sendMessageToPanel("Connection Accepted");
               Thread thread = new Thread(new ServerThread(myDataSocket,this));
               thread.start();
           }
       }
       catch (Exception ex) {
           ex.printStackTrace( );
       }
   }
   public synchronized void userLoggedIn(String username){
       users.add(username);
   }
   public  synchronized void userLoggedOut(String username){
       users.remove(username);
   }
   public synchronized ArrayList<String> getUsers(){
       return users;
   }
   public synchronized ArrayList<String> getMessages(){
       return messages;
   }
   public synchronized HashMap<String,String> getUserLogins(){
       return userLogins;
   }
   public synchronized void addMessage(String message){
       messages.add(message);
   }

   public void addThreadMessages(String message){
       this.frame.sendMessageToPanel(message);
   }
}
