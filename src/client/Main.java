package client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import common.NetworkManager;


public class Main {	
	
    public static void main(String[] args) throws IOException {
    	String answer;
    	String pseudo = "default";
    	String password = "default";
    	boolean choice = false;
    	
    	
    	// Read the config file for the connection
    	BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
    	String config = reader.readLine();
    	
    	String[] lineSplitted = config.split(";");
    	String ip = lineSplitted[0];
    	String name = lineSplitted[1];
    	String port = lineSplitted[2];
    	

    	// Connect to the server
    	Scanner scan = new Scanner(System.in);

    	boolean isConnected = false;

    	while(!isConnected) { // TODO: See if you want to change because you put all in the NewClientConnection,
    	    // TODO: I can't go out of the while with the sc variable, that's why it's insinde it
            System.out.print("Hello.\nWhat's your pseudo? ");
            pseudo = scan.nextLine();
            System.out.print("What's your password? ");
            password = scan.nextLine();

            NewClientConnection sc = new NewClientConnection(pseudo, password, ip, name, Integer.parseInt(port));

            sc.registerToServer();
            if (sc.acceptedByServer()) { // Iff accepted -> Close the socket with the server
                isConnected = true;
                // 1 : the client want just to register, 2 : the client connects as "guest" and ask for file list
                System.out.print("What would you like to do?\n"
                        + "Type 1 if you want just to register or type 2 if you want to ask for file list, then press ENTER key. ");

                scan.nextLine();

                while(choice == false) {
                    answer = scan.nextLine();

                    if (answer.equals("1")) {
                        sc.closeSocket();
                        choice = true;

                        NetworkManager netManager = new NetworkManager();
                        netManager.startingListening(false);
                    }
                    else {
                        if (answer.equals("2")) {
                            choice = true;

                            // Retrieve the list of files from the server
                            sc.getFileList();
                            sc.closeSocket();
                        }
                        else {
                            System.out.println("Please correct your answer: you can only type 1 or 2.");
                        }
                    }
                }
            }
        }
    }
}
