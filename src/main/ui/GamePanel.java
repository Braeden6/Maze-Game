package ui;

import model.GameMap;
import model.Key;
import model.Trap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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
        drawTraps(g);
        drawKeys(g);
        drawCharacter(g);
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
        //bottom half body
        g.drawLine(x,y,x,y + 4);
        //top half body
        g.drawLine(x,y,x,y - 4);
        //right leg
        g.drawLine(x,y + 4,x + 12,y + 14);
        //left leg
        g.drawLine(x,y + 4,x - 12,y + 14);
        //right arm
        g.drawLine(x,y - 2,x + 14,y);
        //left arm
        g.drawLine(x,y - 2,x - 14,y);
        //head
        g.fillOval(x - 5,y - 14, 11, 11);
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
        if (mainGameMap.getMainCharacter().isInViewDistance(t)) {
            int size = 8;
            int x = t.getLocationX();
            int y = t.getLocationY();
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
        if (mainGameMap.getMainCharacter().isInViewDistance(k)) {
            int x = k.getLocationX();
            int y = k.getLocationY();
            g.drawImage(keyImage,x - 13,y - 13,32,26,null);
        }
    }

    // EFFECTS: updates mainGameMap and mainInterface to g and m
    public void loadGame(GameConsoleInterface g, GameMap m) {
        mainGameMap = m;
        mainInterface = g;
    }

    // MODIFIES: this
    //EFFECTS: displays a game string at the top of the JPanel
    public void endGame(String s) {
        JLabel endGame = new JLabel(s);
        endGame.setFont(new Font("Monospaced", Font.BOLD, 50));
        add(endGame, BorderLayout.CENTER);
    }
}