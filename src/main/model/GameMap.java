package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;

// this class is a list of items that are currently on the map ground
public class GameMap implements Saveable {
    public static int SCREEN_SIZE_WIDTH = 1000;
    public static int SCREEN_SIZE_HEIGHT = 1000;
    public static int NUMBER_OF_KEYS = 4;
    public static int NUMBER_OF_TRAPS = 4;

    private ArrayList<Key> onFloorKeys;
    private ArrayList<Trap> onFloorTraps;
    private Character mainCharacter;


    public GameMap(String name) {
        onFloorKeys = new ArrayList<>();
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
    public void addGivenKey(Key givenKey) {
        onFloorKeys.add(givenKey);
    }

    // MODIFIES: this
    // REQUIRES: index > 0 and location must contain a key
    // EFFECTS: removes key from list in the given index location
    public void removeIndexKey(int index) {
        onFloorKeys.remove(index);
    }


    // EFFECTS: returns the list of keys
    public ArrayList<Key> getOnFloorKeys() {
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
    public Key getItem(String name) {
        for (Key k : onFloorKeys) {
            if (k.getItemName().equals(name)) {
                onFloorKeys.remove(k);
                return k;
            }
        }
        return new Key(0,0,"invalid");
    }


    // EFFECTS: return true if one of the trap were set off
    public boolean isTrapSetOff() {
        for (Trap t : onFloorTraps) {
            if (t.isTrapSetOff(mainCharacter.getLocationX(),mainCharacter.getLocationY())) {
                return true;
            }
        }
        return false;
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
    private void saveKeys(PrintWriter printWriter, ArrayList<Key> keys) {
        for (Key k : keys) {
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
            printWriter.print(t.getCenterX());
            printWriter.print(Reader.DELIMITER);
            printWriter.print(t.getCenterY());
            printWriter.print(Reader.LIST_DELIMITER);
        }
        printWriter.print(Reader.CLASS_DELIMITER);
    }
}