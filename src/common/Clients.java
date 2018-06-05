package common;

import java.io.Serializable;
import java.util.ArrayList;

import model.Client;

public class Clients implements Serializable {
    private static final long serialVersionUID = 5832063776451490808L;
    private ArrayList<Client> clients;
    private FileHandler fileHandler = new FileHandler();

    public Clients(){
        clients = fileHandler.readElements();

        if (clients == null) {
        	clients = new ArrayList<Client>();
            System.out.println("I've created a brend new client list");          
        }
    }
    
    public boolean addNewClient(String pseudo, String password, String clientIP, String clientPort) {
        Client newClient = new Client(pseudo, password,  clientIP, clientPort);

        if (!this.isClient(pseudo)) {
            this.clients.add(newClient);
            System.out.println("New client created!");
            return true;
        }

        return false;
    }

    
    public boolean updateClient(String pseudo, String password, String clientIP, String clientPort){
        for (int i = 0; i < clients.size(); i++)
            if (clients.get(i).getPseudo().equalsIgnoreCase(pseudo)) {
                if (clients.get(i).getPassword().equals(password)) {
                    clients.get(i).setClientIP(clientIP);
                    clients.get(i).setClientPort(clientPort);
                    return true;
                }
            }
        
        return false;
    }

    
    public boolean isClient(String pseudo) {
        for (Client c: clients)
            if (c.getPseudo().equalsIgnoreCase(pseudo)) {
                System.out.println("This client exists already");
                return true;
            }
        
        return false;
    }

    
    public boolean isPwdCorrect(String pseudo, String password) {
         for (Client c: clients) {
             if (c.getPseudo().equalsIgnoreCase(pseudo)) {
                 if (c.getPassword().equals(password)) {
                     System.out.println("I confirm the password");
                     return true;
                 }
             }
         }
         
         return false;
    }

    
    public int size() {
        return clients.size();
    }
}
