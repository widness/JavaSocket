package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import common.NetworkManager;


public class NewServerConnection {
    private NetworkManager networkManager = new NetworkManager();

    public NewServerConnection() {
        Socket socket = null ;
        InetAddress localAddress = null;
        ServerSocket srvSocket;
        int clientNo = 1;

        try {
            localAddress = networkManager.getLocalAddress("wlan1");
            
            //Warning : the backlog value (2nd parameter) is handled by the implementation
            srvSocket = new ServerSocket(45000, 2, localAddress);
            System.out.println("Listening to Port :" + srvSocket.getLocalPort());

            //Wait for a client connection
            while(true) {
                Socket clientSocket = srvSocket.accept();
                clientSocket.setSoTimeout(180000); 

                System.out.println("Hey, somebody wants to connect!");

                Thread t = new Thread(new AcceptClient(clientSocket, clientNo));
                clientNo++;

                t.start(); 
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
