package common;

import model.Client;
import model.File;

import java.util.ArrayList;

public class FileToSend {
    private Clients clients;
    private Files files;

    public FileToSend(Clients clients, Files files){
        this.clients = clients;
        this.files = files;
    }

    public Clients getClients(){
        return this.clients;
    }

    public Files getFiles(){
        return this.files;
    }
}
