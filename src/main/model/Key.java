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

    @Override
    public void pickUpItem(Character character) {
        super.pickUpItem(character);
        character.keyAddedToInventory();
    }

    @Override
    public void dropItem(Character character) {
        super.dropItem(character);
        character.keyRemovedFromInventory();
    }

}
