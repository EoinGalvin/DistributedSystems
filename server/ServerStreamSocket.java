package server;

import javax.net.ssl.SSLSocket;
import java.io.*;
import java.net.Socket;
public class ServerStreamSocket extends Socket {
   private SSLSocket socket;
   private BufferedReader input;
   private PrintWriter output;

   ServerStreamSocket(SSLSocket socket)  throws IOException {
      this.socket = socket;
      setStreams( );
   }

   private void setStreams( ) throws IOException{
      // get an input stream for reading from the data socket
      InputStream inStream = socket.getInputStream();
      input = new BufferedReader(new InputStreamReader(inStream));
      OutputStream outStream = socket.getOutputStream();
      // create a PrinterWriter object for character-mode output
      output = new PrintWriter(new OutputStreamWriter(outStream));
   }

   public void sendMessage(String message) {
      output.print(message + "\n");
      output.flush();
   }
   public String receiveMessage( ) throws IOException {
      return input.readLine();
   }

   public void close( )
		throws IOException {
      socket.close( );
   }
}
