package client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {	
    public static void main(String[] args) throws IOException{
    	Scanner scan = new Scanner(System.in);
    	String clientPseudo;
    	String password;

    	System.out.print("Hello.\nWhat's your pseudo? ");
    	clientPseudo = scan.nextLine();
    	System.out.print("What's your password? ");
    	password = scan.nextLine();

    	FileReader in = new FileReader("config.txt");
    	BufferedReader bin = new BufferedReader(in);
    	String config = bin.readLine();
    	
    	String[] lineSplitted = config.split(";");
    	String ip = lineSplitted[0];
    	String name = lineSplitted[1];
    	String port = lineSplitted[2];
    	
//    	NewClientConnection sc = new NewClientConnection(clientPseudo, password, "192.168.43.238", "wlan1", 45000);
    	NewClientConnection sc = new NewClientConnection(clientPseudo, password, ip, name, Integer.parseInt(port));
        sc.connectToServer();
    }
}
