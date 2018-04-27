package server;

import controller.NetworkManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class NewServerConnection {

    private NetworkManager networkManager = new NetworkManager();

    public NewServerConnection(){

        Socket srvSocket = null ;
        InetAddress localAddress = null;
        ServerSocket mySkServer;

        int ClientNo = 1;

        try{
            localAddress = networkManager.getLocalAddress("wlan1");
            //Port: Max 47823
            //Warning : the backlog value (2nd parameter) is handled by the implementation
            mySkServer = new ServerSocket(45000,10,localAddress);
            System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
            System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
            System.out.println("Listening to Port :" + mySkServer.getLocalPort());

            //wait for a client connection
            while(true)
            {
                Socket clientSocket = mySkServer.accept();
                clientSocket.setSoTimeout(180000); // Timeout - 3min

                System.out.println("Hey, somebody want to connect!");

                Thread t = new Thread(new AcceptClient(clientSocket,ClientNo));
                ClientNo++;

                t.start(); //starting the thread
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
