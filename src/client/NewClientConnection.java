package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import controller.DataOutput;


public class NewClientConnection {
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String clientPseudo;
	private String password;
	private PrintWriter pout;
	private DataOutput dataOutput;
	
	
	public NewClientConnection(String serverIP, String clientPseudo, String password) {
		this.serverIP = serverIP;
		this.clientPseudo = clientPseudo;
		this.password = password;
	}
	
	
	public void connectToServer() {
		try {		
			//TODO : scan des adresses IP qui écoutent sur le port 45000
			serverAddress = InetAddress.getByName(serverIP);
			System.out.println("I get the address of the server: " +serverAddress);
			
			// Ask the server to create a new socket
			clientSocket = new Socket(serverAddress, 45000);
			System.out.println(clientSocket);
			
			System.out.println("I got the connexion to " +serverAddress);

			// Give login information to the server
			dataOutput = new DataOutput(clientSocket);
			dataOutput.giveInformationToServer(clientPseudo, password);
			
			System.out.println("Now dying...");
			
			clientSocket.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
