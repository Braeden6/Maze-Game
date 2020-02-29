package model;

import java.util.Random;

public class Trap {
    public static final int DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP = 40;

    private int centerX;
    private int centerY;
    private Random rand;


    public Trap() {
        rand  = new Random();
        centerX = rand.nextInt(GameMap.SCREEN_SIZE_WIDTH);
        centerY = rand.nextInt(GameMap.SCREEN_SIZE_HEIGHT);
    }


    // EFFECTS: returns true if given location will set off the trap
    public boolean isTrapSetOff(int locationX, int locationY) {
        int minY = centerY - DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP;
        int minX = centerX - DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP;
        int maxY = centerY + DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP;
        int maxX = centerX + DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP;
        return locationX >= minX && locationX <= maxX && locationY >= minY && locationY <= maxY;
    }

    // REQUIRES: locationX and locationY to be within map bounds
    // MODIFIES: this
    // EFFECTS: set new location of trap
    public void setTrapCenter(int locationX, int locationY) {
        centerX = locationX;
        centerY = locationY;
    }

    // EFFECTS: returns x center of trap
    public int getCenterX() {
        return centerX;
    }

    // EFFECTS: returns y center of trap
    public int getCenterY() {
        return centerY;
    }
}
