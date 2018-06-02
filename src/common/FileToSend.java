package common;

import model.Client;
import model.File;

import java.util.ArrayList;

public class FileToSend {
    private ArrayList<Client> clients;
    private ArrayList<File> files;

    public FileToSend(ArrayList<Client>clients, ArrayList<File>files){
        this.clients = clients;
        this.files = files;
    }

    public ArrayList<Client> getClients(){
        return this.clients;
    }

    public ArrayList<File> getFiles(){
        return this.files;
    }
}
