package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {	
	
    public static void main(String[] args) throws IOException {
    	String pseudo;
    	String password;

    	// Retrieve client information
    	Scanner scan = new Scanner(System.in);
    	System.out.print("Hello.\n\nWhat's your pseudo? ");
    	pseudo = scan.nextLine();
    	System.out.print("What's your password? ");
    	password = scan.nextLine();

    	// Read the config file for the connection
    	BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
    	String config = reader.readLine();
    	
    	String[] lineSplitted = config.split(";");
    	String ip = lineSplitted[0];
    	String name = lineSplitted[1];
    	String port = lineSplitted[2];
    	
    	// Connect to the server
    	NewClientConnection sc = new NewClientConnection(pseudo, password, ip, name, Integer.parseInt(port));
        sc.connectToServer();
    }
}
