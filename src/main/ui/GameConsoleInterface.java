package ui;

import model.Character;
import model.GameMap;
import model.Key;
import model.Trap;
import persistence.Writer;
import persistence.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

//This is the main game application
public class GameConsoleInterface extends JFrame {

    private static final int INTERVAL = 20;
    private Scanner input;
    private JTextField textBox;
    private Character mainCharacter;
    private GameMap mainGameMap;
    private Random rand;
    private boolean keepGoing = true;
    private GamePanel gp;

    // EFFECTS: initializes scanner and starts main loop
    public GameConsoleInterface() {
        super("Maze Search Game");
        input = new Scanner(System.in);
        rand = new Random();
        generateStartOfGame();
        initializeGraphics();
        addKeyListener(new KeyHandler());
        validate();
        addTimer();
        runGameApp();

    }

    // Set up timer
    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gp.repaint();
            }
        });
        t.start();
    }

    // MODIFIES: this
    // EFFECTS: creates main character and displays location, creates keys at random locations and displays location,
    // then prints out input options
    private void generateStartOfGame() {
        System.out.println("Enter the name of your character");
        String name = input.next();
        mainGameMap = new GameMap(name);
        mainCharacter = mainGameMap.getMainCharacter();
        addTraps();
        addKeys(GameMap.NUMBER_OF_KEYS);
    }

    // MODIFIES: this
    // EFFECTS: initializes the JFrame of the app
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        // 215 for the buttons at the bottom
        setSize(GameMap.SCREEN_SIZE_WIDTH + 30, GameMap.SCREEN_SIZE_HEIGHT + 215);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        new GameOptionPanels(this);
        textBox = new JTextField(10);
        add(textBox, BorderLayout.NORTH);
        gp =  new GamePanel(this, mainGameMap);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: main loop the runs the game
    private void runGameApp() {
        String command;
        while (keepGoing) {
            command = input.next();
            if (command.equals("r")) {
                loadGame();
            }
            keepGoing = trapSetOff();
        }
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
    public void loadGame() {
        String file = "./data/" + textBox.getText() + ".txt";
        try {
            mainGameMap = Reader.readMap(new File(file));
        } catch (IOException e) {
            System.out.println("No save under that name was found");
        }
        mainCharacter = mainGameMap.getMainCharacter();
        gp.loadGame(this, mainGameMap);
    }

    // EFFECTS: displays keys in inventory if not empty
    public void displayInventory() {
        if (!mainCharacter.getInventory().isEmpty()) {
            displayKey(mainCharacter.getInventory());
        } else {
            System.out.println("Inventory is Empty");
        }
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
        System.out.println("New save created under " + mainCharacter.getCharacterName());
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

    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    public void displayKey(LinkedList<Key> listOfKeys) {
        boolean pickedUp = listOfKeys.get(0).isPickedUp();
        String name;
        int locationX;
        int locationY;
        System.out.println("Inventory:");
        for (Key k : listOfKeys) {
            name = k.getItemName();
            locationX = k.getLocationX();
            locationY = k.getLocationY();
            if (pickedUp) {
                System.out.println("Slot" + (listOfKeys.indexOf(k) + 1) + " has " + name);
            } else {
                System.out.println(name + " X: " + locationX + " Y: " + locationY);
            }

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





