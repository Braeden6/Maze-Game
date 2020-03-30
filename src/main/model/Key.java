package model;

//Key is an item you can find in the maze
public class Key extends Item {
    public static final int REACH = 28;

    public Key(int locationX, int locationY, String itemName) {
        this.itemName = itemName;
        this.locationX = locationX;
        this.locationY = locationY;
        pickedUp = false;
    }

    // EFFECTS: returns true if character location can pick up item
    public boolean isAbleToPickUp(int characterLocationX, int characterLocationY) {
        return super.isWithinRange(characterLocationX,characterLocationY,REACH);
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
