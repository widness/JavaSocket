package server;

import java.io.IOException;
import java.net.*;
import common.DataInput;
import common.Clients;
import common.Files;
import common.DataOutput;


public class AcceptClient implements Runnable {
    private Clients clients = new Clients();
    private Files files = new Files();
    private Socket clientSocket;
    private int clientNumber;
    private DataInput dataInput;
    private String recievedInfo;
    private String[] clientInfos;
    private InetAddress clientAddress;
    private DataOutput dataOutput;

    public AcceptClient (Socket clientSocket, int clientNo) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNo;
    }

    public void run() {
        try {
            System.out.println("Client Nr "+clientNumber+ " is connected");
            System.out.println("Socket is available for connection"+ clientSocket);

            dataInput = new DataInput(clientSocket);
            recievedInfo = dataInput.receiveInfoFromClient();

            System.out.println("He sends me: " + recievedInfo);

            if (recievedInfo.contains("-")) {
                clientInfos = recievedInfo.split("-");
            }
            else {
                throw new IllegalAccessException("String: " + recievedInfo + "doesn't contain -");
            }

            //0: Pseudo | 1: Password | 2: clientIP | 3: Port
            if (clients.isClient(clientInfos[0])) {
                if (clients.isPwdCorrect(clientInfos[0], clientInfos[1])) {
                    clients.updateClient(clientInfos[0], clientInfos[1], clientInfos[2], clientInfos[3]);
                }
            } else {
                clients.addNewClient(clientInfos[0], clientInfos[1], clientInfos[2], clientInfos[3]);
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
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
