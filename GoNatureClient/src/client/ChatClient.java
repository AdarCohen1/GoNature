// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import client.*;
import clientGUI.LoginController;
import clientGUI.NewReservationForGuideController;
import clientGUI.NewReservationForUserController;
import clientGUI.UserMenuController;
import common.ChatIF;
import logic.Order;
import ocsf.client.AbstractClient;

import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
@SuppressWarnings("unused")
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  public static Order o1 = new Order(null, null, null, null, null, null);
  public static boolean awaitResponse = false;

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
	 
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    //openConnection();
  }

  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public int type=0;
  public void handleMessageFromServer(Object msg) 
  {
	  System.out.println("--> handleMessageFromServer");
      System.out.println("ChatClient> Message received: " + (String)msg);
      String message = (String) msg.toString();
      System.out.println("chatClient> " + message);
      String[] result = message.split(" ");
	  awaitResponse = false;
	  switch (result[0]) {
		  case "loadOrder":
			  o1.setParkName(result[0]);
			  o1.setOrderNumber(result[1]);
			  o1.setTimeOfVisit(result[2]);
			  o1.setNumberOfVisitors(result[3]);
			  o1.setTelephoneNumber(result[4]);
			  o1.setEmail(result[5]);
			  break;
		  case "userExist":
			  if(result[1].equals("succeed")) {
				  LoginController.typeacc=result[3];
				  UserMenuController.type=result[3];
				  LoginController.isexist=true;
				  if(result[2].equals("1"))
					  LoginController.islogged=true;}
			  else LoginController.isexist=false;
			  break;
		  case "login":
			  if(result[1].equals("succeed")) {
				  if (result[2].equals("guide"))
					  type=1;
				  UserMenuController.username=result[2];
				  LoginController.isexist=true;}
			  else LoginController.isexist=false;
			  break;
		  case "logout":
			  if(result[1].equals("succeed")) {
				  UserMenuController.islogout=true;
			  	  if(type==1) type=0;}
			  else UserMenuController.islogout=false;
			  break;
		  case "parkNames":
			  if (type==1) {
				  for(int i=0; i<Integer.parseInt(result[1]);i++) {
					  NewReservationForGuideController.parknames.add(result[i+2]);}
			  }
			  else {	  
				  for(int i=0; i<Integer.parseInt(result[1]);i++)
					  NewReservationForUserController.parknames.add(result[i+2]);}
			  break;  
	  }
	  
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  
  public void handleMessageFromClientUI(String message)  
  {
    try
    {
    	openConnection();//in order to send more than one message
       	awaitResponse = true;
    	sendToServer(message);
		// wait for response
		while (awaitResponse) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }
    catch(IOException e)
    {
    	e.printStackTrace();
      clientUI.display("Could not send message to server: Terminating client."+ e);
      quit();
    }
  }

  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
