package client;

import common.*;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class AcceptClientFromClient implements Runnable {
	private FileManager fileManager = new FileManager();
    private Clients clients;
    private Socket clientSocket;
    private ClientListManager fileHandler;
    private int clientNumber;
    private DataInput dataInput;
    private DataOutput dataOutput;
    private String homePath;


    public AcceptClientFromClient(Socket clientSocket, int clientNo) throws Exception {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;

        this.fileHandler = new ClientListManager();
        this.clients = new Clients();
        this.clients = fileHandler.readElements();
        this.homePath = fileManager.getHomePath();
    }

    
    // Run the server
    public void run() {
        try {
            System.out.println("Client Nr " +clientNumber+ " is connected.");
            System.out.println("Socket is available for connection: "+ clientSocket);

            dataInput = new DataInput(clientSocket);


            // Receive the list of names from the client (wish list of files)
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<byte[]> data = new ArrayList<byte[]>();
                     
            ArrayList<String> wishList = dataInput.receiveList();
            for (String file: wishList) {
            	Path path = Paths.get(homePath + "\\JavaSocket\\" +file);
            	byte[] bytes = Files.readAllBytes(path);
            	names.add(file);
            	data.add(bytes);
            }
            
            DownloadFiles dl = new DownloadFiles(names, data);
            
                   
            // Send the files to the other client
            dataOutput = new DataOutput(clientSocket);
            dataOutput.sendDownloadFiles(dl);
            
            
            
            
            

            Thread.sleep(3000);

            //TODO: when the client is connected, this message is still shown...
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
