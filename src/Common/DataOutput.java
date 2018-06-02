package Common;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


public class DataOutput {
	private Socket socket;
	private PrintWriter pout;

	
	public DataOutput(Socket socket) {
		this.socket = socket;
	}
	
	
	public void giveInformationToServer(String clientPseudo, String password, String ip, String port) {
		String message = clientPseudo + "-" + password + "-" + ip + "-" + port;
		
		try {
			pout = new PrintWriter(socket.getOutputStream());
			
			
			// CHERCHE LES INFORMATIONS
//			InetAddress address = InetAddress.getLocalHost();
//	        String hostIP = address.getHostAddress() ;
//	        String hostName = address.getHostName();
			
			// WAIT FOR AN INPUT FROM THE CONSOLE
//	        sc = new Scanner(System.in);
//	        System.out.println("Your message :");
//	        String message = sc.nextLine();
//			message += "hostIP: " + hostIP + "| hostName: " + hostName;
			
			
			pout.println(message);
			pout.flush();
			
			pout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
