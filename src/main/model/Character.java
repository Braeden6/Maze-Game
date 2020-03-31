package model;

import java.util.LinkedList;

import static java.lang.Math.*;

// This class is the main character that has an inventory and a move-able location
public class Character {
    public static final int MOVEMENT_DISTANCE = 10;
    public static final int MAX_SIZE_OF_INVENTORY = 6;
    public static final int STARTING_VIEW_DISTANCE = 125;

    private int viewDistance;
    private LinkedList<Item> inventory;
    private String name;
    private int locationX;
    private int locationY;

    // EFFECTS: Set character name to the given name and places him at the starting location
    public Character(String name) {
        viewDistance = STARTING_VIEW_DISTANCE;
        inventory = new LinkedList<>();
        this.name = name;
        locationX = 0;
        locationY = GameMap.SCREEN_SIZE_HEIGHT / 2;
    }

    // MODIFIES: this
    // EFFECTS: increases view distance by given amount
    public void increaseViewDistance(int increase) {
        viewDistance += increase;
    }

    // MODIFIES: this
    // EFFECTS: decreases view distance by given amount
    public void decreaseViewDistance(int decrease) {
        viewDistance -= decrease;
    }

    //getter
    public int getViewDistance() {
        return viewDistance;
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
    public boolean isPickedUpItem(LinkedList<Item> itemOnFloor) {
        if (!isInventoryFull()) {
            for (Item k : itemOnFloor) {
                if (k.isAbleToPickUp(locationX,locationY)) {
                    k.pickUpItem(this);
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
    public LinkedList<Item> getInventory() {
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
    public Item dropItem(int index) {
        Item droppedItem = inventory.get(index);
        droppedItem.dropItem(this);
        return droppedItem;
    }

    // EFFECTS: returns true if the character distance from the item is less then VIEW_DISTANCE
    public boolean isInViewDistance(Item item) {
        int x = item.getLocationX();
        int y = item.getLocationY();
        int d = (int) sqrt(pow(locationX - x, 2) + pow(locationY - y, 2));
        return d <= viewDistance;
    }


}