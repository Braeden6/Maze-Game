package persistence;

import model.*;
import model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

class WriterTest {
    private static final String TEST_FILE = "./data/testMap.txt";
    private Writer testWriter;
    private GameMap testMap;
    Random rand;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        rand = new Random();
        testWriter = new Writer(new File(TEST_FILE));
        testMap = new GameMap("Jeff");
        // add 2 traps to game
        testMap.addGivenTrap(new Trap());
        testMap.addGivenTrap(new Trap());
        // add 2 keys to game
        testMap.addGivenKey(new Key(100,200, "testKey1"));
        testMap.addGivenKey(new Key(250,900, "testKey2"));
    }

    @Test
    void testWriteMap() {
        testWriter.write(testMap);
        testWriter.close();
        try {
            GameMap map = Reader.readMap(new File("./data/testMap.txt"));
            Character character = map.getMainCharacter();
            LinkedList<Item> onFloorKeys = map.getOnFloorKeys();
            //test character info
            assertEquals("Jeff", character.getCharacterName());
            assertEquals(0,character.getLocationX());
            assertEquals(GameMap.SCREEN_SIZE_HEIGHT / 2,character.getLocationY());
            //test onFloorKeys
            assertEquals("testKey1",onFloorKeys.get(0).getItemName());
            assertEquals(100,onFloorKeys.get(0).getLocationX());
            assertEquals(200,onFloorKeys.get(0).getLocationY());
            assertFalse(onFloorKeys.get(0).isPickedUp());
            assertEquals("testKey2",onFloorKeys.get(1).getItemName());
            assertEquals(250,onFloorKeys.get(1).getLocationX());
            assertEquals(900,onFloorKeys.get(1).getLocationY());
            assertFalse(onFloorKeys.get(1).isPickedUp());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}