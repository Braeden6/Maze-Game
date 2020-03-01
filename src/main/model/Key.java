package model;

//Key is an item you can find in the maze
public class Key implements Item {
    public static final int REACH = 28;

    private String itemName;
    private int locationX;
    private int locationY;
    private boolean pickedUp;

    public Key(int locationX, int locationY, String itemName) {
        this.itemName = itemName;
        this.locationX = locationX;
        this.locationY = locationY;
        pickedUp = false;
    }

    // EFFECTS: returns name of item
    public String getItemName() {
        return itemName;
    }

    // EFFECTS: returns true if character location can pick up item
    public boolean isAbleToPickUp(int characterLocationX, int characterLocationY) {
        return isWithinBounds(characterLocationX, locationX) && isWithinBounds(characterLocationY, locationY);
    }

    // EFFECTS: return true if numberWithin is in REACH of location
    private boolean isWithinBounds(int location, int numberWithin) {
        return numberWithin - REACH <= location && numberWithin + REACH >= location;
    }

    // REQUIRES: locationX < SCREEN_SIZE_WIDTH && locationY < SCREEN_SIZE_HEIGHT
    // MODIFIES: this
    // EFFECTS: changes location of item
    public void setLocation(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    // EFFECTS: returns x location of item
    public int getLocationX() {
        return locationX;
    }

    // EFFECTS: returns y location of item
    public int getLocationY() {
        return locationY;
    }

    // REQUIRES: isPickUp == true
    // MODIFIES: this
    // EFFECTS: signals item as picked up
    public void pickUpItem() {
        pickedUp = true;
    }

    // EFFECTS: return true if item has been picked up
    public boolean isPickedUp() {
        return pickedUp;
    }

    // REQUIRES: locationX < SCREEN_SIZE_WIDTH && locationY < SCREEN_SIZE_HEIGHT
    // MODIFIES: this
    // EFFECTS: signals item as dropped and changes to new location
    public void dropItem(int locationX, int locationY) {
        pickedUp = false;
        setLocation(locationX,locationY);
    }

}
