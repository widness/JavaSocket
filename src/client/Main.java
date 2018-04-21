package client;

public class Main {
    public static void main(String[] args){

        NewClientConnection sc = new NewClientConnection("192.168.43.238");
        sc.connectToServer();
        
        // test
    }
}
