package controller;

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

    
    public static void readAll(LinkedList<Client> elements) {
        System.out.println("Read file: ");
        for (Client element: elements) {
            System.out.println(element);
        }
    }
}
