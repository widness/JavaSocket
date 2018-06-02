package Common;

import model.Client;

import java.io.*;
import java.util.LinkedList;


public class FileHandler {
    public static final String PATH = "clientList.txt";
    
    public LinkedList<Client> readElements() {
        FileReader reader;
        String line = null;
        String[] lineSplitted = null;
        LinkedList<Client> returnList = new LinkedList<Client>();

        try {
            reader = new FileReader(new File(PATH));
            BufferedReader bufferedReader = new BufferedReader(reader);
            
            while(true) {
                line = bufferedReader.readLine();
                if (line == null)
                	break;

                lineSplitted = line.split(";");
                returnList.add(new Client(
                        lineSplitted[0],
                        lineSplitted[1],
                        lineSplitted[2],
                        lineSplitted[3]));
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return returnList;
    }

    public void writeElement (Client element) {
        System.out.println("I'm gonna write it in my local file");
        try {
            FileWriter writer = new FileWriter(new File(PATH), true);
            writer.write(element.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateElement (Client element) {
        String lineToRemove = element.toString();
        try {

            File inFile = new File(PATH);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(PATH));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {
                    pw.println(line);
                }
                pw.flush();
            }
            pw.close();
            br.close();

            //Delete the original file
            //FIXEME: isn't working
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

            //Write the news client (= updated one)
            this.writeElement(element);
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void readAll(LinkedList<Client> elements) {
        System.out.println("Read file: ");
        for (Client element: elements) {
            System.out.println(element);
        }
    }
}
