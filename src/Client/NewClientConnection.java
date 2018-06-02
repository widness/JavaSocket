package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.filechooser.FileSystemView;

import common.DataOutput;
import common.NetworkManager;


public class NewClientConnection {
    private NetworkManager networkManager = new NetworkManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String clientPseudo;
	private String password;
	private PrintWriter pout;
	private DataOutput dataOutput;
	private String localIP;
	private int port;
	
	
	public NewClientConnection(String clientPseudo, String password, String serverIP, String networkInterface, int port) {
		this.clientPseudo = clientPseudo;
		this.password = password;
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
	}
	
	
	public void connectToServer() {
		try {
			serverAddress = InetAddress.getByName(serverIP);
			System.out.println("I get the address of the server: " + serverAddress);
			
			// Ask the server to create a new socket
			clientSocket = new Socket(serverAddress, port);
			System.out.println(clientSocket);
			
			System.out.println("I got the connexion to " + serverAddress);
			
			// Give login information to the server
			dataOutput = new DataOutput(clientSocket);
			dataOutput.giveInformationToServer(clientPseudo, password, localIP, Integer.toString(port));

			String folder = createRepository();
			shareRepository(folder);
			
			System.out.println("Now dying...");
			
			clientSocket.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private String createRepository() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path = home.getAbsolutePath();
		
		File folder = new File(path + "/JavaSocket");
		if (!folder.exists())
			folder.mkdir();
		
		return (path + "/JavaSocket");
	}
	
	
	private void shareRepository(String path) {
		File folder = new File(path);
		File[] listFiles = folder.listFiles();
		
		
		
		
		
		
		
//		FileInputStream in = new FileInputStream(fichier);
//		BufferedInputStream bin = new BufferedInputStream(in);
//		DataInputStream dbin = new DataInputStream(bin);
//		System.out.println(dbin.readInt());
//		dbin.close();
		
	}
}
