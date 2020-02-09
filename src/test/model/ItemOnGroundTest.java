package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemOnGroundTest {
    private ItemOnGround testList;
    private ArrayList<Key> testOnFloor;



    @BeforeEach
    void runBefore() {
        testList = new ItemOnGround();
        testOnFloor = new ArrayList<>();
    }

    @Test
    void testAddKey() {
        Key key1 = new Key(100 ,100, "key1");
        Key key2 = new Key(1000 ,300, "key2");
        testOnFloor.add(key1);
        testList.addGivenKey(key1);
        assertEquals(testOnFloor,testList.getOnFloorKeys());
        testOnFloor.add(key2);
        testList.addGivenKey(key2);
        assertEquals(testOnFloor,testList.getOnFloorKeys());
        testOnFloor.remove(1);
        testList.removeIndexKey(1);
        assertEquals(testOnFloor,testList.getOnFloorKeys());
        testOnFloor.remove(0);
        testList.removeIndexKey(0);
        assertEquals(testOnFloor,testList.getOnFloorKeys());
    }
}

