package model;

import persistence.Reader;
import persistence.Saveable;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

// this class is a list of items that are currently on the map ground
public class GameMap implements Saveable {
    public static final int SCREEN_SIZE_WIDTH = 970;
    public static final int SCREEN_SIZE_HEIGHT = 785;
    public static final int NUMBER_OF_KEYS = 10;
    public static final int NUMBER_OF_FLASHLIGHTS = 4;
    public static final int NUMBER_OF_TRAPS = 20;
    public static final int KEY_TO_WIN = 6;

    private LinkedList<Item> onFloorKeys;
    private ArrayList<Trap> onFloorTraps;
    private Character mainCharacter;
    private Random rand;


    public GameMap(String name) {
        rand = new Random();
        onFloorKeys = new LinkedList<>();
        mainCharacter = new Character(name);
        onFloorTraps =  new ArrayList<>();
        addKeys(NUMBER_OF_KEYS);
        addFlashlights(NUMBER_OF_FLASHLIGHTS);
        addTraps();
    }

    // MODIFIES: this
    // EFFECTS: add given trap to list of traps
    public void addGivenTrap(Trap givenTrap) {
        onFloorTraps.add(givenTrap);
    }

    // MODIFIES: this
    // EFFECTS: add given key to the end of the list
    public void addGivenItemToFloor(Item givenKey) {
        onFloorKeys.add(givenKey);
    }

    // MODIFIES: this
    // REQUIRES: index => 0 and location must contain a key
    // EFFECTS: removes key from list in the given index location
    public void removeIndexKey(int index) {
        onFloorKeys.remove(index);
    }

    // MODIFIES: this
    // REQUIRES: there to be a trap
    // EFFECTS: removes trap from list in the given index location (for testing purposes)
    public void resetTrapList() {
        onFloorTraps = new ArrayList<>();
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


    // REQUIRES: amount > 0
    // EFFECTS: adds amount of keys to the ground at random locations
    public void addKeys(int amount) {
        int w = GameMap.SCREEN_SIZE_WIDTH;
        int h = GameMap.SCREEN_SIZE_HEIGHT;
        for (int i = 1; i <= amount; i++) {
            addGivenItemToFloor(new Key(rand.nextInt(w), rand.nextInt(h), "key"));
        }
    }

    // REQUIRES: amount > 0
    // EFFECTS: adds amount of flashlights to the ground at random locations
    public void addFlashlights(int amount) {
        int w = GameMap.SCREEN_SIZE_WIDTH;
        int h = GameMap.SCREEN_SIZE_HEIGHT;
        for (int i = 1; i <= amount; i++) {
            addGivenItemToFloor(new Flashlight("flashlight", rand.nextInt(w), rand.nextInt(h)));
        }
    }

    // EFFECTS: adds game starting amount of traps to the ground at random locations
    private void addTraps() {
        for (int i = 1; i <= GameMap.NUMBER_OF_TRAPS; i++) {
            addGivenTrap(new Trap());
        }
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
        return mainCharacter.getKeysInInventory() >= KEY_TO_WIN;
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