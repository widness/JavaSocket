package server;


import java.io.IOException;
import java.net.*;
import controller.DataInput;
import controller.Clients;

public class AcceptClient implements Runnable {

    private Clients clients = new Clients();
    private Socket clientSocketOnServer;
    private int clientNumber;
    private DataInput dataInput;
    private String recievedInformations;

    private String[] client;

    //Constructor
    public AcceptClient (Socket clientSocketOnServer, int clientNo)
    {
        this.clientSocketOnServer = clientSocketOnServer;
        this.clientNumber = clientNo;
    }
    //overwrite the thread run()
    public void run() {

        try {
            System.out.println("Client Nr "+clientNumber+ " is connected");
            System.out.println("Socket is available for connection"+ clientSocketOnServer);

            dataInput = new DataInput(clientSocketOnServer);
            recievedInformations = dataInput.receiveInfoFromClient();

            System.out.println("he send me: " + recievedInformations);

            if(recievedInformations.contains("-")){
                client = recievedInformations.split("-");
            } else {
                throw new IllegalAccessException("String: " + recievedInformations + "doesn't containe -");
            }
            if(clients.isClient(client[0])){
                if(clients.isPwdCorrect(client[0], client[1]))
                    clients.updateClient(client[0], client[1], client[2], client[3]);
            } else {
                clients.addNewClient(client[0], client[1], client[2], client[3]);
            }

            clientSocketOnServer.close();
            Thread.sleep(3000);
            System.out.println("end of connection to the client " + clientNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}