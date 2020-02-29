package ui;

import model.GameMap;
import model.Key;
import model.Trap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GamePanel extends JPanel {

    private BufferedImage keyImage;
    private GameConsoleInterface mainInterface;
    private GameMap mainGameMap;

    public GamePanel(GameConsoleInterface g, GameMap m) {
        mainInterface = g;
        mainGameMap = m;
        loadImage();
        setPreferredSize(new Dimension(GameMap.SCREEN_SIZE_WIDTH, GameMap.SCREEN_SIZE_HEIGHT));
        setBackground(new Color(0x4E4E4E));
        mainInterface.add(this,BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacter(g);
        drawTraps(g);
        drawKeys(g);
    }

    // MODIFIES: this
    // EFFECTS: load images
    private void loadImage() {
        try {
            keyImage = ImageIO.read(new File("./data/keyImage.jpg"));
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    // MODIFIES: g
    // EFFECTS: draw Character from mainGameMap onto g
    private void drawCharacter(Graphics g) {
        g.setColor(Color.BLUE);
        int x = mainGameMap.getMainCharacter().getLocationX();
        int y = mainGameMap.getMainCharacter().getLocationY();
        g.setColor(new Color(0x000000));
        g.drawOval(x,y,8,8);
        //left arm
        g.drawLine(x + 4,y + 8,x,y + 15);
        // right arm
        g.drawLine(x + 4,y + 8,x + 8,y + 14);
        //body
        g.drawLine(x + 4,y + 8,x + 4,y + 14);
        // left leg
        g.drawLine(x + 4,y + 14,x,y + 22);
        // right leg
        g.drawLine(x + 4,y + 14,x + 8,y + 22);
    }


    // MODIFIES: g
    // EFFECTS: draw traps in mainGameMap onto g
    private void drawTraps(Graphics g) {
        g.setColor(Color.RED);
        for (Trap t : mainGameMap.getOnFloorTraps()) {
            drawTrap(g, t);
        }
    }

    // MODIFIES: g
    // EFFECTS: draw Trap t onto g
    private void drawTrap(Graphics g, Trap t) {
        int x = t.getCenterX() - (Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP / 2);
        int y = t.getCenterY() - (Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP / 2);
        int size  = Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP / 2;
        g.fillOval(x,y,size,size);
    }

    // MODIFIES: g
    // EFFECTS: draw keys in mainGameMap onto g
    private void drawKeys(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Key k : mainGameMap.getOnFloorKeys()) {
            drawKey(g, k);
        }
    }

    // MODIFIES: g
    // EFFECTS: draw Key k onto g
    private void drawKey(Graphics g, Key k) {
        int x = k.getLocationX();
        int y = k.getLocationY();
        int size  = Key.REACH / 2;
        g.drawImage(keyImage,x,y,size,size,null);
       // g.fillOval(x,y,size,size);
    }

    // EFFECTS: updates mainGameMap and mainInterface to g and m
    public void loadGame(GameConsoleInterface g, GameMap m) {
        mainGameMap = m;
        mainInterface = g;
    }



}
