package ui;

import model.Character;
import model.Key;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

//This is the main game application
public class GameApp {
    private static int NUMBER_OF_KEYS = 4;

    private Scanner input;
    private Character mainCharacter;
    private ArrayList<Key> onFloorKeys;

    // EFFECTS: initializes scanner and starts main loop
    public GameApp() {
        runGameApp();
    }

    // MODIFIES: this
    // EFFECTS: main loop the runs the game
    private void runGameApp() {
        boolean keepGoing = true;
        String command;
        generateStartOfGame();

        while (keepGoing) {
            displayMap();
            System.out.println("Enter Action:");
            System.out.println("- q to quit\n- w/a/s/d to move\n- t to drop first item\n- g to try to pick up item");
            command = input.next();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                doAction(command);
            }
        }
        System.out.println("Game has been Stopped");
    }

    // EFFECTS: selects and does action based off command enters
    // - w/a/s/d  is a move action
    // - t will drop first item in inventory
    // - g will pick up item if possible
    private void doAction(String command) {
        if (command.equals("w") | command.equals("s") | command.equals("a") | command.equals("d")) {
            mainCharacter.moveCharacter(command);
        } else if (command.equals("t")) {
            dropItem();
        } else if (command.equals("g")) {
            pickUpItem();
        }
    }

    // MODIFIES: this
    // EFFECTS: if character inventory is not empty then first item in inventory will be dropped
    void dropItem() {
        if (!mainCharacter.getInventory().isEmpty()) {
            System.out.println("Dropping first key");
            onFloorKeys.add(mainCharacter.dropItem(0));
        } else {
            System.out.println("Inventory is currently empty, can not drop anything");
        }
    }

    // MODIFIES: this
    // EFFECTS: picks up item if one is close enough and inventory of character is not full
    void pickUpItem() {
        if (mainCharacter.isPickedUpItem(onFloorKeys)) {
            System.out.println("A key was picked up");
        } else {
            System.out.println("Unable to pick up any key");
        }
    }


    // MODIFIES: this
    // EFFECTS: creates keys at random locations and generates main character based off console input name
    private void generateStartOfGame() {
        input = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Enter the name of your character");
        String name = input.next();
        mainCharacter = new Character(name);
        onFloorKeys = new ArrayList<>();
        for (int i = 1; i <= NUMBER_OF_KEYS; i++) {
            onFloorKeys.add(new Key(rand.nextInt(1000),rand.nextInt(1000),"key" + i));
        }
    }

    // EFFECTS: display main character name and location and display all keys on the floor
    private void displayMap() {
        String name = mainCharacter.getCharacterName();
        int locationX = mainCharacter.getLocationX();
        int locationY = mainCharacter.getLocationY();
        System.out.println(name + " is at" + " X: " + locationX + " Y: " + locationY);
        if (!mainCharacter.getInventory().isEmpty()) {
            System.out.println("Inventory:");
            displayKey(mainCharacter.getInventory());
        }
        System.out.println("Key locations:");
        displayKey(onFloorKeys);
    }

    //EFFECTS: displays key's names in given list. If they are the ground then the location will be displayed also.
    private void displayKey(ArrayList<Key> listOfKeys) {
        String name;
        int locationX;
        int locationY;
        for (Key k : listOfKeys) {
            name  = k.getItemName();
            locationX = k.getLocationX();
            locationY = k.getLocationY();
            if (k.isPickedUp()) {
                System.out.println("Slot " + (listOfKeys.indexOf(k) + 1) + "has " + name);
            } else {
                System.out.println(name + " X: " + locationX + " Y: " + locationY);
            }

        }
    }
}