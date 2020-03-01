package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;


public class GameOptionPanels extends KeyAdapter  {

    private boolean visibleSubmitButton = false;

    private GameConsoleInterface mainInterface;

    private ButtonPressed buttonActionListener;

    private JPanel optionArea;
    private JTextField input;
    private JButton submit;
    private JButton inventory;
    private JButton pickUp;
    private JButton dropItem;
    private JButton newKey;
    private JButton saveGame;
    private JButton loadGame;

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

        inventory.addActionListener(buttonActionListener);
        pickUp.addActionListener(buttonActionListener);
        dropItem.addActionListener(buttonActionListener);
        newKey.addActionListener(buttonActionListener);
        saveGame.addActionListener(buttonActionListener);
        loadGame.addActionListener(buttonActionListener);
    }

    // MODIFIES: this
    // EFFECTS: removes action listener from buttons
    public void endGame() {
        inventory.removeActionListener(buttonActionListener);
        pickUp.removeActionListener(buttonActionListener);
        dropItem.removeActionListener(buttonActionListener);
        newKey.removeActionListener(buttonActionListener);
        saveGame.removeActionListener(buttonActionListener);
        loadGame.removeActionListener(buttonActionListener);
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
    }

    // EFFECTS: set Focusable off all buttons to false
    private void buttonDisableFocusable() {
        inventory.setFocusable(false);
        pickUp.setFocusable(false);
        dropItem.setFocusable(false);
        newKey.setFocusable(false);
        saveGame.setFocusable(false);
        loadGame.setFocusable(false);
    }

    // MODIFIES: this
    // EFFECTS: if load text box is not visible it will open a new one an display it
    public void loadGame() {
        if (!visibleSubmitButton) {
            visibleSubmitButton = true;
            input = new JTextField(10);
            submit = new JButton("Submit");
            submit.addActionListener(new SubmitButton());
            optionArea.add(input);
            optionArea.add(submit);
            mainInterface.resetMainFrame();
        }
    }

    // MODIFIES: this
    // EFFECTS: if Submit button is pressed then gets input and remove button/text box
    private class SubmitButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mainInterface.loadGame(input.getText());
            optionArea.remove(submit);
            optionArea.remove(input);
            mainInterface.resetMainFrame();
            visibleSubmitButton = false;
        }
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
                    loadGame();
                    break;
                default:
                    displayAction(pressed);
                    break;
            }
        }

        private void displayAction(String pressed) {
            if ("Inventory".equals(pressed)) {
                mainInterface.displayInventory();
            }
        }
    }
}

