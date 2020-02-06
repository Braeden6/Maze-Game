package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class KeyTest {
    Key testKey;

    @BeforeEach
    void runBefore() {
        testKey = new Key( 100, 200);
    }

    @Test
    void testisPickUp() {
        assertFalse(testKey.isPickup(106,206));
        assertFalse(testKey.isPickup(94,194));
        assertFalse(testKey.isPickup(95,194));
        assertFalse(testKey.isPickup(94,195));
        assertTrue(testKey.isPickup(95,195));
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
