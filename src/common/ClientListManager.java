package common;

import java.io.*;
import java.util.ArrayList;
import javax.swing.filechooser.FileSystemView;


public class ClientListManager {
    private File[] listFiles;
    private String[] list;
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
                //ois.close();
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
        //oos.close();
    }
    

    public void readAll(ArrayList<Client> elements) {
        System.out.println("Read files: ");
        for (Client element: elements) {
            System.out.println(element);
        }
    }
    
    
    // TODO: check if all the methods are used or not

    
	
	public void shareRepository(String path) throws IOException {
		File folder = new File(path);
		listFiles = folder.listFiles();
					
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("fileList.ser"));
		oos.writeObject(listFiles);
		//oos.close();
	}
	
	
	public ArrayList<File> retrieveRepository(String path) throws IOException, ClassNotFoundException {
		ArrayList<File> listFiles;
		
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("fileList.ser"));
		listFiles = (ArrayList<File>) ois.readObject();
		//ois.close();
		
		return listFiles;
	}
	
	

}
