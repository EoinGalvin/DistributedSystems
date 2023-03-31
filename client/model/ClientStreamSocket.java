package client.model;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientStreamSocket extends Socket {
   private SSLSocket  socket;
   private BufferedReader input;
   private PrintWriter output;

   ClientStreamSocket(InetAddress acceptorHost, int acceptorPort ) throws SocketException, IOException{
      try{
         String trustStoreLocationAsString = System.getProperty("user.dir") + "\\client\\model\\public.jks";

         System.setProperty("javax.net.ssl.trustStore", trustStoreLocationAsString);
         System.setProperty( "javax.net.ssl.trustStorePassword", "password");

         SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
         socket = (SSLSocket) sslSocketFactory.createSocket(acceptorHost,acceptorPort);

         socket.setEnabledProtocols(new String[] {"TLSv1.2"});
         String[] ciphers = socket.getSupportedCipherSuites();
         socket.setEnabledCipherSuites(ciphers);

         socket.startHandshake();
         setStreams();

         JOptionPane.showMessageDialog(null,"Connection Accepted");
      }catch(ConnectException ex){
         JOptionPane.showMessageDialog(null,"Connection refused");
      }
   }
   private void setStreams() throws IOException{
      InputStream inStream = socket.getInputStream();
      input = new BufferedReader(new InputStreamReader(inStream));
      OutputStream outStream = socket.getOutputStream();
      output = new PrintWriter(new OutputStreamWriter(outStream));
   }
   public void sendMessage(String message) throws IOException {
      output.print(message + "\n");
      output.flush();               
   }
   public String receiveMessage() throws IOException {
      return input.readLine();
   }
   public void disconnect()
		throws IOException {	
      socket.close();
   }
} //end class
