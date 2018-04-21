package controller;

import model.Client;

import java.util.ArrayList;

public class Clients {
    private ArrayList<Client> clients;

    public Clients(){
        clients = new ArrayList<>();
    }
    /*	----------------------------------------------------------	*/
    //	-- Add new account
    public void addNewClient(String pseudo, String password, String clientIP, String clientPort){
        Client newClient = new Client(pseudo, password,  clientIP, clientPort);

        if(!isClient(newClient))
            this.clients.add(newClient);
    }

    public boolean isClient(Client client){
        for(Client c: clients){
            if(c.getPseudo().equalsIgnoreCase(client.getPseudo()))
                return true;
        }
        return false;
    }

    public boolean isPwdCorrect(Client client){
         for(Client c: clients){
             if(c.getPassword().equals(client.getPseudo()))
                 return true;
         }
         return false;
    }

    public int size(){
        return clients.size();
    }


}
