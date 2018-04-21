package client;

import java.util.Scanner;


public class Main {
    public static void main(String[] args){
    	Scanner scan = new Scanner(System.in);
    	String clientPseudo;
    	String password;
    	
    	//TODO: éventuellement créer un login
    	System.out.print("Hello.\nWhat's your pseudo? ");
    	clientPseudo = scan.nextLine();
    	System.out.println("What's your password?" );
    	password = scan.nextLine();
    	
    	
    	
    	

        NewClientConnection sc = new NewClientConnection("192.168.43.238", clientPseudo, password);
        sc.connectToServer();
        
        // test
    }
}
