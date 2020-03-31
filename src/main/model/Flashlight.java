package model;

public class Flashlight extends Item {
    public static final int REACH = 28;
    public static final int VIEW_DISTANCE_CHANGE = 50;


    public Flashlight(String itemName, int locationX, int locationY) {
        this.itemName = itemName;
        this.locationX = locationX;
        this.locationY = locationY;
        pickedUp = false;
    }

    @Override
    public void pickUpItem(Character character) {
        super.pickUpItem(character);
        character.increaseViewDistance(VIEW_DISTANCE_CHANGE);
    }

    @Override
    public void dropItem(Character character) {
        super.dropItem(character);
        character.decreaseViewDistance(VIEW_DISTANCE_CHANGE);
    }
}
