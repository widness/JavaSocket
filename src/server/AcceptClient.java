package server;


import java.io.IOException;
import java.net.*;
import controller.DataInput;

public class AcceptClient implements Runnable {

    private Socket clientSocketOnServer;
    private int clientNumber;
    private DataInput dataInput;

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
            //TODO: write the getter client
            dataInput = new DataInput(clientSocketOnServer);
            System.out.println(dataInput.receiveInformationFromClient());

            clientSocketOnServer.close();
            Thread.sleep(3000);
            System.out.println("end of connection to the client " + clientNumber);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}