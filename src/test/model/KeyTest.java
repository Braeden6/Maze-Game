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
        assertFalse(testKey.isAbleToPickUp(106,206));
        assertFalse(testKey.isAbleToPickUp(94,194));
        assertFalse(testKey.isAbleToPickUp(95,194));
        assertFalse(testKey.isAbleToPickUp(94,195));
        assertTrue(testKey.isAbleToPickUp(95,195));
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
