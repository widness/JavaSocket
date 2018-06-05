package common;

import model.Client;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class FileHandler {
    public static final String PATH = "clientList.txt";
    private File[] listFiles;
    private String[] list;
    
    public Clients readElements() {
        try {

            FileInputStream fis = new FileInputStream("clientList.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Clients clients = (Clients) ois.readObject();
            ois.close();

            return clients;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void writeElement (Clients clients) {
        try{
            FileOutputStream fos = new FileOutputStream("clientList.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(clients);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAll(ArrayList<Client> elements) {
        System.out.println("Read file: ");
        for (Client element: elements) {
            System.out.println(element);
        }
    }
    
    public String[] getListFiles() {
    	return list;
    }
    
	
	public void shareRepository(String path) throws IOException {
		File folder = new File(path);
		listFiles = folder.listFiles();
						
		FileOutputStream fout = new FileOutputStream("fileList.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(listFiles);
		oos.close();
	}
	
	
	public ArrayList<File> retrieveRepository(String path) throws IOException, ClassNotFoundException {
		ArrayList<File> listFiles;
		
		FileInputStream fin = new FileInputStream("fileList.ser");
		ObjectInputStream ois = new ObjectInputStream(fin);
		listFiles = (ArrayList<File>) ois.readObject();
		ois.close();
		
		return listFiles;
	}
	
	
	public void retrieveListFiles(String path) {
		File folder = new File(path);
		listFiles = folder.listFiles();
		
		String[] list = new String[listFiles.length];
		for (int i=0; i<listFiles.length; i++) {
			list[i] = listFiles[i].getName();
		}
		
		this.list = list;
	}
}
