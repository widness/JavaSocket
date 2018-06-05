package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import common.*;
import model.Client;


public class AcceptClient implements Runnable {
    private Clients clients;
    private Files files = new Files();
    private Socket clientSocket;
    private FileHandler fileHandler;
    private int clientNumber;
    private DataInput dataInput;
    private Client recievedInfos;
    private String[] clientInfos;
    private InetAddress clientAddress;
    private DataOutput dataOutput;

    public AcceptClient (Socket clientSocket, int clientNo) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;
        try {
            this.clients = fileHandler.readElements();
        } catch (Exception e) {
            System.out.println("I create a new client list");
            this.clients = new Clients();
        }

    }

    public void run() {
        try {
            System.out.println("Client Nr "+clientNumber+ " is connected");
            System.out.println("Socket is available for connection"+ clientSocket);

            dataInput = new DataInput(clientSocket);

            //recievedInfo = dataInput.receiveArrayListFromClient();
            Client client = dataInput.receiveClient();

            //0: Pseudo | 1: Password | 2: clientIP | 3: Port | 4: fileList
            if (clients.isClient(client.getPseudo())) {
                if (clients.isPwdCorrect(client.getPseudo(), client.getPassword())) {
                    clients.updateClient(client);
                    fileHandler.writeElement(clients);
                }
            } else {
                clients.addNewClient(client);
                fileHandler.writeElement(clients);
            }

            clientAddress = InetAddress.getByName(clientInfos[2]);
            dataOutput.giveClientList(clients, files);

            clientSocket.close();
            Thread.sleep(3000);
            System.out.println("End of connection to the client " + clientNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
