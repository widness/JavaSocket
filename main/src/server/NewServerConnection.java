package server;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class NewServerConnection {
    public NewServerConnection(){
        ServerSocket mySkServer ;
        Socket srvSocket = null ;
        InetAddress localAddress=null;
        String interfaceName = "eth1";


        try {
            // Cherche le type d'address IP
            NetworkInterface ni = NetworkInterface.getByName(interfaceName);
            Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
            while(inetAddresses.hasMoreElements()) {
                InetAddress ia = inetAddresses.nextElement();

                if(!ia.isLinkLocalAddress()) {
                    if(!ia.isLoopbackAddress()) {
                        System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
                        localAddress = ia;
                    }
                }
            }

            // backlog : Connection simultané max
            // LocalAdress : Ip cible
            mySkServer = new ServerSocket(45000,5,localAddress);

            System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
            System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
            System.out.println("Listening to Port :" + mySkServer.getLocalPort());

            mySkServer.setSoTimeout(30000);//set 30 sec timout

            //Listen to a client connection wait until a client connects
            System.out.println("Waiting for a client connection:");

            // Listening
            srvSocket = mySkServer.accept();

            System.out.println("A client is connected");

            // Close listening
            mySkServer.close();
            srvSocket.close();

            System.out.println("Closing socket....");

        }catch (SocketException e) {

            //System.out.println("Connection Timed out");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
