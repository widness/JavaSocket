package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

import common.Client;
import common.ClientFile;
import common.ClientFiles;
import common.Clients;
import common.DataInput;
import common.DataOutput;
import common.FileHandler;
import common.FileManager;
import common.NetworkManager;



public class NewClientConnection {
    private NetworkManager networkManager = new NetworkManager();
    private FileHandler fileHandler = new FileHandler();
    private FileManager fileManager = new FileManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String clientPseudo;
	private String password;
	private DataOutput dataOutput;
	private String localIP;
	private int port;
	private String homePath;
	private DataInput dataInput;
	
    
	public NewClientConnection(String clientPseudo, String password, String serverIP, String networkInterface, int port) {
		this.clientPseudo = clientPseudo;
		this.password = password;
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
		this.homePath = fileManager.getHomePath();
	}
	
	
	// Get the connection to the server
	public void registerToServer() {
		try {
			serverAddress = InetAddress.getByName(serverIP);
			clientSocket = new Socket(serverAddress, port);
			System.out.println("I got the connexion to " + serverAddress);
			
			
			// Test if the share repository exists
			String folderPath = homePath + "/JavaSocket";
					
			if (fileManager.createRepository(folderPath)) {
				System.out.println("The repository has been created on your desktop.");
			}
			
			
			// Retrieve the files
			System.out.println("\nPlease add the files you want to share in the 'JavaSocket' repository on your desktop...");
			System.out.println("When you're ready, press the ENTER key.");				
			System.in.read();
			
			fileManager.retrieveListFiles(folderPath);
			String[] filesList = fileManager.getListFiles();
			
			
			// Give login information to the server
			dataOutput = new DataOutput(clientSocket);
			dataOutput.giveInformationToServer(clientPseudo, password, localIP, Integer.toString(port), filesList);

		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	
	public boolean acceptedByServer() {
        dataInput = new DataInput(clientSocket);
        Clients clients = dataInput.receiveClients();

        // Get the list of clients and save it into the files list, IPs list and ports list (at same time)
        for (Client c: clients.getClients()) {
            if (c.getPseudo().equals("false")) {
                System.out.println("user exist already and the password isn't the same");
                return false;
            }
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("couldn't close the socket");
            e.printStackTrace();
        }

        return true;
    }
	
	
	public void getFileList() {
		// Retrieve the list of clients and files from the server
		dataInput = new DataInput(clientSocket);
		Clients clients = dataInput.receiveClients();
		
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<String> ips = new ArrayList<String>();
		ArrayList<String> ports = new ArrayList<String>();
		
		
		// Get the list of clients and save it into the files list, IPs list and ports list (at same time)
		for (Client c: clients.getClients()) {
			//if (!c.getClientIP().equals(localIP)) {			// in comments for testing, don't delete this line !!
		        for (String s: c.getFiles()) {
		            files.add(s);
		            ips.add(c.getClientIP());
		            ports.add(c.getClientPort());
		        }
			//}
		}
		
		
		// Print the files list
		for (int i = 0; i < files.size(); i++) {
			int startIndex = files.get(i).lastIndexOf('\\') + 1;
			int endIndex = files.get(i).length();
			String filename = files.get(i).substring(startIndex, endIndex);
			
		    System.out.println(i + ": " + filename);
		}
		     
		
		// Save the client's choice
		System.out.println("Which file do you want to download?");
		System.out.println("Type the number of the file you want to choose and press ENTER key to validate.");
		System.out.println("To send your request to the server, type -1 and press ENTER key.");
		
		Scanner scan = new Scanner(System.in);
		ClientFiles choices = new ClientFiles();
		ClientFile targetedFile;
		int fileNumber;
		boolean request = false;
		
		// Search for a file with the index typed by the client and add it to the list of choices            
		while (request == false) {
			if (scan.hasNextInt()) {
			    fileNumber = scan.nextInt();
			    
			    if (fileNumber >= 0) {
			    	try {
			    		System.out.println(files.get(fileNumber)); 
		            	
		                targetedFile = new ClientFile(files.get(fileNumber),
		                        ips.get(fileNumber),
		                        ports.get(fileNumber));
		                
		                choices.addFile(targetedFile);
			    	} catch (ArrayIndexOutOfBoundsException e) { 
		            	// Too big number
		                System.out.println("Can't find this file, please try again.");
		            }
			    }
			    else {
			    	request = true;
			    }
			}
			else {
				System.out.println("Please correct your answer: you can only type numbers.");
				break;
			}
		}
		
		
		// Separate all the different IP addresses
		ArrayList<ClientFile> choosenFiles = new ArrayList<ClientFile>();
		choosenFiles = choices.getClientFiles();
		
		ArrayList<String> allIP = new ArrayList<String>();
		
		try {
			for (ClientFile cf: choosenFiles) {
				JSONObject object = new JSONObject(cf);
				
				String ip = object.getString("ip");
				System.out.println(cf);
				
				if(!IPexists(ip, allIP)) {
					allIP.add(ip);
				}           		
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		// Create a list of files for each IP address
		for (String ip: allIP) {
			ArrayList<String> names = new ArrayList<String>();             
		    int port = 0;
		    
		    try {
		    	for (ClientFile cf: choosenFiles) {
		    		JSONObject object = new JSONObject(cf);
		    		String testIP = object.getString("ip");
		    		
		    		if (testIP.equals(ip)) {
		    			port = object.getInt("port");
		    			
		    			String name = object.getString("name");           	
		            	names.add(name);
		    		}
		    	}               	
		    } catch (JSONException e) {
				e.printStackTrace();
			}
		    
		    
		    // Request the list of files to the host client
		    NewClientConnection nc = new NewClientConnection("default", "default", ip, "wlan1", 45001);
		    nc.connectToHost(ip, port, names);
		    
		    
//		    System.out.println(ip);
//		    System.out.println(port);
//		    for(int i=0; i<names.size(); i++) {
//		    	System.out.println(names.get(i));
//		    }

		   
		    
		}
	}
	
	
	// Get the connection to the server
	public void connectToHost(String ip, int port, ArrayList<String> names) {
		try {
			serverAddress = InetAddress.getByName(ip);
			clientSocket = new Socket(serverAddress, port);
			System.out.println("I got the connexion to " + serverAddress);
			
			
			// Test if the download repository exists
			String folderPath = homePath + "/DownloadsP2P";
					
			if (fileManager.createRepository(folderPath)) {
				System.out.println("The repository has been created on your desktop.");
			}
			
			
			// Send the wish list
			DataOutput dout = new DataOutput(clientSocket);
			dout.sendObject(names);
			
			
			// Download the files
			DataInput din = new DataInput(clientSocket);
			din.receiveData(folderPath);
			
		} catch(UnknownHostException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}


	// Check it the IP already exists in the list
	private boolean IPexists(String ip, ArrayList<String> allIP) {
		boolean test = false;
		
		for (int i = 0; i < allIP.size(); i++) {
			if (ip.equals(allIP.get(i))) {
				test = true;
			}
		}
				
		return test;
	}
}
