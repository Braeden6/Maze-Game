package model;

import java.util.Random;

public class Trap extends Item {
    public static final int DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP = 24;

    public Trap() {
        Random rand  = new Random();
        super.locationX = rand.nextInt(GameMap.SCREEN_SIZE_WIDTH);
        super.locationY = rand.nextInt(GameMap.SCREEN_SIZE_HEIGHT);
    }

    // EFFECTS: returns true if given location will set off the trap
    public boolean isTrapSetOff(int locationX, int locationY) {
        return super.isWithinRange(locationX,locationY,DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP);
    }
}
