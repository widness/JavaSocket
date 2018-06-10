package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.filechooser.FileSystemView;
import org.json.JSONException;
import org.json.JSONObject;
import common.*;


public class NewClientConnection {
    private NetworkManager networkManager = new NetworkManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private InetAddress otherClientAddress;
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
			
			// Test if the share repository exists and retrieve the files
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

            // Get the list of clients and save it into the choicesList, ipList and portList (at same time)
            for (Client c: clients.getClients()) {
            	//if (!c.getClientIP().equals(localIP)) {
                    for (String s: c.getFiles()) {
                        choicesList.add(s);
                        ipList.add(c.getClientIP());
                        portList.add(c.getClientPort());
                    }
            	//}
            }

            // Print the choicesList
            for (int i = 0; i < choicesList.size(); i++) {
            	int startIndex = choicesList.get(i).lastIndexOf('\\') + 1;
            	int endIndex = choicesList.get(i).length();
            	String filename = choicesList.get(i).substring(startIndex, endIndex);
            	
                System.out.println(i + ": " + filename);
            }

            System.out.println("Which file do you want to download?");
            System.out.println("Type the number of the file you want to choose and press ENTER key to validate.");
            System.out.println("To send your request to the server, type -1 and press ENTER key.");

            ClientFiles choosenList = new ClientFiles();
            ClientFile targetedFile;
            int fileNumber = 0;
            Scanner scan = new Scanner(System.in);
            
            // Search for a file with the index typed by the client and add it to the list to send
            while (fileNumber >= 0) {
                fileNumber = scan.nextInt();

                if (fileNumber >= 0) {
                    try {
                    	System.out.println(choicesList.get(fileNumber)); 
                    	
                        targetedFile = new ClientFile(choicesList.get(fileNumber),
                                ipList.get(fileNumber),
                                portList.get(fileNumber));
                        
                        choosenList.addFile(targetedFile);
                    } catch (ArrayIndexOutOfBoundsException e) { 
                    	// Too big number
                        System.out.println("Can't find the file, try again.");
                    }
                }
             }
                  
           
 
            
            // TODO: are you the host client or the client who wants to download files?
            // TODO: request for files to the host client
            ArrayList<ClientFile> test = new ArrayList<ClientFile>();
            test = choosenList.getClientFiles();
            
            for (int i = 0; i < test.size(); i++) {
            	System.out.println(test.get(i).toString());
            }
            
            
            // Créer un "File" pour chaque élément de la liste
            // Convertir les "File" en array de bytes
            // Transférer ces bytes à l'autre client
            // L'autre client reconvertit les bytes pour les lire
            
            // File{name='C:\Users\Montaine\Desktop\JavaSocket\LEFRIC 3 juillet.jpg', ip='fe80:0:0:0:78d4:e1bc:ec81:d84e%wlan1', port='45000'}
            
            try { 
            	for (ClientFile cf: test) {
            		JSONObject object = new JSONObject(cf);
                	
                	String name = object.getJSONObject("name").toString();
                	Path path = Paths.get(name);
                	String ip = object.getJSONObject("ip").toString();
                	int port = Integer.parseInt(object.getJSONObject("port").toString());
                	
                	byte[] data = Files.readAllBytes(path);
                	System.out.println(data);
            	}           		
            } catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            	
            	
            	
            

            
            
            
            
            
            System.out.println("Log out from the server.");
            clientSocket.close();
            scan.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// Retrieve the home path of the client
	private String getHomePath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path = home.getAbsolutePath() + "/JavaSocket";

		return path;
	}
	
	
	// Method to create a new repository on the desktop
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
