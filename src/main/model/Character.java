package model;

import java.util.LinkedList;

// This class is the main character that has an inventory and a move-able location
public class Character {
    public static final int MOVEMENT_DISTANCE = 10;
    public static final int MAX_SIZE_OF_INVENTORY = 6;

    private LinkedList<Key> inventory;
    private String name;
    private int locationX;
    private int locationY;


    // EFFECTS: Set character name to the given name and places him at the starting location
    public Character(String name) {
        inventory = new LinkedList<>();
        this.name = name;
        locationX = 0;
        locationY = GameMap.SCREEN_SIZE_HEIGHT / 2;
    }

    // EFFECTS: set location of main character
    // MODIFIES: this
    public void setLocation(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    // REQUIRES: command must be w, a, s, or d
    // MODIFIES: this
    // EFFECTS: moves character by MOVEMENT_DISTANCE in direction; however, will not move to negatives or
    //  above SCREEN_SIZE_WIDTH and SCREEN_SIZE_HEIGHT
    // -w is up on the screen
    // -s is down on the screen
    // -a is left on the screen
    // -d is right on the screen
    public void moveCharacter(String command) {
        switch (command) {
            case "w":
                locationY = moveMaximum(locationY,GameMap.SCREEN_SIZE_HEIGHT);
                break;
            case "s":
                locationY = moveMinimum(locationY);
                break;
            case "a":
                locationX = moveMinimum(locationX);
                break;
            default:
                locationX = moveMaximum(locationX,GameMap.SCREEN_SIZE_WIDTH);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: test whether movement will cause character move out of bounds. Moves character by MOVEMENT_DISTANCE
    // or to the edge and return new location
    private int moveMaximum(int currentLocation, int maxValue) {
        return Math.min(currentLocation + MOVEMENT_DISTANCE, maxValue);
    }

    // MODIFIES: this
    // EFFECTS: test whether movement will cause character move out of bounds. Moves character by MOVEMENT_DISTANCE
    // or to the edge and return new location
    private int moveMinimum(int currentLocation) {
        return Math.max(currentLocation - MOVEMENT_DISTANCE, 0);
    }

    // MODIFIES: this
    // EFFECTS: checks if inventory is full and if it can pick up the item. If both are true it add item
    // to inventory and returns true as if it had picked up item
    public boolean isPickedUpItem(LinkedList<Key> itemOnFloor) {
        if (!isInventoryFull()) {
            for (Key k : itemOnFloor) {
                if (k.isAbleToPickUp(locationX,locationY)) {
                    k.pickUpItem();
                    inventory.add(k);
                    itemOnFloor.remove(k);
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: check if inventory is full
    private boolean isInventoryFull() {
        return inventory.size() >= MAX_SIZE_OF_INVENTORY;
    }

    // EFFECTS: return character locationX
    public int getLocationX() {
        return locationX;
    }

    // EFFECTS: return character locationX
    public int getLocationY() {
        return locationY;
    }

    // EFFECTS: returns character name
    public String getCharacterName() {
        return name;
    }

    // EFFECTS: returns list of items in inventory
    public LinkedList<Key> getInventory() {
        return inventory;
    }

    // REQUIRES: 0 <= locationX and locationY <= SCREEN_SIZE
    // MODIFIES: this
    // EFFECTS: set new location of character
    public void setCharacterLocation(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    // REQUIRES: index must have item in the list position
    // MODIFIES: this
    // EFFECTS: removes item from inventory, marks item as dropped, and changes location of item as character's current
    public Key dropItem(int index) {
        Key droppedItem = inventory.get(index);
        inventory.remove(index);
        droppedItem.dropItem(locationX,locationY);
        return droppedItem;
    }
}