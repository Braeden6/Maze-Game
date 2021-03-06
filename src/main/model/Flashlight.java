package model;

import ui.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Flashlight extends Item {
    public static final int REACH = 28;
    public static final int VIEW_DISTANCE_CHANGE = 50;

    private BufferedImage flashlightImage;

    public Flashlight(String itemName, int locationX, int locationY) {
        flashlightImage = GamePanel.loadImage(new File("./data/flashlight.jpg"));
        this.itemName = itemName;
        this.locationX = locationX;
        this.locationY = locationY;
        pickedUp = false;
    }

    // MODIFIES: this
    // EFFECTS: adds item from character inventory and updates view distance
    @Override
    public void pickUpItem(Character character) {
        super.pickUpItem(character);
        character.increaseViewDistance(VIEW_DISTANCE_CHANGE);
    }

    // MODIFIES: this
    // EFFECTS: removes item from character inventory, updates view distance and drops it at the character location
    @Override
    public void dropItem(Character character) {
        super.dropItem(character);
        character.decreaseViewDistance(VIEW_DISTANCE_CHANGE);
    }

    // MODIFIES: g
    // EFFECTS: draws Item onto given graphics
    @Override
    public void drawItem(Graphics g) {
            int x = this.locationX;
            int y = this.locationY;
            g.drawImage(flashlightImage,x - 13,y - 13,32,26,null);
        }
    }