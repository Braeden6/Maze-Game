package ui;

import model.Character;
import model.GameMap;
import model.Key;
import model.Trap;
import persistence.Writer;
import persistence.Reader;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

//This is the main game application
public class GameConsoleInterface extends JFrame {

    private Scanner input;
    private Character mainCharacter;
    private GameMap mainGameMap;
    private Random rand;

    // EFFECTS: initializes scanner and starts main loop
    public GameConsoleInterface() throws FileNotFoundException, UnsupportedEncodingException {
        super("Maze Search Game");
        setSize(100, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        addKeyListener(new KeyHandler());
        input = new Scanner(System.in);
        rand = new Random();
        generateStartOfGame();
        setVisible(true);
        runGameApp();

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
                    displayCharacter();
                    break;
                case KeyEvent.VK_S:
                    mainCharacter.moveCharacter("s");
                    displayCharacter();
                    break;
                case KeyEvent.VK_D:
                    mainCharacter.moveCharacter("d");
                    displayCharacter();
                    break;
                case KeyEvent.VK_W:
                    mainCharacter.moveCharacter("w");
                    displayCharacter();
                    break;
                default:
                    break;
            }
            trapSetOff();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates main character and displays location, creates keys at random locations and displays location,
    // then prints out input options
    private void generateStartOfGame() {
        System.out.println("Enter the name of your character");
        String name = input.next();
        mainGameMap = new GameMap(name);
        mainCharacter = mainGameMap.getMainCharacter();
        displayCharacter();
        addTraps();
        addKeys(GameMap.NUMBER_OF_KEYS);
        displayKey(mainGameMap.getOnFloorKeys());
        displayInputOptions();
    }

    // MODIFIES: this
    // EFFECTS: main loop the runs the game
    private void runGameApp() throws FileNotFoundException, UnsupportedEncodingException {
        boolean keepGoing = true;
        String command;
        while (keepGoing) {
            command = input.next();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                doAction(command);
                keepGoing = trapSetOff();
            }
        }
    }

    // EFFECTS: ends game if a trap has been set off
    private boolean trapSetOff() {
        if (mainGameMap.isTrapSetOff()) {
            System.out.println("You hit a trap");
            return false;
        }
        return true;
    }

    // EFFECTS: selects and does action based off command enters
    // - t will drop first item in inventory
    // - g will pick up item if possible
    // - k will add a key at a random location on the map
    // - f will save current game
    // - r will load a previous save
    // - o will reprint locations of all the traps on the floor
    // - i will display inventory if not empty
    // - l will reprint locations of all the keys on the floor
    public void doAction(String command) throws FileNotFoundException, UnsupportedEncodingException {
        command = command.toLowerCase();
        switch (command) {
            case "t":
                dropItem();
                break;
            case "g":
                pickUpItem();
                break;
            case "k":
                addKeys(1);
                break;
            case "f":
                saveGame();
                break;
            case "r":
                loadGame();
                break;
            default:
                checkDisplayRequest(command);
                break;
        }
    }

    // EFFECTS: checks to see if a display option has been triggered
    // - o will reprint locations of all the traps on the floor
    // - i will display inventory if not empty
    // - l will reprint locations of all the keys on the floor
    private void checkDisplayRequest(String command) {
        switch (command) {
            case "i":
                displayInventory();
                break;
            case "o":
                displayTraps();
                break;
            case "l":
                displayKey(mainGameMap.getOnFloorKeys());
                break;
        }
    }

    // EFFECTS: loads game of given name
    public void loadGame() {
        System.out.println("Please enter the name of the game you wish to load");
        String file = "./data/" + input.next() + ".txt";
        try {
            mainGameMap = Reader.readMap(new File(file));
        } catch (IOException e) {
            System.out.println("No save under that name was found");
        }
        mainCharacter = mainGameMap.getMainCharacter();
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
    public void saveGame() throws FileNotFoundException, UnsupportedEncodingException {
        Writer saveGame;
        saveGame = new Writer(new File("./data/" + mainCharacter.getCharacterName() + ".txt"));
        saveGame.write(mainGameMap);
        saveGame.close();
        System.out.println("New save created under " + mainCharacter.getCharacterName());
    }

    // MODIFIES: this
    // EFFECTS: if character inventory is not empty then first item in inventory will be dropped
    public void dropItem() {
        if (!mainCharacter.getInventory().isEmpty()) {
            System.out.println("Dropping first key");
            mainGameMap.addGivenKey(mainCharacter.dropItem(0));
        } else {
            System.out.println("Inventory is currently empty, can not drop anything");
        }
    }

    // MODIFIES: this
    // EFFECTS: picks up item if one is close enough and inventory of character is not full
    public void pickUpItem() {
        if (mainCharacter.isPickedUpItem(mainGameMap.getOnFloorKeys())) {
            System.out.println("A key was picked up");
        } else {
            System.out.println("Unable to pick up any key");
        }
    }

    // REQUIRES: amount > 0
    // EFFECTS: adds amount of keys to the ground at random locations and then displays all the key locations
    private void addKeys(int amount) {
        String keyName;
        for (int i = 1; i <= amount; i++) {
            keyName = "key" + (mainGameMap.getOnFloorKeys().size() + 1);
            mainGameMap.addGivenKey(new Key(rand.nextInt(1000), rand.nextInt(1000), keyName));
        }
        displayKey(mainGameMap.getOnFloorKeys());
    }

    // EFFECTS: adds game starting amount of traps to the ground at random locations
    private void addTraps() {
        for (int i = 1; i <= GameMap.NUMBER_OF_TRAPS; i++) {
            mainGameMap.addGivenTrap(new Trap());
        }
    }

    // EFFECTS: displays all possible input option while playing the game
    private void displayInputOptions() {
        System.out.println("Enter Action:");
        System.out.println("- q to quit");
        System.out.println("- w/a/s/d to move");
        System.out.println("- t to drop first item");
        System.out.println("- g to try to pick up item");
        System.out.println("- l to display locations of the keys");
        System.out.println("- o to display locations of traps");
        System.out.println("- i to display inventory");
        System.out.println("- k will add another key");
        System.out.println("- f will save the current game state");
        System.out.println("- r will load the game from a previous save");
    }

    // EFFECTS: display location of all traps
    private void displayTraps() {
        int x;
        int y;
        for (Trap t : mainGameMap.getOnFloorTraps()) {
            x = t.getCenterX();
            y = t.getCenterY();
            System.out.println("Trap" + (mainGameMap.getOnFloorTraps().indexOf(t) + 1) + " X: " + x + " Y: " + y);
        }
    }

    // EFFECTS: display main character name and location
    private void displayCharacter() {
        String name = mainCharacter.getCharacterName();
        int locationX = mainCharacter.getLocationX();
        int locationY = mainCharacter.getLocationY();
        System.out.println(name + " is at" + " X: " + locationX + " Y: " + locationY);
    }

    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    private void displayKey(ArrayList<Key> listOfKeys) {
        boolean pickedUp = listOfKeys.get(0).isPickedUp();
        String name;
        int locationX;
        int locationY;
        if (pickedUp) {
            System.out.println("Inventory:");
        } else {
            System.out.println("Key locations:");
        }
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

}




