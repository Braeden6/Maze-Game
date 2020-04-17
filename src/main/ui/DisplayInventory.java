package ui;

import model.Character;
import model.GameMap;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

public class DisplayInventory extends JFrame {

    private GameConsoleInterface mainInterface;
    private Character mainCharacter;
    private JPanel inventoryPanel;
    private  JTextField input;

    public DisplayInventory(GameConsoleInterface g,Character c) {
        super("Inventory");
        mainCharacter = c;
        mainInterface = g;
        addPanel();
        setSize(220, 100 + 20 * Character.MAX_SIZE_OF_INVENTORY);
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
        inventoryPanel.setBackground(new Color(0x4E4E4E));
        add(inventoryPanel);
        addInventoryLayout();
    }

    // MODIFIES: this
    // EFFECTS: adds a submit button that is for dropping an item in your inventory
    private void addInventoryLayout() {
        inventoryPanel.setLayout(null);
        JButton submit = new JButton("Drop Item");
        submit.setSize(200,20);
        submit.setLocation(0,20 * Character.MAX_SIZE_OF_INVENTORY + 30);
        submit.addActionListener(e -> dropItemInput());
        input = new JTextField(10);
        input.setSize(200,20);
        input.setLocation(0,20 * Character.MAX_SIZE_OF_INVENTORY + 10);
        inventoryPanel.add(input);
        inventoryPanel.add(submit);
        add(inventoryPanel);
    }

    //
    //
    private void dropItemInput() {
        String key = input.getText();
        int i;
        try {
            i = Integer.parseInt(key);
        } catch (Exception e) {
            return;
        }
        if (i <= Character.MAX_SIZE_OF_INVENTORY) {
            mainInterface.dropItem(i - 1);
        }
    }


    // MODIFIES: g
    // EFFECTS: draws inventory to given graphics
    private void drawInventory(Graphics g) {
        int y = 20;
        g.setColor(Color.black);
        g.setFont(new Font("Arial", 20, 20));
        for (String s : getKeysString(mainCharacter.getInventory())) {
            g.drawString(s, 0, y);
            y += 20;
        }
    }

    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    public ArrayList<String> getKeysString(LinkedList<Item> listOfKeys) {
        ArrayList<String> inventory = new ArrayList<>();
        for (int i = 0; i < Character.MAX_SIZE_OF_INVENTORY; i++) {
            if (listOfKeys.size() > i) {
                inventory.add("Slot " + (i + 1) + ": a " + listOfKeys.get(i).getItemName());
            } else {
                inventory.add("Slot " + (i + 1) + ":");
            }
        }
        return inventory;
    }
}
