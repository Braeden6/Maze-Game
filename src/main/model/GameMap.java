package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

// this class is a list of items that are currently on the map ground
public class GameMap implements Saveable {
    public static final int SCREEN_SIZE_WIDTH = 970;
    public static final int SCREEN_SIZE_HEIGHT = 785;
    public static final int NUMBER_OF_KEYS = 10;
    public static final int NUMBER_OF_TRAPS = 20;
    public static final int KEY_TO_WIN = 4;

    private LinkedList<Item> onFloorKeys;
    private ArrayList<Trap> onFloorTraps;
    private Character mainCharacter;


    public GameMap(String name) {
        onFloorKeys = new LinkedList<>();
        mainCharacter = new Character(name);
        onFloorTraps =  new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add given trap to list of traps
    public void addGivenTrap(Trap givenTrap) {
        onFloorTraps.add(givenTrap);
    }

    // MODIFIES: this
    // EFFECTS: add given key to the end of the list
    public void addGivenKey(Item givenKey) {
        onFloorKeys.add(givenKey);
    }

    // MODIFIES: this
    // REQUIRES: index > 0 and location must contain a key
    // EFFECTS: removes key from list in the given index location
    public void removeIndexKey(int index) {
        onFloorKeys.remove(index);
    }


    // EFFECTS: returns the list of keys
    public LinkedList<Item> getOnFloorKeys() {
        return onFloorKeys;
    }

    // EFFECTS: returns the list of traps
    public ArrayList<Trap> getOnFloorTraps() {
        return onFloorTraps;
    }

    //EFFECTS: returns the main character
    public Character getMainCharacter() {
        return mainCharacter;
    }


    // EFFECTS: returns item and removes it from the list that has the given name or a new one with name invalid
    public Item getItem(String name) {
        for (Item k : onFloorKeys) {
            if (k.getItemName().equals(name)) {
                onFloorKeys.remove(k);
                return k;
            }
        }
        return new Key(0,0,"invalid");
    }


    // EFFECTS: returns true if one of the trap were set off
    public boolean isTrapSetOff() {
        for (Trap t : onFloorTraps) {
            if (t.isTrapSetOff(mainCharacter.getLocationX(),mainCharacter.getLocationY())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if keys in the same location is  >= KEY_TO_WIN
    public boolean gameWon() {
        Item checkingKey;
        int amountOfSameLocation = 0;
        for (int i = 0; amountOfSameLocation < KEY_TO_WIN && i < onFloorKeys.size(); i++) {
            checkingKey = onFloorKeys.get(i);
            amountOfSameLocation = 0;
            for (Item k : onFloorKeys) {
                if (isSameLocation(k, checkingKey)) {
                    amountOfSameLocation++;
                }
            }
        }
        return amountOfSameLocation >= KEY_TO_WIN;
    }

    // EFFECTS: returns true if they 2 keys have the same x and y coords
    public boolean isSameLocation(Item k1, Item k2) {
        int k1x = k1.getLocationX();
        int k2x = k2.getLocationX();
        int k1y = k1.getLocationY();
        int k2y = k2.getLocationY();
        return k1x == k2x && k1y == k2y;
    }

    @Override
    public void save(PrintWriter printWriter) {
        saveCharacter(printWriter);
        saveKeys(printWriter, onFloorKeys);
        saveTraps(printWriter, onFloorTraps);
    }

    // MODIFIES: printWriter
    // EFFECTS: writes the Character to printWriter
    private void saveCharacter(PrintWriter printWriter) {
        printWriter.print(mainCharacter.getCharacterName());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(mainCharacter.getLocationX());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(mainCharacter.getLocationY());
        printWriter.print(Reader.LIST_DELIMITER);
        saveKeys(printWriter, mainCharacter.getInventory());
    }

    // MODIFIES: printWriter
    // EFFECTS: writes the Key list to printWriter
    private void saveKeys(PrintWriter printWriter, LinkedList<Item> keys) {
        for (Item k : keys) {
            printWriter.print(k.getItemName());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(k.getLocationX());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(k.getLocationY());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(k.isPickedUp());
            printWriter.print(Reader.LIST_DELIMITER);
        }
        printWriter.print(Reader.CLASS_DELIMITER);
    }

    // MODIFIES: printWriter
    // EFFECTS: writes the Key list to printWriter
    private void saveTraps(PrintWriter printWriter, ArrayList<Trap> traps) {
        for (Trap t : traps) {
            printWriter.print(t.getLocationX());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(t.getLocationY());
            printWriter.print(Reader.LIST_DELIMITER);
        }
        printWriter.print(Reader.CLASS_DELIMITER);
    }
}