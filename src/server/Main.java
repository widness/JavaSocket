package server;

import common.NetworkManager;


public class Main {
    public static void main(String[] args) {
        System.out.println("zzZZzzZZzz");
        System.out.println("Eh.. Hey?!");
        System.out.println("Ok ok, i'm starting the server.");

        NetworkManager netManager = new NetworkManager();
        netManager.startingListening(true);
    }
}
