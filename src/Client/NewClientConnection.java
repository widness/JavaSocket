package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileSystemView;

import common.*;


public class NewClientConnection {
    private NetworkManager networkManager = new NetworkManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private InetAddress OtherClientAddress;
	private String serverIP;
	private String clientPseudo;
	private String password;
    private DataInput dataInput;
	private PrintWriter pout;
	private DataOutput dataOutput;
	private String localIP;
	private int port;
	private String folderPath;
    private Scanner scan;
	
	public NewClientConnection(String clientPseudo, String password, String serverIP, String networkInterface, int port) {
		this.clientPseudo = clientPseudo;
		this.password = password;
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
		this.folderPath = getHomePath();
		scan = new Scanner(System.in);
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

            ArrayList<String> choicesList = new ArrayList<String>();
            ArrayList<String> ipList = new ArrayList<String>();
            ArrayList<String> portList = new ArrayList<String>();

            // Get all list and save it into the choicesList | ipList and portList
            for(Client c: clients.getClients()) {
                for(String s: c.getFiles()) {
                    choicesList.add(s);
                    ipList.add(c.getClientIP());
                    portList.add(c.getClientPort());
                }
            }

            // Print the choicesList
            for(int i = 0; i < choicesList.size(); i++) {
                System.out.println(i + ": " + choicesList.get(i));
            }

            System.out.println("Wich file did you want to download?");
            System.out.println("Number: choose file");
            System.out.println("Enter: Validate the choosen file");
            System.out.println("-1: Send the request");

            ClientFiles choosenList = new ClientFiles();
            ClientFile targetedFile;
            int fileNumber = 0;
            // Choose white file index and add it too the list to send
            while(fileNumber >= 0){
                fileNumber = this.scan.nextInt();

                if(fileNumber >= 0){
                    try{
                        targetedFile = new ClientFile(choicesList.get(fileNumber),
                                ipList.get(fileNumber),
                                portList.get(fileNumber));
                        choosenList.addFile(targetedFile);
                    } catch (ArrayIndexOutOfBoundsException e) { // Choosen a to big number
                        System.out.println("Couldn't find the file, try again");
                    }
                }

              }
            System.out.println("I'm disconnecting from the server.");
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
