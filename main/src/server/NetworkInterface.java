package server;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class NetworkInterface {

/**
 * @param args
 * @throws UnknownHostException
 */
public static void main(String[] args) throws UnknownHostException {
    try {

        //collections of all interfaces
        Enumeration<java.net.NetworkInterface> allni;

        System.out.println("----->All interfaces");

        //get all the interfaces of your machine
        allni = java.net.NetworkInterface.getNetworkInterfaces();
        while(allni.hasMoreElements()) {
            java.net.NetworkInterface nix = allni.nextElement();

            //get the interfaces names
            System.out.println("interface name: " + nix.getName());
        }

        System.out.println("----->Up interfaces");

        //get all the interfaces of your machine
        allni = java.net.NetworkInterface.getNetworkInterfaces();
        while(allni.hasMoreElements()) {
            java.net.NetworkInterface nix = allni.nextElement();

            //get the interfaces names if connected
            if (nix.isUp()){
                System.out.println("interface name: " + nix.getName());
            }
        }
        System.out.println("Host: " + InetAddress.getLocalHost());

        Scanner sc = new Scanner(System.in);
        System.out.print("Url.. : ");
        String nr = sc.nextLine();


        try {
            System.out.println("IP: " + InetAddress.getByName(nr));
        } catch (Exception e) {
            System.out.println("Seems like this don't existe");
            System.out.println(e);
        }


        System.out.println();
    } catch (SocketException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
