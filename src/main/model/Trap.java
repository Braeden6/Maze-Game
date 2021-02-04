package model;

import java.awt.*;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Trap extends Item {
    public static final int DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP = 24;
    public static final int DAMAGE = 10;

    public Trap() {
        Random rand  = new Random();
        super.locationX = rand.nextInt(GameMap.SCREEN_SIZE_WIDTH);
        super.locationY = rand.nextInt(GameMap.SCREEN_SIZE_HEIGHT);
    }

    // EFFECTS: returns true if given location will set off the trap
    public boolean isTrapSetOff(int locationX, int locationY) {
        return super.isWithinRange(locationX,locationY,DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP);
    }

    // MODIFIES: g
    // EFFECTS: draws Trap onto given graphics
    @Override
    public void drawItem(Graphics g) {
            int size = 8;
            int x = this.getLocationX();
            int y = this.getLocationY();
            g.drawLine(x,y,x + size,y + size);
            g.drawLine(x,y,x,y + (int) sqrt(2 * pow(size,2)));
            g.drawLine(x,y,x - size,y + size);
            g.drawLine(x,y,x - size,y - size);
            g.drawLine(x,y,x, y - (int) sqrt(2 * pow(size,2)));
            g.drawLine(x,y,x + size,y - size);
            g.drawLine(x,y,x - (int) sqrt(2 * pow(size,2)),y);
            g.drawLine(x,y,x + (int) sqrt(2 * pow(size,2)),y);
    }


}
