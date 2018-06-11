package client;

import common.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AcceptClientFromClient implements Runnable {
	private FileManager fileManager = new FileManager();
    private Clients clients;
    private Socket clientSocket, clientSocket2;
    private FileHandler fileHandler;
    private int clientNumber;
    private DataInput dataInput;
    private Client recievedInfo;
    private String[] clientInfo;
    private InetAddress clientAddress;
    private DataOutput dataOutput;
    private String homePath;


    public AcceptClientFromClient(Socket clientSocket, int clientNo) throws Exception {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;

        this.fileHandler = new FileHandler();
        this.clients = new Clients();
        this.clients = fileHandler.readElements();
        this.homePath = fileManager.getHomePath();
    }

    
    public void run() {
        try {
            System.out.println("Client Nr " +clientNumber+ " is connected.");
            System.out.println("Socket is available for connection: "+ clientSocket);

            dataInput = new DataInput(clientSocket);


            // Receive the list from the client
            ArrayList<byte[]> data = new ArrayList<byte[]>();
            //String folderPath = homePath + "/JavaSocket";
                     
            ArrayList<String> wishList = dataInput.receiveList();
            for (String file: wishList) {
            	Path path = Paths.get(file);
            	byte[] bytes = Files.readAllBytes(path);
            	data.add(bytes);
            }
            
            
            // Send the files to the other client
            dataOutput = new DataOutput(clientSocket);
            dataOutput.sendObject(data);
            
            

//          ArrayList<byte[]> bytes = new ArrayList<byte[]>();
//        	Path path = Paths.get(name);    
//        	byte[] data = Files.readAllBytes(path);
//        	bytes.add(data);
            
            
            
            
//            dataOutput = new DataOutput(clientSocket);
//            dataOutput.sendObject(clients);

            //dataInput.receiveData();

            Thread.sleep(3000);

            //TODO: when the client is already connected, this message is still shown...
            System.out.println("End of connection to the client " + clientNumber);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
