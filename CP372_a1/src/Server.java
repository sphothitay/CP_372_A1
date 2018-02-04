import java.net.*;
//import java.util.HashSet;
//import java.util.Scanner;
//import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
    	if (args.length < 1) {
    		System.err.println("Usage: java Sever <port number>");
    		System.exit(1);
    	}
    	
    	int portNum = Integer.parseInt(args[0]);
    	ExecutorService executor = null;
     	
    	// Create a new ServerSocket object using port 4444
        try (ServerSocket serverSocket = new ServerSocket(portNum);){
        	executor = Executors.newFixedThreadPool(5);
        	System.out.println("Waiting for clients");
            while (true) {
            	Socket clientSocket = serverSocket.accept();
            	Runnable worker = new RequestHandler(clientSocket);
            	executor.execute(worker);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portNum + ".");
            System.out.println(e.getMessage());
        } finally {
        	if (executor != null) {
        		executor.shutdown();
        	}
        }
        
//        Set<BibEntry> bibList = new HashSet<BibEntry>();
//        
//        // OLD INPUT OUTPUT STREAM IMPLEMENTATION, SHOULD USE SCANNER
//        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//        Scanner in = new Scanner(clientSocket.getInputStream());
//        //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//        String inputLine = null;
//		String outputLine = null;
//        Protocol p = new Protocol();
//        
//        // Use scanner.hasnextline
//        // Have to use dataoutputstream
//        outputLine = p.processInput(null, bibList);
//        out.println(outputLine);
//
//        synchronized (inputLine) {
//	        while ((inputLine = in.nextLine()) != null) { // Instead of readline use scanner has next line
//	             outputLine = p.processInput(inputLine, bibList); 
//	             out.println(outputLine);
//	             if (outputLine.equals("Done."))
//	                break;
//	        }
//        }
        
//        out.close();
//        in.close();
    }
}