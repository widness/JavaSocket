package common;

import java.util.ArrayList;


public class ClientFiles {
    private ArrayList<ClientFile> clientFiles = new ArrayList<ClientFile>();

    public ClientFiles(){}

    
    // If the file already exists in list, do nothing!
    public void addFile(ClientFile clientFile) {
        if (!this.isFile(clientFile.getName())) {
            this.clientFiles.add(clientFile);
            System.out.println(clientFile.getName() + " added!");
        }
    }

    
    // Test if the file has already been added to the list
    public boolean isFile(String fileName) {
        if (!clientFiles.isEmpty()) {
            for (ClientFile cf: clientFiles) {
                if (cf.getName().equalsIgnoreCase(fileName)) {
                    System.out.println("You have already choosen this file.");
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
