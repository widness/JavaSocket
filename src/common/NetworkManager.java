package common;

import client.AcceptClientFromClient;
import server.AcceptClient;
import java.io.IOException;
import java.net.*;
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
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address) continue;
                    System.out.println(iface.getDisplayName() + " " + addr.getHostAddress());
                    return addr.getHostAddress();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    
    // @param: if the server calls this function or a client
    // If the server -> Check password and send a list back
    // @see: AcceptClientFromClient
    public void startingListening(boolean isServer) {

        InetAddress localAddress;
        ServerSocket srvSocket;
        int clientNo = 1;

        try {
            localAddress = this.getLocalAddress("wlan1");

            // Warning : the backlog value (2nd parameter) is handled by the implementation
            if (isServer) {
                srvSocket = new ServerSocket(45000, 2, localAddress);
            } else {
                srvSocket = new ServerSocket(45001, 2, localAddress);
            }

            System.out.println("Listening to Port : " + srvSocket.getLocalPort());

            //Wait for a client connection
            while(true) {
                Socket clientSocket = srvSocket.accept();
                clientSocket.setSoTimeout(180000);

                System.out.println("Hey, somebody wants to connect!");

                if(isServer){
                    Thread t = new Thread(new AcceptClient(clientSocket, clientNo));
                    t.start();
                } else {
                    Thread t = new Thread(new AcceptClientFromClient(clientSocket, clientNo));
                    t.start();
                }
                clientNo++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
