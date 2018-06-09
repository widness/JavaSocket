package common;

import java.util.ArrayList;

public class ClientFiles {
    private static final long serialVersionUID = 5832163776451490808L;
    private ArrayList<ClientFile> clientFiles = new ArrayList<ClientFile>();

    public ClientFiles(){}

    public void addFile(ClientFile clientFile) {
        // If: file already in list -> Do nothing
        if (!this.isFile(clientFile.getName())) {
            this.clientFiles.add(clientFile);
            System.out.println(clientFile.getName() + " added!");
        }
    }

    public boolean isFile(String fileName) {
        if(!clientFiles.isEmpty()){
            for (ClientFile cf: clientFiles) {
                if (cf.getName().equalsIgnoreCase(fileName)) {
                    System.out.println("You already choosne this file");
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<ClientFile> getClientFiles() {
        return this.clientFiles;
    }
}
