package model;


public class File {
    private String owner;
    private String name;

    
    public File(String owner, String name){
        this.owner = owner;
        this.name = name; 
    }

    
    public String getOwner() {
        return owner;
    }

    
    public void setOwner(String owner) {
        this.owner = owner;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }
}
