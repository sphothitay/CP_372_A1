import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JTextArea;
/**
 * 
 * @author Harjodh Singh, Simon Photitay, Group 49
 * Email: sing4262@mylaurier.ca
 * @version 1.1
 * Date Feb 1st 2018
 * 
 * Backend of the GUI, GUI will use backendclient object to perform actions
 * Connect, disconnect, send, send 
 * 
 */


public class BackendClient {

	//Need to add input,output and socket as global variables to class. That way all methods can access them.
	Socket clientSocket = null;
	Scanner input = null; //will utilize scanner not buffer
	PrintWriter output = null;
	String fromServer;
	

	//Client side connection, input,output and socket opened here
	public boolean connect(String ip,int portNum) {
		try {
			clientSocket = new Socket(ip,portNum);
			output = new PrintWriter(clientSocket.getOutputStream(),true);
			input = new Scanner(clientSocket.getInputStream());
		}catch(IOException err){
			System.out.println(err);
			return false;
		}catch(Exception err) {
			System.out.println(err);
			return false;
		}
		return true;
	}
	
	public void send(JTextArea userToServer, JTextArea replyField) {
		try {
			if(!(userToServer.getText().equals(""))) {
				output.println(userToServer.getText());
			
				while(!(fromServer = input.nextLine()).equals("Done.\n")) {
					//fromServer = input.nextLine();
					if(fromServer.equals("Done.")) {
						break;
					}
					replyField.setText(replyField.getText() +fromServer+"\n");
				}	
			}
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	public void send(String isbn, JTextArea userToServer, JTextArea replyField) { //overload for when there is ISBN
		try {
			if(userToServer != null) {
				output.println(userToServer.getText() + "\nISBN " + isbn); //send ISBN
				
				/*
				while( input.hasNextLine()) {
					fromServer = input.nextLine();
					replyField.setText(replyField.getText() + " \n" + fromServer);
				}
				*/
			
			}
		}catch(Exception err) {
			err.printStackTrace();
		}
	}
	
	/*
	 * disconnect to handle all clean up as streams stay open for length of connected session
	 */
	public void disconnect(){
		try {
			input.close();
			output.close();
			clientSocket.close();
			System.out.println("Client offline");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
