import java.io.*;
import java.util.ArrayList;
/**
 * This module is to be used with a concurrent Echo server.
 * Its run method carries out the logic of a client session.
 * @author M. L. Liu
 */

class EchoServerThread implements Runnable {
   static final String endMessage = ".";
   MyStreamSocket myDataSocket;
String users[] = new String[10];
ArrayList<String> allMessages = new ArrayList<String>();

   EchoServerThread(MyStreamSocket myDataSocket) {
      this.myDataSocket = myDataSocket;
users[0] ="username:ivan&password:aaa";
users[1] = "username:peter&password:1234";
 
   } // end constructor 
 
   public void run( ) {
      boolean done = false;
      String message;
String header;
String username = "-----";

      try {

         while (!done) {
message =myDataSocket.receiveMessage( );
header = Functionality.getHeader(message);

switch(header){
case "login/":
 if (!findUser(message.trim()) ){
System.out.println("fail/Wrong username or password");
                myDataSocket.sendMessage("fail/Wrong credentials");
                myDataSocket.close( );
 done = true;
} else {
username = Functionality.getUsername(message);
System.out.println("The user : " + username + " is logged in");
                 myDataSocket.sendMessage("loggedin/You are logged in");
}
break;

case "message/" :
message = Functionality.getMessage(message);
allMessages.add(message);
System.out.println("Message received from " + username + "\n" + message);
                 myDataSocket.sendMessage("received/Message " + allMessages.size() + " sent" );
break;

case "logout/":
System.out.println("The user " + username + " is logged out");
myDataSocket.close();
done = true;
break;

case "retrieveall/":
String messages = sendAllMessages();
System.out.println("The list of the messages of" + username + " is: " + messages);
                 myDataSocket.sendMessage( messages);
break;


case "retrieveone/" :
int position = Integer.parseInt(Functionality.getMessage(message));

if (position >= allMessages.size() )
message = "Not such message found";
else 
message = allMessages.get(position);

                 myDataSocket.sendMessage("onemessage/" + message);
System.out.println("The user " + username + " requested the message:\n" + position + ". " + message );

} // end switch  
 
          } //end while !done

        }// end try
        catch (Exception ex) {
           System.out.println("Exception caught in thread: " + ex);
        } // end catch
   } //end run




private boolean findUser(String message){
String credentials = message.substring(6);
boolean pass = false;  
for (String user : users){
if ( credentials.equals(user))
pass = true;
 
} // end for 
 
 return pass;
} // end find user 




private String sendAllMessages(){
String result = "";
String header = "\namessage/";

if (allMessages.size() == 0)
result = "\nlastmessage/Empty list";
else 
{
for (int x = 0 ; x< allMessages.size() ;x++){

if (x == allMessages.size() - 1)
header = "\nlastmessage/" ;

result += header + (x+1) + ". " + allMessages.get(x);

} // end for 
 } // end else 
return result;
} // end send all messages


} //end class 
