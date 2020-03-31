package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlashlightTest {

    private Flashlight testFlashlight;
    private Character testCharacter;

    @BeforeEach
    void runBefore() {
        testFlashlight = new Flashlight("testFlashlight",0, 0);
        testCharacter = new Character("bob");
    }

    @Test
    void testisPickUp() {
        testFlashlight.setLocation(100,200);
        assertEquals("testFlashlight", testFlashlight.getItemName());
        assertFalse(testFlashlight.isAbleToPickUp(101 + Flashlight.REACH,201 + Flashlight.REACH));
        assertFalse(testFlashlight.isAbleToPickUp(99 - Flashlight.REACH,199 - Flashlight.REACH));
        assertFalse(testFlashlight.isAbleToPickUp(100,199 - Flashlight.REACH));
        assertFalse(testFlashlight.isAbleToPickUp(99 - Flashlight.REACH,200));
        assertTrue(testFlashlight.isAbleToPickUp(100 - Flashlight.REACH,200 - Flashlight.REACH));
    }

    @Test
    void testPickUpAndDrop(){
        assertFalse(testFlashlight.isPickedUp());
        testFlashlight.pickUpItem(testCharacter);
        assertTrue(testFlashlight.isPickedUp());
        assertEquals(175,testCharacter.getViewDistance());
        testFlashlight.dropItem(500,602);
        assertFalse(testFlashlight.isPickedUp());
        assertEquals(500, testFlashlight.getLocationX());
        assertEquals(602, testFlashlight.getLocationY());
        testFlashlight.dropItem(testCharacter);
        assertEquals(125,testCharacter.getViewDistance());
    }



}
