package ui;

import model.Character;
import model.GameMap;
import model.Key;
import model.Trap;
import persistence.Writer;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

//This is the main game application
public class GameConsoleInterface extends JFrame {

    private static final int INTERVAL = 20;
    private Character mainCharacter;
    private boolean displayInventory = false;
    private GameMap mainGameMap;
    private Random rand;
    private GamePanel gp;
    private DisplayInventory dp;

    //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private JPanel enterName;
    JTextField nameInput;
    JButton submit;

    // EFFECTS: initializes scanner and starts main loop
    public GameConsoleInterface() {
        super("Search Game");
        rand = new Random();
        initializeGame();
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
        Timer t = new Timer(INTERVAL, ae -> {
            gp.repaint();
            if (displayInventory) {
                dp.repaint();
            }

        });
        t.start();
    }

    // MODIFIES: this
    // EFFECTS: initializes the JFrame of the app
    private void initializeGame() {
        setLayout(new BorderLayout());
        // 215 for the buttons at the bottom
        setSize(400, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        enterName = new JPanel();
        enterName.setLayout(new GridLayout(0,1));
        enterName.setSize(0, 0);
        nameInput = new JTextField(10);

        submit = new JButton("Submit");

        submit.addActionListener(e -> {
            String name = nameInput.getText();
            generateGame(name);
            addGraphics();
        });
        addLabels();
        add(enterName, BorderLayout.SOUTH);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add text to the JFrame used to enter name
    private void addLabels() {
        JLabel label1 = new JLabel("  Enter Character");
        JLabel label2 = new JLabel("    Name Below");
        JLabel label3 = new JLabel();
        label1.setFont(new Font("Monospaced", Font.BOLD, 35));
        label2.setFont(new Font("Monospaced", Font.BOLD, 35));
        enterName.add(label1);
        enterName.add(label2);
        enterName.add(label3);
        enterName.add(nameInput);
        enterName.add(submit);
    }

    // MODIFIES: this
    // EFFECTS: generates GameMap, Keys, Traps, and Character, based off given name
    private void generateGame(String name) {
        mainGameMap = new GameMap(name);
        mainCharacter = mainGameMap.getMainCharacter();
        addTraps();
        addKeys(GameMap.NUMBER_OF_KEYS);
    }

    // MODIFIES: this
    // EFFECTS: remove name input box and add GamePanel and DisplayInventory
    private void addGraphics() {
        remove(enterName);
        setSize(GameMap.SCREEN_SIZE_WIDTH + 30, GameMap.SCREEN_SIZE_HEIGHT + 215);
        new GameOptionPanels(this);
        gp =  new GamePanel(this, mainGameMap);
        dp = new DisplayInventory(this, mainCharacter);
        resetMainFrame();
        addKeyListener(new KeyHandler());
        addTimer();
    }



    // EFFECTS: ends game if a trap has been set off
    public boolean trapSetOff() {
        if (mainGameMap.isTrapSetOff()) {
            System.out.println("You hit a trap");
            return false;
        }
        return true;
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
        gp.loadGame(this, mainGameMap);
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
            mainGameMap.addGivenKey(mainCharacter.dropItem(0));
        }
    }

    // MODIFIES: this
    // EFFECTS: picks up item if one is close enough and inventory of character is not full
    public void pickUpItem() {
        mainCharacter.isPickedUpItem(mainGameMap.getOnFloorKeys());
    }

    // REQUIRES: amount > 0
    // EFFECTS: adds amount of keys to the ground at random locations and then displays all the key locations
    public void addKeys(int amount) {
        int w = GameMap.SCREEN_SIZE_WIDTH;
        int h = GameMap.SCREEN_SIZE_HEIGHT;
        String keyName;
        for (int i = 1; i <= amount; i++) {
            keyName = "key" + (mainGameMap.getOnFloorKeys().size() + 1);
            mainGameMap.addGivenKey(new Key(rand.nextInt(w), rand.nextInt(h), keyName));
        }
    }

    // EFFECTS: adds game starting amount of traps to the ground at random locations
    private void addTraps() {
        for (int i = 1; i <= GameMap.NUMBER_OF_TRAPS; i++) {
            mainGameMap.addGivenTrap(new Trap());
        }
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
                    mainCharacter.moveCharacter("w");
                    break;
                case KeyEvent.VK_D:
                    mainCharacter.moveCharacter("d");
                    break;
                case KeyEvent.VK_W:
                    mainCharacter.moveCharacter("s");
                    break;
                default:
                    break;
            }
            trapSetOff();
        }
    }
}





