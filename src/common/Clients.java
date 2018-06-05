package common;

import java.io.Serializable;
import java.util.ArrayList;

import model.Client;

public class Clients implements Serializable {
    private static final long serialVersionUID = 5832063776451490808L;
    private ArrayList<Client> clients;
    private FileHandler fileHandler = new FileHandler();

    public Clients(){

    }
    
    public boolean addNewClient(Client client) {
        Client newClient = client;

        if (!this.isClient(newClient.getPseudo())) {
            this.clients.add(newClient);
            System.out.println("New client created!");
            return true;
        }

        return false;
    }

    
    public boolean updateClient(Client client){
        for (int i = 0; i < clients.size(); i++)
            if (clients.get(i).getPseudo().equalsIgnoreCase(client.getPseudo())) {
                if (clients.get(i).getPassword().equals(client.getPassword())) {
                    clients.get(i).setClientIP(client.getClientIP());
                    clients.get(i).setClientPort(client.getClientPort());
                    clients.get(i).setFiles(client.getFiles());
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
