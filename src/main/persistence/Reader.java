package persistence;

import model.GameMap;
import model.Key;
import model.Trap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Reader {
    //used to seperate num/string/etc...
    public static final String DELIMITER = ",";
    //used at the end of a list
    public static final String LIST_DELIMITER = ";";
    // used to seperate items list/ class
    public static final String CLASS_DELIMITER = "\n";


    // EFFECTS: returns a list of accounts parsed from file; throws
    // IOException if an exception is raised when opening / reading from file
    public static GameMap readMap(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }

    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns a list of accounts parsed from list of strings
    // where each string contains data for one account
    private static GameMap parseContent(List<String> fileContent) {
        ArrayList<String> readInformation = new ArrayList<>();
        ArrayList<String> readInformationListDelimiter;
        ArrayList<String> readInformationDelimiter;
        for (String line : fileContent) {
            readInformationListDelimiter = splitString(line,LIST_DELIMITER);
            for (String line2 : readInformationListDelimiter) {
                readInformationDelimiter = splitString(line2,DELIMITER);
                for (String line3 : readInformationDelimiter) {
                    readInformation.add(line3);
                }
            }
            readInformation.add("/");
        }
        return parseGame(readInformation);
    }

    // EFFECTS: returns a list of strings obtained by splitting line on given DELIMITER
    private static ArrayList<String> splitString(String line, String delimiter) {
        String[] splits = line.split(delimiter);
        return new ArrayList<>(Arrays.asList(splits));
    }

    // REQUIRES: components has size 4 where element 0 represents the
    // id of the next account to be constructed, element 1 represents
    // the id, elements 2 represents the name and element 3 represents
    // the balance of the account to be constructed
    // EFFECTS: returns an account constructed from components
    private static GameMap parseGame(ArrayList<String> components) {
        GameMap map = new GameMap(getFirstItem(components));
        int locationX = Integer.parseInt(getFirstItem(components));
        int locationY =  Integer.parseInt(getFirstItem(components));
        map.getMainCharacter().setLocation(locationX, locationY);
        addKeys(components,map.getMainCharacter().getInventory()); //inventory
        addKeys(components,map.getOnFloorKeys()); // keys on floor
        addTraps(components,map.getOnFloorTraps());
        return map;
    }

    // EFFECTS: returns first item in list and removes the item from the list
    // MODIFIES: given list
    private static String getFirstItem(ArrayList<String> list) {
        String firstItem = list.get(0);
        list.remove(0);
        return firstItem;
    }

    // EFFECTS: adds keys to list until a '/' is reached
    // MODIFIES: Given key list
    private static void addKeys(ArrayList<String> components, ArrayList<Key> listOfKeys) {
        Key createdKey;
        String name;
        int locationX;
        int locationY;
        while (components.get(0) != "/") {
            name = getFirstItem(components);
            locationX = Integer.parseInt(getFirstItem(components));
            locationY =  Integer.parseInt(getFirstItem(components));
            createdKey = new Key(locationX,locationY,name);
            if (Boolean.parseBoolean(getFirstItem(components))) {
                createdKey.pickUpItem();
            }
            listOfKeys.add(createdKey);
        }
        components.remove(0);
    }

    // EFFECTS: adds traps to list until a '/' is reached
    // MODIFIES: Given trap list
    private static void addTraps(ArrayList<String> components, ArrayList<Trap> listOfKeys) {
        Trap createdTrap;
        int locationX;
        int locationY;
        while (components.get(0) != "/") {
            locationX = Integer.parseInt(getFirstItem(components));
            locationY =  Integer.parseInt(getFirstItem(components));
            createdTrap = new Trap();
            createdTrap.setTrapCenter(locationX,locationY);
            listOfKeys.add(createdTrap);
        }
        components.remove(0);
    }
}