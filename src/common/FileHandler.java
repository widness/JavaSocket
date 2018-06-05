package common;

import java.io.*;
import java.util.ArrayList;


public class FileHandler {
    private File[] listFiles;
    private String[] list;
    private File f;

    public FileHandler() {
        this.f = new File("clientList.ser");
    }

    public Clients readElements(){

        Clients clients = new Clients();

        if (f.isFile() && f.canRead()) {
            try {
                FileInputStream fis = new FileInputStream(f);
                try {
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    clients = (Clients) ois.readObject();
                    fis.close();
                    ois.close();
                    return clients;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("New clientList.ser created");
        return clients;
    }

    public void writeElement (Clients clients) throws Exception{
        FileOutputStream fos = new FileOutputStream("clientList.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(clients);
        oos.close();
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
