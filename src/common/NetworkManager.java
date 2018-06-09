package common;

import server.AcceptClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;


public class NetworkManager {
    private InetAddress localAddress = null;

    public InetAddress getLocalAddress(String interfaceName) {
        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();
                if (!ia.isLinkLocalAddress()) {
                    if (!ia.isLoopbackAddress()) {
                        System.out.println(ni.getName() + " =>   IP: " + ia.getHostAddress());
                        localAddress = ia;
                    }
                }
            }
            
            return localAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    
    public String getOwnIp(String interfaceName) {
        try {
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();

                if (!ia.isLoopbackAddress()) {
                    System.out.println(ni.getName() + " =>   IP: " + ia.getHostAddress());
                    return ia.getHostAddress();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // @param: if the server call this function or a client
    // If the server -> Ctrl password and send a list back
    // @see: AcceptClient
    public void startingListening(boolean isServer){
        InetAddress localAddress;
        ServerSocket srvSocket;
        int clientNo = 1;

        try {
            localAddress = this.getLocalAddress("wlan1");

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
