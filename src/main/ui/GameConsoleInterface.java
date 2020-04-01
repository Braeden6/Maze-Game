package ui;

import model.*;
import model.Character;
import persistence.Writer;
import persistence.Reader;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

//This is the main game application
public class GameConsoleInterface extends JFrame {

    private static final int INTERVAL = 20;
    private Character mainCharacter;
    private boolean displayInventory = false;
    private GameMap mainGameMap;
    private GamePanel gp;
    private DisplayInventory dp;
    private GameOptionPanels gameOption;
    private Timer timer;

    private KeyHandler movement;

    // EFFECTS: initializes scanner and starts main loop
    public GameConsoleInterface() {
        super("Search Game");
        new CharacterNameInputScreen(this);
    }

    // EFFECTS: reset visibility and focus of the main frame
    public void resetMainFrame() {
        setVisible(true);
        requestFocus();
    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, ae -> {
            gp.repaint();
            trapSetOff();
            gameWon();
            if (displayInventory) {
                dp.repaint();
            }

        });
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: initializes the JFrame of the app
    public void initializeGame(String name) {
        generateGame(name);
        addGraphics();
    }

    // MODIFIES: this
    // EFFECTS: generates GameMap, Keys, Traps, and Character, based off given name
    private void generateGame(String name) {
        mainGameMap = new GameMap(name);
        mainCharacter = mainGameMap.getMainCharacter();
    }

    // MODIFIES: this
    // EFFECTS: remove name input box and add GamePanel and DisplayInventory
    private void addGraphics() {
        setSize(GameMap.SCREEN_SIZE_WIDTH + 30, GameMap.SCREEN_SIZE_HEIGHT + 215);
        setLocationRelativeTo(null);
        gameOption = new GameOptionPanels(this, mainGameMap);
        gp =  new GamePanel(this, mainGameMap);
        dp = new DisplayInventory(this, mainCharacter);
        resetMainFrame();
        movement = new KeyHandler();
        addKeyListener(movement);
        addTimer();
    }

    // EFFECTS: ends game and disables all actionListeners
    public void trapSetOff() {
        if (mainGameMap.isTrapSetOff()) {
            removeKeyListener(movement);
            gameOption.endGame();
            gp.endDisplayOfGame("Game Lost");
            timer.stop();
            setVisible(true);
        }
    }

    // EFFECTS: ends game and disables all actionListeners
    public void gameWon() {
        if (mainGameMap.gameWon()) {
            removeKeyListener(movement);
            gameOption.endGame();
            gp.endDisplayOfGame("Game Won");
            timer.stop();
            setVisible(true);
        }
    }

    // EFFECTS: loads game of given name
    public void loadGame(String name) {
        String file = "./data/" + name + ".txt";
        try {
            mainGameMap = Reader.readMap(new File(file));
        } catch (IOException e) {
            //nothing
        }
        mainCharacter = mainGameMap.getMainCharacter();
        gp.displayLoadedGame(this, mainGameMap);
        dp.setMainCharacter(mainCharacter);
    }

    // EFFECTS: displays keys in inventory if not empty
    public void displayInventory() {
        if (!displayInventory) {
            displayInventory = true;
            dp.enableDisplay();
        } else {
            dp.disableDisplay();
            displayInventory = false;
        }
        setVisible(true);
    }

    // EFFECTS: asks for input of file name and saves the current state of the game
    public void saveGame() {
        Writer saveGame;
        try {
            saveGame = new Writer(new File("./data/" + mainCharacter.getCharacterName() + ".txt"));
            saveGame.write(mainGameMap);
            saveGame.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: if character inventory is not empty then first item in inventory will be dropped
    public void dropItem() {
        if (!mainCharacter.getInventory().isEmpty()) {
            mainGameMap.addGivenItemToFloor(mainCharacter.dropItem(0));
        }
    }

    // MODIFIES: this
    // EFFECTS: picks up item if one is close enough and inventory of character is not full
    public void pickUpItem() {
        mainCharacter.isPickedUpItem(mainGameMap.getOnFloorKeys());
    }

    // EFFECTS: keyHandler that checks for w/a/s/d and updates character if move was requested
    private class KeyHandler extends KeyAdapter {
        @Override
        // MODIFIES: this
        // EFFECTS:  updates game in response to a keyboard event
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    mainCharacter.moveCharacter("a");
                    break;
                case KeyEvent.VK_S:
                    mainCharacter.moveCharacter("s");
                    break;
                case KeyEvent.VK_D:
                    mainCharacter.moveCharacter("d");
                    break;
                case KeyEvent.VK_W:
                    mainCharacter.moveCharacter("w");
                    break;
                default:
                    break;
            }
        }
    }
}





