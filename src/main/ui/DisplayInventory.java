package ui;

import model.Character;
import model.GameMap;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class DisplayInventory extends JFrame {

    private JPanel inventoryPanel;
    private Character mainCharacter;

    public DisplayInventory(GameConsoleInterface g, Character c) {
        super("Inventory");
        mainCharacter = c;
        addPanel();
        setSize(500,500);
    }

    //setter
    public void setMainCharacter(Character c) {
        mainCharacter = c;
    }

    // MODIFIES: mainInterface
    // EFFECTS: enables display of inventory on main interface
    public void enableDisplay() {
        setVisible(true);
    }

    // MODIFIES: mainInterface
    // EFFECTS: disables display of inventory on main interface
    public void disableDisplay() {
        setVisible(false);
    }


    // MODIFIES: this
    // EFFECTS: initiates panel to for graphics and adds it to the inventory window
    private void addPanel() {
        int w = GameMap.SCREEN_SIZE_WIDTH / 4;
        int h = 30;
        inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawInventory(g);
            }
        };
        inventoryPanel.setPreferredSize(new Dimension(w,h));
        inventoryPanel.setBackground(Color.white);
        add(inventoryPanel);
    }


    // MODIFIES: g
    // EFFECTS: draws inventory to given graphics
    private void drawInventory(Graphics g) {
        int x = 0;
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 20, 20));
        for (String s : getKeysString(mainCharacter.getInventory())) {
            g.drawString(s + "|", x, 20);
            x += s.length() * 10;
        }
    }
    
    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    public ArrayList<String> getKeysString(LinkedList<Item> listOfKeys) {
        ArrayList<String> inventory = new ArrayList<>();
        String name;
        for (Item k : listOfKeys) {
            name = k.getItemName();
            inventory.add("Slot" + (listOfKeys.indexOf(k) + 1) + " has " + name);
        }
        return inventory;
    }
}
