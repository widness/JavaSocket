package client;

import common.FileHandler;
import common.NetworkManager;


public class NewGuestConnection {
	private NetworkManager networkManager = new NetworkManager();
	private FileHandler fileHandler = new FileHandler();
	private String serverIP;
	private String localIP;
	private int port;
	private String homePath;
	

	public NewGuestConnection(String serverIP, String networkInterface, int port) {
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
		this.homePath = fileHandler.getHomePath();
	}
	
	
	//public void 

}
