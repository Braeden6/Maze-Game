package ui;

import model.Character;
import model.GameMap;
import model.Key;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DisplayInventory extends JPanel {

    private GameConsoleInterface mainInterface;
    private Character mainCharacter;

    public DisplayInventory(GameConsoleInterface g, Character c) {
        mainInterface = g;
        mainCharacter = c;
        int w = GameMap.SCREEN_SIZE_WIDTH / 4;
        int h = 30;
        setPreferredSize(new Dimension(w,h));
        setBackground(Color.white);
    }

    //setter
    public void setMainCharacter(Character c) {
        mainCharacter = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawInventory(g);
    }

    // MODIFIES: this
    // EFFECTS: display everything in mainCharacter inventory
    private void drawInventory(Graphics g) {
        int x = 0;
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 20, 20));
        for (String s : getKeysString(mainCharacter.getInventory())) {
            g.drawString(s + "|", x, 20);
            x += s.length() * 10;
        }

    }

    // MODIFIES: mainInterface
    // EFFECTS: enables display of inventory on main interface
    public void enableDisplay() {
        mainInterface.add(this, BorderLayout.NORTH);
    }

    // MODIFIES: mainInterface
    // EFFECTS: disables display of inventory on main interface
    public void disableDisplay() {
        mainInterface.remove(this);
    }

    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    public ArrayList<String> getKeysString(LinkedList<Key> listOfKeys) {
        ArrayList<String> inventory = new ArrayList<>();
        String name;
        for (Key k : listOfKeys) {
            name = k.getItemName();
            inventory.add("Slot" + (listOfKeys.indexOf(k) + 1) + " has " + name);
        }
        return inventory;
    }
}
