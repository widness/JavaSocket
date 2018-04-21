package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class NewClientConnection {
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String clientName;
	
	public NewClientConnection(String serverIP, String clientName) {
		this.serverIP = serverIP;
		this.clientName = clientName;
	}
	
	public void connectToServer() {
		try {
			System.out.println("Hello.");
			
			//TODO : scan des adresses IP qui écoutent sur le port 45000
			serverAddress = InetAddress.getByName(serverIP);
			System.out.println("I get the address of the server: " +serverAddress);
			
			// Ask the server to create a new socket
			clientSocket = new Socket(serverAddress, 45000);
			System.out.println(clientSocket);
			
			System.out.println("I got the connexion to " +serverAddress);
			System.out.println("Now dying...");
			
			clientSocket.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
