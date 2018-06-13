package common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class DataOutput {
    private Socket socket;


    public DataOutput(Socket socket) {
        this.socket = socket;
    }


    // Give the connection info to the server
    public void giveInformationToServer(String clientPseudo, String password, String ip, String port, String[] list) {
        Client me = new Client(clientPseudo, password, ip, port);

        for (int i = 0; i < list.length; i++) {
            me.addFile(list[i]);
        }
        
        sendObject(me);
    }

    
    // Send the download files to the other client
    public void sendDownloadFiles(DownloadFiles objectToSend) {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(objectToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // Method to send an object through the socket
    public void sendObject(Object objectToSend) {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(objectToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
