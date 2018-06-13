package common;

import java.io.*;


public class ClientListManager {
    private File f;

    
    public ClientListManager() {
        this.f = new File("clientList.ser");
    }

    
    public Clients readElements() throws ClassNotFoundException {
        Clients clients = new Clients();

        if (f.isFile() && f.canRead()) {
            try {
            	ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                clients = (Clients) ois.readObject();
                return clients;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("The file 'clientList.ser' has been created.");
        return clients;
    }
    

    public void writeElement(Clients clients) throws Exception {
    	ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("clientList.ser"));
        oos.writeObject(clients);
    }
}
