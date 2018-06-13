package common;

import java.util.ArrayList;


public class DownloadFiles {
	private	ArrayList<String> names = new ArrayList<String>();
    private ArrayList<byte[]> bytes = new ArrayList<byte[]>();
    
    
    public DownloadFiles(ArrayList<String> names, ArrayList<byte[]> bytes) {
    	this.names = names;
    	this.bytes = bytes;
    }

    
	public ArrayList<String> getNames() {
		return names;
	}

	
	public ArrayList<byte[]> getBytes() {
		return bytes;
	}
}
