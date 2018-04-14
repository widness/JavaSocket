package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NewClientConnection {
    public NewClientConnection() {
        Socket clientSocket;
        InetAddress serverAddress;
        String serverName = "192.168.108.10";

        try {
            serverAddress = InetAddress.getByName(serverName);
            System.out.println("Get the address of the server : "+ serverAddress);

            //Ask the server to create a new socket
            clientSocket = new Socket(serverAddress,45000);

            System.out.println("We got the connexion to  "+ serverAddress);
            System.out.println("now dying....");

            clientSocket.close();

        }catch (UnknownHostException e) {

            e.printStackTrace();

        }catch (IOException e) {

            e.printStackTrace();
        }
    }
}
