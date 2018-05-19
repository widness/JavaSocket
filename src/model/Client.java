package model;


public class Client {
    private String pseudo;
    private String password;
    private String clientIP;
    private String clientPort;

    public Client(String pseudo, String password, String clientIP, String clientPort){
        this.pseudo = pseudo;
        this.password = password;
        this.clientIP = clientIP;
        this.clientPort = clientPort;
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
