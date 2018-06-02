package common;

import model.File;

import java.util.ArrayList;


public class Files {
    private ArrayList<File> files;

    public Files() {
        files = new ArrayList<>();
    }


    public void addNewFile(String owner, String name) {
        File newFile = new File(owner, name);

        if (isFile(newFile))
            this.files.add(newFile);
    }

    
    public boolean isFile(File file) {
        for (File f: files) {
            if (f.getOwner().equalsIgnoreCase(file.getOwner()))
                if (f.getName().equals(file.getName()))
                    return true;
        }
        
        return false;
    }

    
    public int size() {
        return files.size();
    }
}