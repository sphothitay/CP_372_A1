import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JTextArea;


public class BackendClient {

	//Need to add input,output and socket as global variables to class. That way all methods can access them.
	Socket clientSocket = null;
	Scanner input = null;
	PrintWriter output = null;
	String inputLine, outputLine;
	
	
	public void disconnect(){
		try {
			input.close();
			output.close();
			clientSocket.close();
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
	
	public boolean send(JTextArea userToServer) {
		try {
			if(userToServer != null) {
				userToServer.write(output);
				return true;
			}
		}catch(Exception err) {
			return false;
		}
		return false;
	}
	
	public boolean send(String isbn, JTextArea userToServer) { //overload for when ther is ISBN
		try {
			if(userToServer != null) {
				output.println(isbn); //send ISBN
				userToServer.write(output); //send rest of request
				return true;
			}
		}catch(Exception err) {
			return false;
		}
		
		return false;
	}
	

}
