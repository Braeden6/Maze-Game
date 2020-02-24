package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {
    private GameMap testMap;
    private ArrayList<Key> testKeyList;
    Key key1;
    Key key2;



    @BeforeEach
    void runBefore() {
        testMap = new GameMap("Bob");
        testKeyList = new ArrayList<>();
        key1 = new Key(100 ,100, "key1");
        key2 = new Key(1000 ,300, "key2");
        testKeyList.add(key1);
        testMap.addGivenKey(key1);
    }

    @Test
    void testAddKey() {
        assertEquals(testKeyList, testMap.getOnFloorKeys());
        testKeyList.add(key2);
        testMap.addGivenKey(key2);
        assertEquals(testKeyList, testMap.getOnFloorKeys());
        testKeyList.remove(1);
        testMap.removeIndexKey(1);
        assertEquals(testKeyList, testMap.getOnFloorKeys());
        testKeyList.remove(0);
        testMap.removeIndexKey(0);
        assertEquals(testKeyList, testMap.getOnFloorKeys());
    }

    @Test
    void testSpecificKey() {
        assertEquals("Bob", testMap.getMainCharacter().getCharacterName());
        for (int i = 0; i < 4 ; i++) {
            testMap.addGivenKey(key1);
        }
        testMap.addGivenKey(key2);
        for (int i = 0; i < 6 ; i++) {
            testMap.addGivenKey(key1);
        }
        testMap.addGivenKey(key2);
        assertEquals(key2,testMap.getItem("key2"));
        assertEquals(key2,testMap.getItem("key2"));
        assertEquals("invalid",testMap.getItem("key2").getItemName());
    }

    @Test
    void testAddTrap() {
        Trap testTrap = new Trap();
        Trap testTrap2 = new Trap();
        testMap.addGivenTrap(testTrap);
        testMap.addGivenTrap(testTrap2);
        assertEquals(testTrap,testMap.getOnFloorTraps().get(0));
        assertFalse(testMap.isTrapSetOff());
        testTrap2.setTrapCenter(0,500);
        assertTrue(testMap.isTrapSetOff());
    }
}
