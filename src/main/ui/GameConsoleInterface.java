package ui;

import model.Character;
import model.GameMap;
import model.Key;
import model.Trap;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

//This is the main game application
public class GameConsoleInterface {

    private Scanner input;
    private Character mainCharacter;
    private GameMap mainGameMap;
    Random rand;

    // EFFECTS: initializes scanner and starts main loop
    public GameConsoleInterface() {
        rand = new Random();
        runGameApp();
    }

    // MODIFIES: this
    // EFFECTS: main loop the runs the game
    private void runGameApp() {
        boolean keepGoing = true;
        String command;
        generateStartOfGame();

        while (keepGoing) {
            if (mainGameMap.isTrapSetOff()) {
                System.out.println("You hit a trap");
                keepGoing = false;
            } else {
                command = input.next();

                if (command.equals("q")) {
                    keepGoing = false;
                } else {
                    doAction(command);
                }
            }
        }
        System.out.println("Game has been Stopped");
    }

    // EFFECTS: selects and does action based off command enters
    // - w/a/s/d  is a move action and displays characters new location
    // - t will drop first item in inventory
    // - g will pick up item if possible
    // - l will reprint locations of all the keys on the floor
    // - o will reprint locations of all the traps on the floor
    // - i will display inventory if not empty
    // - k will add a key at a random location on the map
    private void doAction(String command) {
        command = command.toLowerCase();
        if (command.equals("w") | command.equals("s") | command.equals("a") | command.equals("d")) {
            mainCharacter.moveCharacter(command);
            displayCharacter();
        } else if (command.equals("t")) {
            dropItem();
        } else if (command.equals("g")) {
            pickUpItem();
        } else if (command.equals("l")) {
            displayKey(mainGameMap.getOnFloorKeys());
        } else if (command.equals("i")) {
            if (!mainCharacter.getInventory().isEmpty()) {
                displayKey(mainCharacter.getInventory());
            } else {
                System.out.println("Inventory is Empty");
            }
        } else if (command.equals("k")) {
            addKeys(1);
            displayKey(mainGameMap.getOnFloorKeys());
        } else if (command.equals("o")) {
            displayTraps();
        }
    }

    // MODIFIES: this
    // EFFECTS: if character inventory is not empty then first item in inventory will be dropped
    void dropItem() {
        if (!mainCharacter.getInventory().isEmpty()) {
            System.out.println("Dropping first key");
            mainGameMap.addGivenKey(mainCharacter.dropItem(0));
        } else {
            System.out.println("Inventory is currently empty, can not drop anything");
        }
    }

    // MODIFIES: this
    // EFFECTS: picks up item if one is close enough and inventory of character is not full
    void pickUpItem() {
        if (mainCharacter.isPickedUpItem(mainGameMap.getOnFloorKeys())) {
            System.out.println("A key was picked up");
        } else {
            System.out.println("Unable to pick up any key");
        }
    }


    // MODIFIES: this
    // EFFECTS: creates main character and displays location, creates keys at random locations and displays location,
    // then prints out input options
    private void generateStartOfGame() {
        input = new Scanner(System.in);
        System.out.println("Enter the name of your character");
        String name = input.next();
        mainGameMap = new GameMap(name);
        mainCharacter = mainGameMap.getMainCharacter();
        displayCharacter();
        addTraps(GameMap.NUMBER_OF_TRAPS);
        addKeys(GameMap.NUMBER_OF_KEYS);
        displayKey(mainGameMap.getOnFloorKeys());
        displayInputOptions();
    }

    // REQUIRES: amount > 0
    // EFFECTS: adds amount of keys to the ground at random locations
    private void addKeys(int amount) {
        String keyName;
        for (int i = 1; i <= amount; i++) {
            keyName = "key" + (mainGameMap.getOnFloorKeys().size() + 1);
            mainGameMap.addGivenKey(new Key(rand.nextInt(1000),rand.nextInt(1000),keyName));
        }
    }

    // REQUIRES: amount > 0
    // EFFECTS: adds amount of traps to the ground at random locations
    private void addTraps(int amount) {
        for (int i = 1; i <= amount; i++) {
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
            name  = k.getItemName();
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

/*
    // EFFECTS: saves state of chequing and savings accounts to ACCOUNTS_FILE
    private void saveAccounts() {
        try {
            Writer writer = new Writer(new File(ACCOUNTS_FILE));
            writer.write(cheq);
            writer.write(sav);
            writer.close();
            System.out.println("Accounts saved to file " + ACCOUNTS_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + ACCOUNTS_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

 */