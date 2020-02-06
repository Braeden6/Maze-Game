package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character testCharacter;
    private ArrayList<Key> testInventory;
    private Key key1;
    private Key key2;
    private Key key3;
    private Key key4;

    @BeforeEach
    void runBefore() {
        testCharacter = new Character("Bod");
        testInventory = new ArrayList<>();
    }

    @Test
    void testInventory() {
        key1 = new Key(400, 200, "key1");
        key2 = new Key(304, 405, "key2");
        key3 = new Key(201, 900, "key3");
        key4 = new Key(201, 900, "key4");


        //add first item
        testCharacter.setCharacterLocation(400, 200);
        assertTrue(testCharacter.isPickedUpItem(key1));
        testInventory.add(key1);
        assertEquals(testInventory, testCharacter.getInventory());

        // add second item
        testCharacter.setCharacterLocation(300, 401);
        assertTrue(testCharacter.isPickedUpItem(key2));
        assertFalse(testCharacter.isPickedUpItem(key3));
        testInventory.add(key2);
        assertEquals(testInventory, testCharacter.getInventory());

        // add third and last item
        testCharacter.setCharacterLocation(205, 899);
        assertFalse(testCharacter.isPickedUpItem(key2));
        assertTrue(testCharacter.isPickedUpItem(key3));
        testInventory.add(key3);
        assertEquals(testInventory, testCharacter.getInventory());

        // add past inventory max
        assertFalse(testCharacter.isPickedUpItem(key4));
        assertEquals(testInventory, testCharacter.getInventory());
    }

    @Test
    void testDropItem() {
        key1 = new Key(400, 200, "key1");
        key2 = new Key(304, 405, "key2");
        testCharacter.setCharacterLocation(400, 200);
        assertTrue(testCharacter.isPickedUpItem(key1));
        testCharacter.setCharacterLocation(299, 400);
        assertTrue(testCharacter.isPickedUpItem(key2));

        testCharacter.setCharacterLocation(900, 950);
        assertEquals(key1,testCharacter.dropItem(0));
        assertEquals(900, key1.getLocationX());
        assertEquals(950, key1.getLocationY());

        testInventory.add(key2);
        assertEquals(testInventory, testCharacter.getInventory());
    }

    @Test
    void testGeneralMovement() {
        testCharacter.moveCharacter("w");
        assertEquals(510, testCharacter.getLocationY());
        testCharacter.moveCharacter("d");
        assertEquals(10, testCharacter.getLocationX());
        testCharacter.setCharacterLocation(500, 206);
        testCharacter.moveCharacter("d");
        assertEquals(510, testCharacter.getLocationX());
        testCharacter.moveCharacter("s");
        assertEquals(196, testCharacter.getLocationY());
    }

    @Test
    void testMoveTopMax() {
        testCharacter.setCharacterLocation(999, 999);
        testCharacter.moveCharacter("w");
        assertEquals(1000, testCharacter.getLocationY());
    }

    @Test
    void testMoveBottomMax() {
        testCharacter.setCharacterLocation(500, 4);
        testCharacter.moveCharacter("s");
        assertEquals(0, testCharacter.getLocationY());
    }

    @Test
    void testMoveRightMax() {
        testCharacter.setCharacterLocation(999, 999);
        testCharacter.moveCharacter("d");
        assertEquals(1000, testCharacter.getLocationX());
    }

    @Test
    void testMoveLeftMax() {
        testCharacter.moveCharacter("a");
        testCharacter.moveCharacter("a");
        assertEquals(0, testCharacter.getLocationX());
    }
}

