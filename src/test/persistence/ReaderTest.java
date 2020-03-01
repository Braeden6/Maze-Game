package persistence;


import model.Character;
import model.GameMap;
import model.Trap;
import model.Key;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {

    @BeforeEach
    void runBefore() {
        new Reader();
    }


    @Test
    void testParseMap1() {
        try {
            GameMap map = Reader.readMap(new File("./data/testMap1.txt"));
            Character character = map.getMainCharacter();
            ArrayList<Trap> onFloorTraps = map.getOnFloorTraps();
            LinkedList<Key> onFloorKeys = map.getOnFloorKeys();
            LinkedList<Key> inventory = character.getInventory();
           //test character info
            assertEquals("Bobby", character.getCharacterName());
            assertEquals(456,character.getLocationX());
            assertEquals(992,character.getLocationY());
            //test character inventory
            assertEquals("key4",inventory.get(0).getItemName());
            assertEquals(435,inventory.get(0).getLocationX());
            assertEquals(675,inventory.get(0).getLocationY());
            assertTrue(inventory.get(0).isPickedUp());
            assertEquals("key3",inventory.get(1).getItemName());
            assertEquals(1000,inventory.get(1).getLocationX());
            assertEquals(245,inventory.get(1).getLocationY());
            assertTrue(inventory.get(1).isPickedUp());
            //test onFloorKeys
            assertEquals("key1",onFloorKeys.get(0).getItemName());
            assertEquals(333,onFloorKeys.get(0).getLocationX());
            assertEquals(234,onFloorKeys.get(0).getLocationY());
            assertFalse(onFloorKeys.get(0).isPickedUp());
            assertEquals("key2",onFloorKeys.get(1).getItemName());
            assertEquals(250,onFloorKeys.get(1).getLocationX());
            assertEquals(876,onFloorKeys.get(1).getLocationY());
            assertFalse(onFloorKeys.get(1).isPickedUp());
            //test onFloorTraps
            assertEquals(400,onFloorTraps.get(0).getLocationX());
            assertEquals(250,onFloorTraps.get(0).getLocationY());
            assertEquals(350,onFloorTraps.get(1).getLocationX());
            assertEquals(435,onFloorTraps.get(1).getLocationY());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testParseMap2() {
        try {
            GameMap map = Reader.readMap(new File("./data/testMap2.txt"));
            Character character = map.getMainCharacter();
            ArrayList<Trap> onFloorTraps = map.getOnFloorTraps();
            LinkedList<Key> onFloorKeys = map.getOnFloorKeys();
            //test character info
            assertEquals("Fred", character.getCharacterName());
            assertEquals(1000,character.getLocationX());
            assertEquals(1000,character.getLocationY());
            //test onFloorKeys
            assertEquals("key1",onFloorKeys.get(0).getItemName());
            assertEquals(345,onFloorKeys.get(0).getLocationX());
            assertEquals(400,onFloorKeys.get(0).getLocationY());
            assertFalse(onFloorKeys.get(0).isPickedUp());
            assertEquals("key2",onFloorKeys.get(1).getItemName());
            assertEquals(250,onFloorKeys.get(1).getLocationX());
            assertEquals(876,onFloorKeys.get(1).getLocationY());
            assertFalse(onFloorKeys.get(1).isPickedUp());
            //test onFloorTraps
            assertEquals(400,onFloorTraps.get(0).getLocationX());
            assertEquals(250,onFloorTraps.get(0).getLocationY());
            assertEquals(350,onFloorTraps.get(1).getLocationX());
            assertEquals(435,onFloorTraps.get(1).getLocationY());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

}