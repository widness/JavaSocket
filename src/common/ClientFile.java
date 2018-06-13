package common;


public class ClientFile {
    private String name;
    private String ip;
    private String port;

    
    public ClientFile(String name, String ip, String port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
    }
    
    
    // List of getters and setters methods
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getIp() {
        return ip;
    }

    
    public void setIp(String ip) {
        this.ip = ip;
    }

    
    public String getPort() {
        return port;
    }

    
    public void setPort(String port) {
        this.port = port;
    }

    
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
