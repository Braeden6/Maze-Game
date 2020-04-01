package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    private GameMap testMap;
    Key key1;
    Key key2;



    @BeforeEach
    void runBefore() {
        testMap = new GameMap("Bob");
        key1 = new Key(100 ,100, "key1");
        key2 = new Key(1000 ,300, "key2");
        testMap.addGivenItemToFloor(key1);
    }

    @Test
    void testAddKey() {
        Item item;
        int startingIndex = GameMap.NUMBER_OF_KEYS + GameMap.NUMBER_OF_FLASHLIGHTS;
        assertEquals(key1, testMap.getOnFloorKeys().get(startingIndex));
        testMap.addGivenItemToFloor(key2);
        assertEquals(key2, testMap.getOnFloorKeys().get(startingIndex + 1));
        testMap.removeIndexKey(startingIndex);
        assertEquals(key2, testMap.getOnFloorKeys().get(startingIndex));
        testMap.removeIndexKey(startingIndex);
        try {
            item = testMap.getOnFloorKeys().get(startingIndex);
        } catch (Exception e) {
            return;
        }
        assertEquals(item.getItemName(),"fail");
    }

    @Test
    void testSpecificKey() {
        assertEquals("Bob", testMap.getMainCharacter().getCharacterName());
        for (int i = 0; i < 4 ; i++) {
            testMap.addGivenItemToFloor(key1);
        }
        testMap.addGivenItemToFloor(key2);
        for (int i = 0; i < 6 ; i++) {
            testMap.addGivenItemToFloor(key1);
        }
        testMap.addGivenItemToFloor(key2);
        assertEquals(key2,testMap.getItem("key2"));
        assertEquals(key2,testMap.getItem("key2"));
        assertEquals("invalid",testMap.getItem("key2").getItemName());
    }

    @Test
    void testAddTrap() {
        testMap.resetTrapList();
        Trap testTrap = new Trap();
        Trap testTrap2 = new Trap();
        testMap.addGivenTrap(testTrap);
        testMap.addGivenTrap(testTrap2);
        assertEquals(testTrap,testMap.getOnFloorTraps().get(0));
        assertFalse(testMap.isTrapSetOff());
        testTrap2.setLocation(0,GameMap.SCREEN_SIZE_HEIGHT / 2);
        assertTrue(testMap.isTrapSetOff());
    }

    @Test
    void testGameOver1() {
        assertFalse(testMap.gameWon());
        for (int i = 0; i < GameMap.KEY_TO_WIN; i++) {
            new Key(100, 100, "key").pickUpItem(testMap.getMainCharacter());
        }
        assertTrue(testMap.gameWon());
    }
}

