package model;


import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {
    private static final long serialVersionUID = 5950169519310163575L;

    private String pseudo;
    private String password;
    private String clientIP;
    private String clientPort;
    private ArrayList<String> files = new ArrayList<String>();

    public Client(String pseudo, String password, String clientIP, String clientPort){
        this.pseudo = pseudo;
        this.password = password;
        this.clientIP = clientIP;
        this.clientPort = clientPort;
    }

    public void addFile(String fileName) {
        files.add(fileName);
    }

    public ArrayList<String> getFile() {
        return this.files;
    }
    
    public String getPseudo() {
        return pseudo;
    }

    
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    
    public String getPassword() {
        return password;
    }

    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getClientIP() {
        return clientIP;
    }

    
    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    
    public String getClientPort() {
        return clientPort;
    }

    
    public void setClientPort(String clientPort) {
        this.clientPort = clientPort;
    }

    
    @Override
    public String toString() {
        return pseudo + ";" + password + ";" + clientIP + ";" + clientPort;
    }
}
