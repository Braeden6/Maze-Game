package model;

import ui.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

//Key is an item you can find in the maze
public class Key extends Item {
    public static final int REACH = 28;

    private BufferedImage keyImage;

    public Key(int locationX, int locationY, String itemName) {
        keyImage = GamePanel.loadImage(new File("./data/keyImage.jpg"));
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

    // MODIFIES: g
    // EFFECTS: draw Key onto g
    @Override
    public void drawItem(Graphics g) {
        int x = locationX;
        int y = locationY;
        g.drawImage(keyImage,x - 13,y - 13,32,26,null);
    }

}
