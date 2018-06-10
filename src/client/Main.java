package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {	
	
    public static void main(String[] args) throws IOException {
    	String answer;
    	String pseudo;
    	String password;
    	boolean choice = false;
    	
    	
    	// Read the config file for the connection
    	BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
    	String config = reader.readLine();
    	
    	String[] lineSplitted = config.split(";");
    	String ip = lineSplitted[0];
    	String name = lineSplitted[1];
    	String port = lineSplitted[2];
    	

    	// Retrieve client information
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Hello.\nWhat would you like to do?\n"
    			+ "Type 1 if you want to register or type 2 if you want to ask for file list, then press ENTER key.");
    	
    	
    	// 1 : the client want to register, 2 : the client connects as guest and ask for file list
    	answer = scan.nextLine();
    	
    	while(choice == false) {
        	if (answer.equals("1")) {
        		choice = true;
        		
            	System.out.print("\nWhat's your pseudo? ");
            	pseudo = scan.nextLine();
            	System.out.print("What's your password? ");
            	password = scan.nextLine();
            	
            	// Connect to the server
            	NewClientConnection sc = new NewClientConnection(pseudo, password, ip, name, Integer.parseInt(port));
                sc.connectToServer();
        	}
        	else {
        		if (answer.equals("2")) {
        			choice = true;
        			
        			// Connect to the server
        			NewGuestConnection gc = new NewGuestConnection(ip, name, Integer.parseInt(port));
        		}
        		else {
        			System.out.println("Please correct your answer: you can only type 1 or 2.");
        			answer = scan.nextLine();
        		}
        	}
    	}
    }
}
