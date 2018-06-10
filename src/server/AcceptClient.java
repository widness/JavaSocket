package server;

import java.io.IOException;
import java.net.*;

import common.*;
import common.Client;


public class AcceptClient implements Runnable {
    private Clients clients;
    private Socket clientSocket, clientSocket2;
    private FileHandler fileHandler;
    private int clientNumber;
    private DataInput dataInput;
    private Client recievedInfo;
    private String[] clientInfo;
    private InetAddress clientAddress;
    private DataOutput dataOutput;

    
    public AcceptClient(Socket clientSocket, int clientNo) throws Exception {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;

        this.fileHandler = new FileHandler();
        this.clients = new Clients();
        this.clients = fileHandler.readElements();
    }
    
    
    public void run() {
        try {
            System.out.println("Client Nr " +clientNumber+ " is connected.");
            System.out.println("Socket is available for connection: "+ clientSocket);

            dataInput = new DataInput(clientSocket);

            // recievedInfo = dataInput.receiveArrayListFromClient();
            Client client = dataInput.receiveClient();

            // 0: pseudo | 1: password | 2: clientIP | 3: port | 4: fileList
            if (clients.isClient(client.getPseudo())) {
                if (clients.isPwdCorrect(client.getPseudo(), client.getPassword())) {
                    clients.updateClient(client);
                    fileHandler.writeElement(clients);
                }
            } else {
                clients.addNewClient(client);
                fileHandler.writeElement(clients);
            }

            clientAddress = InetAddress.getByName(client.getClientIP());

            dataOutput = new DataOutput(clientSocket);
            dataOutput.sendObject(clients);

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
