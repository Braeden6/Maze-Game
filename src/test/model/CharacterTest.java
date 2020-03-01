package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character testCharacter;
    private ArrayList<Key> testInventory;
    private LinkedList<Key> testFloor;
    private Key key1;
    private Key key2;

    @BeforeEach
    void runBefore() {
        testCharacter = new Character("Bob");
        testInventory = new ArrayList<>();
        testFloor = new LinkedList<>();
    }

    @Test
    void testInventory() {
        assertEquals("Bob",testCharacter.getCharacterName());
        key1 = new Key(400, 200, "key1");
        key2 = new Key(304, 405, "key2");
        testFloor.add(key1);
        testFloor.add(key2);
        for ( int i = 0 ; i < Character.MAX_SIZE_OF_INVENTORY; i++) {
            testFloor.add(new Key(201, 900, "key" ));
        }



        //add first item
        testCharacter.setCharacterLocation(400, 200);
        assertTrue(testCharacter.isPickedUpItem(testFloor));
        testInventory.add(key1);
        assertEquals(testInventory, testCharacter.getInventory());

        // add second item
        testCharacter.setCharacterLocation(300, 401);
        assertTrue(testCharacter.isPickedUpItem(testFloor));
        testInventory.add(key2);
        assertEquals(testInventory, testCharacter.getInventory());

        // add third and last item
        testCharacter.setCharacterLocation(202 + Key.REACH, 899);
        assertFalse(testCharacter.isPickedUpItem(testFloor));
        testCharacter.setCharacterLocation(201 + Key.REACH / 2, 899);
        for (int i = 2 ; i < Character.MAX_SIZE_OF_INVENTORY; i++) {
            assertTrue(testCharacter.isPickedUpItem(testFloor));
        }
        // add past inventory max
        assertFalse(testCharacter.isPickedUpItem(testFloor));
    }

    @Test
    void testDropItem() {
        ArrayList<Key> testFloorRemoved = new ArrayList<>();
        key1 = new Key(400, 200, "key1");
        key2 = new Key(304, 405, "key2");
        testFloor.add(key1);
        testFloor.add(key2);
        testFloor.add(key2);
        testFloorRemoved.add(key1);
        testFloorRemoved.add(key2);
        testFloorRemoved.add(key2);
        testCharacter.setCharacterLocation(400, 200);
        assertTrue(testCharacter.isPickedUpItem(testFloor));
        testFloorRemoved.remove(0);
        assertEquals(testFloor, testFloorRemoved);

        testCharacter.setCharacterLocation(299, 400);
        assertTrue(testCharacter.isPickedUpItem(testFloor));
        testFloorRemoved.remove(0);
        assertEquals(testFloor, testFloorRemoved);

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
        int y = GameMap.SCREEN_SIZE_HEIGHT / 2 + Character.MOVEMENT_DISTANCE;
        assertEquals(y, testCharacter.getLocationY());
        testCharacter.moveCharacter("d");
        assertEquals(10, testCharacter.getLocationX());
        testCharacter.setCharacterLocation(500, 206);
        testCharacter.moveCharacter("d");
        assertEquals(500 + Character.MOVEMENT_DISTANCE, testCharacter.getLocationX());
        testCharacter.moveCharacter("s");
    assertEquals(206 - Character.MOVEMENT_DISTANCE, testCharacter.getLocationY());
    }

    @Test
    void testMoveTopMax() {
        testCharacter.setCharacterLocation(GameMap.SCREEN_SIZE_WIDTH, GameMap.SCREEN_SIZE_HEIGHT);
        testCharacter.moveCharacter("w");
        assertEquals(GameMap.SCREEN_SIZE_HEIGHT, testCharacter.getLocationY());
    }

    @Test
    void testMoveBottomMax() {
        testCharacter.setCharacterLocation(500, 4);
        testCharacter.moveCharacter("s");
        assertEquals(0, testCharacter.getLocationY());
    }

    @Test
    void testMoveRightMax() {
        testCharacter.setCharacterLocation(GameMap.SCREEN_SIZE_WIDTH, GameMap.SCREEN_SIZE_HEIGHT);
        testCharacter.moveCharacter("d");
        assertEquals(GameMap.SCREEN_SIZE_WIDTH, testCharacter.getLocationX());
    }

    @Test
    void testMoveLeftMax() {
        testCharacter.moveCharacter("a");
        testCharacter.moveCharacter("a");
        assertEquals(0, testCharacter.getLocationX());
    }


    @Test
    void testSetLocation() {
        testCharacter.setLocation(100,250);
        assertEquals(100, testCharacter.getLocationX());
        assertEquals(250, testCharacter.getLocationY());
    }

    @Test
    void testIsViewDistance() {
        key1 = new Key(100,100,"key");
        testCharacter.setLocation(100 + Character.VIEW_DISTANCE + 10 ,100);
        assertFalse(testCharacter.isInViewDistance(key1));
        testCharacter.setLocation(100 + Character.VIEW_DISTANCE ,100);
        assertTrue(testCharacter.isInViewDistance(key1));
    }
}

