import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Scanner;

public class RequestHandler implements Runnable {

	private final Socket client;
	ServerSocket serverSocket = null;

	public RequestHandler(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		try (PrintWriter out = new PrintWriter(client.getOutputStream(), true); BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));) {
			System.out.println("Thread started with name: " + Thread.currentThread().getName());
			String inputLine = null;
			
//			synchronized (inputLine) {
		        while ((inputLine = in.readLine()) != null) { // Instead of readline use scanner has next line
//		             outputLine = p.processInput(inputLine, bibList); 
//		             out.println(outputLine);
//		             if (outputLine.equals("Done."))
//		                break;
		        	inputLine.replaceAll("A-Za-z0-9 ]",  "");
		        	System.out.println("Received Message from " + Thread.currentThread().getName() + " : " + inputLine);
		        	out.write("You entered : " + inputLine + "\n");
		        	out.flush();
		        }
//	        }
		} catch (IOException e) {
			System.out.println("I/O exception " + e);
		} catch (Exception ex) {
			System.out.println("Exception in Thread Run. Exception : " + ex);
		}

	}

}
