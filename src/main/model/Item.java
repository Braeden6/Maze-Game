package model;

public abstract class Item {

    protected int locationX;
    protected int locationY;
    protected boolean pickedUp;
    protected String itemName;

    //getters
    public boolean isPickedUp() {
        return pickedUp;
    }

    public String getItemName() {
        return itemName;
    }

    public int getLocationX() {
        return locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    // REQUIRES: locationX and locationY to be within map bounds
    // MODIFIES: this
    // EFFECTS: set new location of the item
    public void setLocation(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
    }

    // EFFECTS: returns true if given location will set off the trap
    public boolean isWithinRange(int locationX, int locationY, int distance) {
        int minY = locationY - distance;
        int minX = locationX - distance;
        int maxY = locationY + distance;
        int maxX = locationX + distance;
        int x = getLocationX();
        int y = getLocationY();
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    // REQUIRES: isPickUp == true
    // MODIFIES: this
    // EFFECTS: signals item as picked up
    public void pickUpItem() {
        pickedUp = true;
    }

    public void dropItem(int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        pickedUp = false;
    }

    // EFFECTS: returns true if character location can pick up item
    public boolean isAbleToPickUp(int characterLocationX, int characterLocationY) {
        return isWithinRange(characterLocationX,characterLocationY,Key.REACH);
    }




}
