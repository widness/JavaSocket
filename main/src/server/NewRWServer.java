package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;
import java.io.PrintWriter;

public class NewRWServer {
    /**
     * @param args
     */
    public static void main(String[] args) {

        Socket srvSocket = null ;
        InetAddress localAddress = null;
        ServerSocket mySkServer;
        PrintWriter pout;
        Scanner sc;
        int i =0;
        String interfaceName = "eth1";

        try {

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

            //Warning : the backlog value (2nd parameter is handled by the implementation
            mySkServer = new ServerSocket(45000,10,localAddress);

            //set 3min timeout
            mySkServer.setSoTimeout(180000);

            System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
            System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
            System.out.println("Listening to Port :" + mySkServer.getLocalPort());

            //wait for client connection
            srvSocket = mySkServer.accept();
            System.out.println("A client is connected :"+ i++);

            //open the output data stream to write on the client
            // permet l'écriture
            pout = new PrintWriter(srvSocket.getOutputStream());

            // Cherche les informations
            InetAddress address = InetAddress.getLocalHost();
            String hostIP = address.getHostAddress() ;
            String hostName = address.getHostName();

            //wait for an input from the console
            sc = new Scanner(System.in);
            System.out.println("Your message :");
            String message = sc.nextLine();

            message += "hostIP: " + hostIP + "| hostName: " + hostName;

            //write the message on the output stream
            pout.println(message);

            // Envoie les messages
            pout.flush();

            //Then die
            System.out.println("Now dying");
            srvSocket.close();
            mySkServer.close();
            pout.close();

        }catch (SocketException e) {

            System.out.println("Connection Timed out");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}