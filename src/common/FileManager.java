package common;

import java.io.File;
import javax.swing.filechooser.FileSystemView;


public class FileManager {
    private File[] listFiles;
    private String[] list;
	
    
    public FileManager() {}
    
    
    public String[] getListFiles() {
    	return list;
    }
	
		
	// Retrieve the home path of the client
	public String getHomePath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path = home.getAbsolutePath();
		
		return path;
	}
	
	
	// Method to create a new repository (=> given path)
	public boolean createRepository(String folderPath) {			
		File folder = new File(folderPath);

		if (!folder.exists()) {
			folder.mkdir();
			return true;
		}
		
		return false;
	}
	
	
	// Method to retrieve a list of files (=> given path)
	public void retrieveListFiles(String path) {
		File folder = new File(path);
		listFiles = folder.listFiles();
		
		String[] list = new String[listFiles.length];
		for (int i = 0; i < listFiles.length; i++) {
			list[i] = listFiles[i].getName();
		}
		
		this.list = list;
	}
}
