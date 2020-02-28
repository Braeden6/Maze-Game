package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;


public class GameOptionPanels extends KeyAdapter  {

    private GameConsoleInterface mainInterface;

    private ButtonPressed buttonActionListener;

    private JPanel optionArea;

    private JButton inventory;
    private JButton pickUp;
    private JButton dropItem;
    private JButton newKey;
    private JButton saveGame;
    private JButton loadGame;
    private JButton displayKeys;
    private JButton displayTraps;

    public GameOptionPanels(GameConsoleInterface parent) {
        mainInterface = parent;
        optionArea = new JPanel();
        initializeGraphics();
    }

    // EFFECTS: initialize Buttons to main JFrame
    private void initializeGraphics() {
        optionArea.setLayout(new GridLayout(0,1));
        optionArea.setSize(new Dimension(0, 0));
        mainInterface.add(optionArea, BorderLayout.SOUTH);
        buttonActionListener = new ButtonPressed();
        initializeButtons();
        buttonDisableFocusable();
        enableButtons();
    }

    // EFFECTS: adds buttons to JPanel and add ActionListener to for each button
    private void enableButtons() {
        optionArea.add(inventory);
        optionArea.add(pickUp);
        optionArea.add(dropItem);
        optionArea.add(newKey);
        optionArea.add(saveGame);
        optionArea.add(loadGame);
        optionArea.add(displayKeys);
        optionArea.add(displayTraps);

        inventory.addActionListener(buttonActionListener);
        pickUp.addActionListener(buttonActionListener);
        dropItem.addActionListener(buttonActionListener);
        newKey.addActionListener(buttonActionListener);
        saveGame.addActionListener(buttonActionListener);
        loadGame.addActionListener(buttonActionListener);
        displayKeys.addActionListener(buttonActionListener);
        displayTraps.addActionListener(buttonActionListener);
    }

    // MODIFIES: this
    // EFFECTS: instantiates each of the buttons
    private void initializeButtons() {
        inventory = new JButton("Inventory");
        pickUp = new JButton("Pick Up Item");
        dropItem = new JButton("Drop Item");
        newKey = new JButton("Add Key");
        saveGame = new JButton("Save Game");
        loadGame = new JButton("Load Game");
        displayKeys = new JButton("Display Keys");
        displayTraps = new JButton("Display Traps");
    }

    // EFFECTS: set Focusable off all buttons to false
    private void buttonDisableFocusable() {
        inventory.setFocusable(false);
        pickUp.setFocusable(false);
        dropItem.setFocusable(false);
        newKey.setFocusable(false);
        saveGame.setFocusable(false);
        loadGame.setFocusable(false);
        displayKeys.setFocusable(false);
        displayTraps.setFocusable(false);
    }

    private class ButtonPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String pressed = event.getActionCommand();
            switch (pressed) {
                case "Pick Up Item" :
                    mainInterface.pickUpItem();
                    break;
                case "Drop Item" :
                    mainInterface.dropItem();
                    break;
                case "Add Key" :
                    mainInterface.addKeys(1);
                    break;
                case "Save Game" :
                    mainInterface.saveGame();
                    break;
                case "Load Game" :
                    mainInterface.loadGame();
                    break;
                default:
                    displayAction(pressed);
                    break;
            }
        }

        private void displayAction(String pressed) {
            switch (pressed) {
                case "Display Keys" :
                    mainInterface.displayKey(mainInterface.getMainGameMap().getOnFloorKeys());
                    break;
                case "Display Traps" :
                    mainInterface.displayTraps();
                    break;
                case "Inventory" :
                    mainInterface.displayInventory();
                    break;
                default:
                    break;
            }

        }
    }
}

