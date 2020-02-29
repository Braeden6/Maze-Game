package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeyTest {
    private Key testKey;

    @BeforeEach
    void runBefore() {
        testKey = new Key( 100, 200, "testKey");
    }

    @Test
    void testisPickUp() {
        assertEquals("testKey", testKey.getItemName());
        assertFalse(testKey.isAbleToPickUp(101 + Key.REACH,201 + Key.REACH));
        assertFalse(testKey.isAbleToPickUp(99 - Key.REACH,199 - Key.REACH));
        assertFalse(testKey.isAbleToPickUp(100,199 - Key.REACH));
        assertFalse(testKey.isAbleToPickUp(99 - Key.REACH,200));
        assertTrue(testKey.isAbleToPickUp(100 - Key.REACH,200 - Key.REACH));
    }

    @Test
    void testPickUpAndDrop(){
        assertFalse(testKey.isPickedUp());
        testKey.pickUpItem();
        assertTrue(testKey.isPickedUp());
        testKey.dropItem(500,602);
        assertFalse(testKey.isPickedUp());
        assertEquals(500, testKey.getLocationX());
        assertEquals(602, testKey.getLocationY());
    }



}
