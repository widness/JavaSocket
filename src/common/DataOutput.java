package common;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class DataOutput {
    private Socket socket;
    private PrintWriter pout;


    public DataOutput(Socket socket) {
        this.socket = socket;
    }


    public void giveInformationToServer(String clientPseudo, String password, String ip, String port, String[] list) {
        Client me = new Client(clientPseudo, password, ip, port);

        for (int i = 0; i < list.length; i++) {
            me.addFile(list[i]);
        }
        this.sendObject(me);
    }

    public void sendObject(Object objectToSend) {
        try {
            ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
            objectOutput.writeObject(objectToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

