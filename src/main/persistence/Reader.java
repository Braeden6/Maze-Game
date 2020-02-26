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
    //used to separate num/string/etc...
    public static final String DELIMITER = ",";
    //used at the end of a list
    public static final String LIST_DELIMITER = ";";
    // used to separate items list/ class
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
    // where  string 1 == character string 2 == ground keys 3 == traps
    private static GameMap parseContent(List<String> fileContent) {
        ArrayList<String> readInformation = new ArrayList<>();
        ArrayList<String> readInformationListDelimiter;
        ArrayList<String> readInformationDelimiter;
        for (String line : fileContent) {
            readInformationListDelimiter = splitString(line,LIST_DELIMITER);
            for (String line2 : readInformationListDelimiter) {
                readInformationDelimiter = splitString(line2,DELIMITER);
                readInformation.addAll(readInformationDelimiter);
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

    // REQUIRES: first component  is character info
    //  (each index is separated by a DELIMITER)
    //          - index 0 == name
    //          - index 1 == locationX
    //          - index 2 == locationY
    //          - rest is the character inventory following the keys requirements
    //          second component is onFloorKeys info
    //          - index 0 == name
    //          - index 1 == locationX
    //          - index 2 == locationY
    //          - index 3 == isPickedUp (which is false)
    //          - 4-7/8-11/etc. are more keys
    //          third component is onFloorTraps info
    //          - index 0 == locationX
    //          - index 1 == locationY
    //          - 2-3/4-5/etc. are more traps
    // EFFECTS: returns an GameMap constructed from components
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
        while (!components.get(0).equals("/")) {
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
        while (!components.get(0).equals("/")) {
            locationX = Integer.parseInt(getFirstItem(components));
            locationY =  Integer.parseInt(getFirstItem(components));
            createdTrap = new Trap();
            createdTrap.setTrapCenter(locationX,locationY);
            listOfKeys.add(createdTrap);
        }
        components.remove(0);
    }
}