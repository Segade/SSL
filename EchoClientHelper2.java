import java.net.*;
import java.io.*;

/**
 * This class is a module which provides the application logic
 * for an Echo client using stream-mode socket.
 * @author M. L. Liu
 */

public class EchoClientHelper2 {

   static final String endMessage = "logout/";
   private MyStreamSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;

   EchoClientHelper2(String hostName,
                     String portNum) throws SocketException,
                     UnknownHostException, IOException {
                                     
  	   this.serverHost = InetAddress.getByName(hostName);
  		this.serverPort = Integer.parseInt(portNum);
      //Instantiates a stream-mode socket and wait for a connection.
   	this.mySocket = new MyStreamSocket(this.serverHost,
         this.serverPort); 
/**/  System.out.println("Connection request made");
   } // end constructor
	
   public String getEcho( String message) throws SocketException,
      IOException{     
      String echo = "";    
      mySocket.sendMessage( message);
	   // now receive the echo
      echo = mySocket.receiveMessage();
      return echo;
   } // end getEcho

   public void done( ) throws SocketException,
                              IOException{
      mySocket.sendMessage(endMessage);
      mySocket.close( );
   } // end done 


   public String retrieveAllMessages() throws SocketException, IOException{
String result = "";
String header = "-";
String message = "";

      mySocket.sendMessage("retrieveall/retrieve all the messages");

while (!header.equals("lastmessage/")) {
message = mySocket.receiveMessage();
header = Functionality.getHeader(message);

result += Functionality.getMessage(message) + "\n";
} // end while 


return result;
} // end retrieve all messages 



} //end class
