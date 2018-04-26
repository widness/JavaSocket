package controller;

import model.Client;

import java.io.IOException;
import java.util.LinkedList;

public class Clients {
    private LinkedList<Client> clients;
    private FileHandler fileHandler = new FileHandler();

    public Clients(){

        clients = fileHandler.readElements();

        if(clients == null){
            System.out.println("I've created a brend new clientList");
            clients = new LinkedList<Client>();
        }

    }
    /*	----------------------------------------------------------	*/
    //	-- Add new account
    public boolean addNewClient(String pseudo, String password, String clientIP, String clientPort){
        Client newClient = new Client(pseudo, password,  clientIP, clientPort);

        if(!this.isClient(pseudo)) {
            this.clients.add(newClient);
            System.out.println("New client created :)");
            fileHandler.writeElement(newClient);
            return true;
        }

        return false;
    }

    public boolean updateClient(String pseudo, String password, String clientIP, String clientPort){
        for(int i = 0; i<clients.size(); i++)
            if(clients.get(i).getPseudo().equalsIgnoreCase(pseudo))
                if (clients.get(i).getPassword().equals(password)) {
                    clients.get(i).setClientIP(clientIP);
                    clients.get(i).setClientPort(clientPort);
                }

        return false;
    }

    public boolean isClient(String pseudo){
        for(Client c: clients)
            if(c.getPseudo().equalsIgnoreCase(pseudo)) {
                System.out.println("This client exist already");
                return true;
            }
        return false;
    }

    public boolean isPwdCorrect(String pseudo, String password){
         for(Client c: clients){
             if(c.getPseudo().equalsIgnoreCase(pseudo))
                 if (c.getPassword().equals(password)) {
                     System.out.println("I Confirm the password");
                     return true;
                 }
         }
         return false;
    }

    public int size(){
        return clients.size();
    }


}
