package model;

import java.util.ArrayList;
import java.util.Random;

// this class is a list of items that are currently on the map ground
public class ItemOnGround {
    private static int SCREEN_SIZE_WIDTH = 1000;
    private static int SCREEN_SIZE_HEIGHT = 1000;

    private ArrayList<Key> onFloorKeys;


    public ItemOnGround() {
        onFloorKeys = new ArrayList<>();
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
}
