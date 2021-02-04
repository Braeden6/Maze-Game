package ui;

import model.GameMap;
import model.Item;
import model.Trap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class GamePanel extends JPanel {

    private MainGameSystem mainInterface;
    private GameMap mainGameMap;

    public GamePanel(MainGameSystem g, GameMap m) {
        mainInterface = g;
        mainGameMap = m;
        setPreferredSize(new Dimension(GameMap.SCREEN_SIZE_WIDTH, GameMap.SCREEN_SIZE_HEIGHT));
        setBackground(new Color(0x4E4E4E));
        mainInterface.add(this,BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTraps(g);
        drawItems(g);
        drawCharacter(g);
    }

    // MODIFIES: this
    // EFFECTS: load images
    static public BufferedImage loadImage(File location) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(location);
        } catch (Exception e) {
            System.out.println("error");
        }
        return image;
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
            if (mainGameMap.getMainCharacter().isInViewDistance(t)) {
                t.drawItem(g);
            }

        }
    }

    // MODIFIES: g
    // EFFECTS: draw keys in mainGameMap onto g
    private void drawItems(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Item i : mainGameMap.getOnFloorKeys()) {
            if (mainGameMap.getMainCharacter().isInViewDistance(i)) {
                i.drawItem(g);
            }
        }
    }


    // EFFECTS: updates mainGameMap and mainInterface to g and m
    public void displayLoadedGame(MainGameSystem g, GameMap m) {
        mainGameMap = m;
        mainInterface = g;
    }

    // MODIFIES: this
    //EFFECTS: displays a game string at the top of the JPanel
    public void endDisplayOfGame(String s) {
        JLabel endGame = new JLabel(s);
        endGame.setFont(new Font("Monospaced", Font.BOLD, 50));
        add(endGame, BorderLayout.CENTER);
    }
}