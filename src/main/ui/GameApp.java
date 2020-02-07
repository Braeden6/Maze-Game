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
        boolean isPickedUp = false;
        if (command.equals("w") | command.equals("s") | command.equals("a") | command.equals("d")) {
            mainCharacter.moveCharacter(command);
        } else if (command.equals("t")) {
            isPickedUp = mainCharacter.isPickedUpItem(onFloorKeys);
            if (isPickedUp) {
                System.out.println("A key was picked up");
            }
        } else if (command.equals("g")) {
            System.out.println("Pickup item");
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
        String name = mainCharacter.getCharaceterName();
        int locationX = mainCharacter.getLocationX();
        int locationY = mainCharacter.getLocationY();
        System.out.println(name + " is at" + " X: " + locationX + " Y: " + locationY);
        System.out.println("Key locations:");
        displayKey();
    }

    //EFFECTS: displays key name then location x and y
    private void displayKey() {
        String name;
        int locationX;
        int locationY;
        for (Key k : onFloorKeys) {
            name  = k.getItemName();
            locationX = k.getLocationX();
            locationY = k.getLocationY();
            System.out.println(name + " X: " + locationX + " Y: " + locationY);
        }
    }
}