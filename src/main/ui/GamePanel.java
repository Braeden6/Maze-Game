package ui;

import model.GameMap;
import model.Key;
import model.Trap;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private GameConsoleInterface mainInterface;
    private GameMap mainGameMap;

    public GamePanel(GameConsoleInterface g, GameMap m) {
        mainInterface = g;
        mainGameMap = m;
        setPreferredSize(new Dimension(mainGameMap.SCREEN_SIZE_WIDTH, mainGameMap.SCREEN_SIZE_HEIGHT));
        setBackground(Color.GRAY);
        mainInterface.add(this,BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCharacter(g);
        drawTraps(g);
        drawKeys(g);
        //drawString(g);

    }

    private void drawCharacter(Graphics g) {
        g.setColor(Color.BLUE);
        int x = mainGameMap.getMainCharacter().getLocationX();
        int y = mainGameMap.getMainCharacter().getLocationY();
        g.fillOval(x,y,10,10);
    }

    private void drawTraps(Graphics g) {
        g.setColor(Color.RED);
        for (Trap t : mainGameMap.getOnFloorTraps()) {
            drawTrap(g, t);
        }
    }

    private void drawTrap(Graphics g, Trap t) {
        int x = t.getCenterX();
        int y = t.getCenterY();
        int size  = t.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP;
        g.fillOval(x,y,size,size);
    }

    private void drawKeys(Graphics g) {
        g.setColor(Color.YELLOW);
        for (Key k : mainGameMap.getOnFloorKeys()) {
            drawKey(g, k);
        }
    }

    private void drawKey(Graphics g, Key k) {
        int x = k.getLocationX();
        int y = k.getLocationY();
        int size  = k.REACH;
        g.fillOval(x,y,size,size);
    }

    private void drawString(Graphics g) {
        Color saved = g.getColor();
        //g.setColor(new Color(188, 12, 4));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        g.drawString("hello", 100, 120);
        g.setColor(saved);
    }

    private void inputBox(GameConsoleInterface g) {
        JTextField input = new JTextField(10);
        g.add(input, BorderLayout.NORTH);
    }

    public void loadGame(GameConsoleInterface g, GameMap m) {
        mainGameMap = m;
        mainInterface = g;
    }



}
