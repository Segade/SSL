import java.io.*;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class EchoClient2 {
   static final String endMessage = ".";
   public static void main(String[] args) {
         String message, echo;
boolean done = false;

      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      try {
         System.out.println("Welcome to the client.\n" );
          EchoClientHelper2 helper =             new EchoClientHelper2("localhost", "1981");

System.out.println("Enter your user name: ");
         String username = br.readLine();

          System.out.println("Enter your password: ");
         String password = br.readLine();
message = "login/username:" + username + "&password:" + password ;
 
echo = helper.getEcho(message);
String header = Functionality.getHeader(echo);
System.out.println(Functionality.getMessage(echo)) ;

if (header.equals("loggedin/")){
while(!done){
String option =displayMenu();

switch (option){
case "message/" :
message = "";
while(message.equals("")){
System.out.println("Enter your message: ");
message = br.readLine();
message = message.trim();

if (message.equals(""))
System.out.println("Empty messages are not allowed");
} // end while

echo = helper.getEcho("message/" + message); 
System.out.println(Functionality.getMessage(echo));
break;

case "logout/" :
System.out.println("You are logged out");
helper.done();
done = true;
break;

case "retrieveone/" :
int position = askPosition();
echo = helper.getEcho(option + position);
System.out.println("The message " + (position+1) + " is: " + Functionality.getMessage(echo) );
break;

case "retrieveall/":
 echo = helper.retrieveAllMessages(); 
System.out.println("The list of the messages is: " + echo );
} // end switch

} // end while 
  
}  // end if logged in
else {
System.out.println("Wrong user name or password");
helper.done();
} // end else logged in
  
      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } //end catch
   } //end main


private static String displayMenu() throws IOException{
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);

System.out.println("Press enter to send a message." +
"\nPress 1 to retrieve all the messages.\nPress 2 to retrieve one message." +
"\nPress 3 to log out.\n");
 
String option = br.readLine();

switch (option){
case "" : option = "message/";
break;

case "1" : option = "retrieveall/";
break;

case "2": option = "retrieveone/";

break;
case "3" : option = "logout/";
break;

default:
System.out.println("Wrong option.\nPlease choose a valid option");
} // end switch
 
return option;
} // end display menu


private static int askPosition() throws IOException{
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
String position = "a";

while (!Functionality.isInteger(position)){
System.out.println("Enter the number of the message you want to see (Numbers<= 0 are not allowed): ");
position = br.readLine();
} // end while

return Integer.parseInt(position) - 1;
} // end ask position 

   
} // end class
