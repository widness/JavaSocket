package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.filechooser.FileSystemView;

import common.*;
import model.Client;


public class NewClientConnection {
    private NetworkManager networkManager = new NetworkManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String clientPseudo;
	private String password;
    private DataInput dataInput;
	private PrintWriter pout;
	private DataOutput dataOutput;
	private String localIP;
	private int port;
	private String folderPath;
	
	
	public NewClientConnection(String clientPseudo, String password, String serverIP, String networkInterface, int port) {
		this.clientPseudo = clientPseudo;
		this.password = password;
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
		this.folderPath = getHomePath();
	}
	
	
	public void connectToServer() {
		try {
			serverAddress = InetAddress.getByName(serverIP);
			System.out.println("I get the address of the server: " + serverAddress);
			
			// Ask the server to create a new socket
			clientSocket = new Socket(serverAddress, port);
			System.out.println(clientSocket);
			
			System.out.println("I got the connexion to " + serverAddress);
			
			if (createRepository()) {
				System.out.println("Please add the files you want to share in the 'JavaSocket' repository on your desktop...");
				System.out.println("When you're ready, press the ENTER key.");				
				System.in.read();
			}
			
			FileHandler fileHandler = new FileHandler();
			fileHandler.retrieveListFiles(folderPath);
			String[] filesList = fileHandler.getListFiles();
			
			// Give login information to the server
			dataOutput = new DataOutput(clientSocket);
			dataOutput.giveInformationToServer(clientPseudo, password, localIP, Integer.toString(port), filesList);

            dataInput = new DataInput(clientSocket);
            Clients clients = dataInput.receiveClients();

            System.out.println(clients.getClients().get(0).getPseudo());

			System.out.println("Now dying...");
			
			clientSocket.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String getHomePath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path = home.getAbsolutePath() + "/JavaSocket";

		return path;
	}
	
	
	private boolean createRepository() {			
		File folder = new File(folderPath);

		if (!folder.exists()) {
            System.out.println("I create a new File");
			folder.mkdir();
			return true;
		}
		
		return false;
	}
	
		
}
