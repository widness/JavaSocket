package common;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class DataInput {
	private Socket socket;
	private BufferedReader buffin;

	
	public DataInput(Socket socket) {
		this.socket = socket;
	}

	
	// TODO: check if this method is used?
	public String receiveStringFromClient() {
		try {
			buffin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = buffin.readLine();

			//buffin.close();
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	
    public Client receiveClient() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            
            try {
                Client client = (Client) objectInput.readObject();
                //objectInput.close();
                return client;
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has a problem.");
            e.printStackTrace();
        }
        
        return null;
    }

    
    public Clients receiveClients() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            
            try {
                Clients clients = (Clients) objectInput.readObject();
                //objectInput.close();
                return clients;
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    // 0 : names of the files, 1 : files converted into bytes
    public void receiveData() {
    	try {
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		
    		try {
    			DownloadFiles dl = (DownloadFiles) objectInput.readObject();
    			ArrayList<String> names = dl.getNames();
    			ArrayList<byte[]> bytes = dl.getBytes();

    			
    			for (int i = 0; i < bytes.size(); i++) {
    				String name = names.get(i);
    				byte[] item = bytes.get(i);
    				
    				name = retrieveFileName(name);
    				
    				readBytes(name, item);
    			}
    		} catch (ClassNotFoundException e) {
				e.printStackTrace();
    		}
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }


	private String retrieveFileName(String name) {
		int startIndex = name.lastIndexOf('\\') + 1;
		int endIndex = name.length();
		String refactor = name.substring(startIndex, endIndex);
				
		return refactor;
	}


	private void readBytes(String name, byte[] item) throws IOException {
		String path = "C:\\Users\\Montaine\\Desktop\\" + name;
				
		FileOutputStream fos = new FileOutputStream(path); 
	    fos.write(item);
	    fos.close();
	}
}
