package common;

import model.Client;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;


public class DataInput {
	private Socket socket;
	private BufferedReader buffin;

	
	public DataInput(Socket socket) {
		this.socket = socket;
	}

	
	public String receiveStringFromClient() {
		try {
			buffin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message = buffin.readLine();

			buffin.close();
			return message;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

    public void receiveArrayListFromClient() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            try {
                Client client = (Client) objectInput.readObject();

                System.out.println(client.getPseudo());
                ArrayList<String> files = client.getFile();
                for (int i = 0; i < files.size(); i++) {
                    files.get(i);
                }
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem");
            e.printStackTrace();
        }

    }
}
