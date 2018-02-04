import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JTextArea;


public class BackendClient {

	//Need to add input,output and socket as global variables to class. That way all methods can access them.
	Socket clientSocket = null;
	Scanner input = null;
	PrintWriter output = null;
	String fromServer;
	
	
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
	//Client side connection, input,output and socket opened here
	public boolean connect(String ip,int portNum) {
		try {
			clientSocket = new Socket(ip,portNum);
			output = new PrintWriter(clientSocket.getOutputStream(),true);
			input = new Scanner(new InputStreamReader(clientSocket.getInputStream()));
		}catch(IOException err){
			System.out.println(err);
			return false;
		}catch(Exception err) {
			System.out.println(err);
			return false;
		}
		return true;
	}
	
	public boolean send(JTextArea userToServer, JTextArea replyField) {
		try {
			if(userToServer != null) {
				output.println(userToServer.getText());
				//userToServer.write(output);
				
				/*
				while( input.hasNextLine()) {
					fromServer = input.nextLine();
					replyField.setText(replyField.getText() + fromServer);
				}
				*/
				
				return true;
			}
		}catch(Exception err) {
			return false;
		}
		return false;
	}
	
	public boolean send(String isbn, JTextArea userToServer, JTextArea replyField) { //overload for when ther is ISBN
		try {
			if(userToServer != null) {
				output.println(userToServer.getText() + "\nISBN " + isbn); //send ISBN
				
				/*
				while( input.hasNextLine()) {
					fromServer = input.nextLine();
					replyField.setText(replyField.getText() + " \n" + fromServer);
				}
				*/
				
				
				return true;
			}
		}catch(Exception err) {
			return false;
		}
		
		return false;
	}
	

}
