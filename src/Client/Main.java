package client;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
    	Scanner scan = new Scanner(System.in);
    	String clientPseudo;
    	String password;

    	System.out.print("Hello.\nWhat's your pseudo? ");
    	clientPseudo = scan.nextLine();
    	System.out.print("What's your password?" );
    	password = scan.nextLine();

    	NewClientConnection sc = new NewClientConnection(clientPseudo, password,
                "192.168.71.105", "wlan1", 45000);
        sc.connectToServer();

    }
}
