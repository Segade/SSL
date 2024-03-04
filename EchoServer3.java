import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.ssl.*;


/**
 * This module contains the application logic of an echo server
 * which uses a stream-mode socket for interprocess communication.
 * Unlike EchoServer2, this server services clients concurrently.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class EchoServer3 {
   public static void main(String[] args) {
      int serverPort = 1981;    // default port
      String message;
      String ksName = "herong.jks";
      char ksPass[] = "secaiv".toCharArray();
      char ctPass[] = "secaiv".toCharArray();


      if (args.length == 1 )
         serverPort = Integer.parseInt(args[0]);       
      try {
         KeyStore ks = KeyStore.getInstance("JKS");
         ks.load(new FileInputStream(ksName), ksPass);
		 
         KeyManagerFactory kmf = 
         KeyManagerFactory.getInstance("SunX509");
         kmf.init(ks, ctPass);
         SSLContext sc = SSLContext.getInstance("TLS");
         sc.init(kmf.getKeyManagers(), null, null);
         SSLServerSocketFactory ssf = sc.getServerSocketFactory();
         SSLServerSocket s 
            = (SSLServerSocket) ssf.createServerSocket(1981);
         // instantiates a stream socket for accepting
         //   connections
 
 
/**/     System.out.println("Echo server ready.");  
         while (true) {  // forever loop
            // wait to accept a connection 
/**/        System.out.println("Waiting for a connection.");
            MyStreamSocket myDataSocket = new MyStreamSocket
                ( (SSLSocket)s.accept() );
/**/        System.out.println("connection accepted");
            // Start a thread to handle this client's sesson
            Thread theThread = 
               new Thread(new EchoServerThread(myDataSocket));
            theThread.start();
            // and go on to the next client
            } //end while forever
       } // end try
	    catch (Exception ex) {
          ex.printStackTrace( );
	    } // end catch
   } //end main
} // end class
