package common;

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

    public ArrayList<Object> receiveArrayListFromClient() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            try {
                Object object = objectInput.readObject();
                ArrayList<Object> receivedArray =  (ArrayList<Object>) object;

                String test[];
                test = (String[])receivedArray.get(4);
                System.out.println(test[0]);

                for (int i = 0; i < receivedArray.size(); i++) {
                   System.out.println((String)receivedArray.get(i));
                }

                return receivedArray;

            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem");
            e.printStackTrace();
        }
        return null;
    }
}
