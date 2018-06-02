package common;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class DataOutput {
	private Socket socket;
	private PrintWriter pout;

	
	public DataOutput(Socket socket) {
		this.socket = socket;
	}
	
	
	public void giveInformationToServer(String clientPseudo, String password, String ip, String port) {
		ArrayList<Object> list = new ArrayList<>();
		
		FileHandler fileHandler = new FileHandler();
		File[] listFiles = fileHandler.getListFiles();
		
		for (int i=0; i<listFiles.length; i++) {
			System.out.println(listFiles[i]);
		}
		
		list.add(clientPseudo);
		list.add(password);
		list.add(ip);
		list.add(port);
		list.add(listFiles);
		
		
		for (int i=0; i<list.size(); i++) {
			System.out.println((String)list.get(i));
		}
		
		
		
//		String message = clientPseudo + "-" + password + "-" + ip + "-" + port;
		
			
		try {
//			pout = new PrintWriter(socket.getOutputStream());
			
			
			// CHERCHE LES INFORMATIONS
//			InetAddress address = InetAddress.getLocalHost();
//	        String hostIP = address.getHostAddress() ;
//	        String hostName = address.getHostName();
			
			// WAIT FOR AN INPUT FROM THE CONSOLE
//	        sc = new Scanner(System.in);
//	        System.out.println("Your message :");
//	        String message = sc.nextLine();
//			message += "hostIP: " + hostIP + "| hostName: " + hostName;
			
			
//			pout.println(list);
//			pout.flush();
//			
//			pout.close();
			
			ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(list); 
            
            objectOutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void giveClientList(Clients clients, Files files) {
        FileToSend fts = new FileToSend(clients, files);
        // TODO: Write next
    }
	
	
//	public void giveFileListToServer(File[] listFiles) {
//		try {
//			pout = new PrintWriter(socket.getOutputStream());
//			pout.println(listFiles);
//			pout.flush();
//			pout.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//	}
}
