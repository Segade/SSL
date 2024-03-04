public class Functionality{

public static String getHeader(String message){
int indexOfSlash = message.indexOf('/') + 1;
return message.substring(0, indexOfSlash);
} // end get header 

public static String getMessage(String message){
return message.substring(message.indexOf("/") + 1);
} // end get message 



public static boolean isInteger(String number) {
        try {
int numberInt = Integer.parseInt(number);
if (numberInt <= 0) 
return false;

            return true;
        } catch (NumberFormatException e) {
            return false;
        }
} // end is integer 


public static String getUsername(String credentials){
return credentials.substring(credentials.indexOf(":") + 1, credentials.indexOf("&"));
} // end get username 


} // end class 