package server;

import java.io.IOException;
import java.net.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.*;
import common.Client;


public class AcceptClient implements Runnable {
    private Clients clients;
    private Socket clientSocket;
    private ClientListManager clientListManager;
    private int clientNumber;
    private DataInput dataInput;
    private InetAddress clientAddress;
    private DataOutput dataOutput;
    private Logger logger;
    private FileHandler fh;
    private DateManager dateManager;
    
    
    public AcceptClient(Socket clientSocket, int clientNo) throws Exception {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;
        this.clientListManager = new ClientListManager();
        this.clients = new Clients();
        this.clients = clientListManager.readElements();
        this.dateManager = new DateManager();
        
        // Logger
        logger = Logger.getLogger("acceptClient");

        try{
            fh = new FileHandler("./logger/connection" + dateManager.getMonth() + ".log", true);
        } catch (Exception e) { 
        	// If the month's log doesn't, creates a new one
            fh = new FileHandler("./logger/connection" + dateManager.getMonth() + ".log");
        }

        logger.addHandler(fh);
    }
    
    
    // Start the server
    public void run() {
        try {
            System.out.println("Client Nr " +clientNumber+ " is connected.");
            System.out.println("Socket is available for connection: "+ clientSocket);

            dataInput = new DataInput(clientSocket);

            Client client = dataInput.receiveClient();

            // 0: pseudo | 1: password | 2: clientIP | 3: port | 4: fileList
            if (clients.isClient(client.getPseudo())) {
                if (clients.isPwdCorrect(client.getPseudo(), client.getPassword())) {
                    clients.updateClient(client);
                    clientListManager.writeElement(clients);
                    logger.log(Level.INFO, clients.getClientName() + " is connected.");
                } else {
                    System.out.println("Wrong password.");
                    logger.log(Level.WARNING, clients.getClientName() + " used a wrong password.");

                    Client defaultClient = new Client("false", "false", "false", "false");
                    clients.addNewClient(defaultClient);
                }
            } else {
                logger.log(Level.INFO, clients.getClientName() + " creates a new account.");
                clients.addNewClient(client);
                clientListManager.writeElement(clients);
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
            logger.log(Level.SEVERE, clients.getClientName() + " looses the connection.");
            e.printStackTrace();
        }
    }
}
