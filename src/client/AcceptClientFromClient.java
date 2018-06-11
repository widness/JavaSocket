package client;

import common.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class AcceptClientFromClient implements Runnable {
    private Clients clients;
    private Socket clientSocket, clientSocket2;
    private FileHandler fileHandler;
    private int clientNumber;
    private DataInput dataInput;
    private Client recievedInfo;
    private String[] clientInfo;
    private InetAddress clientAddress;
    private DataOutput dataOutput;


    public AcceptClientFromClient(Socket clientSocket, int clientNo) throws Exception {
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

            // TODO: Faire le reste

            dataOutput = new DataOutput(clientSocket);
            dataOutput.sendObject(clients);

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
