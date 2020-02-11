package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TrapTest {
    Trap testTrap;
    int x;
    int y;

    @BeforeEach
    void runBefore() {
        testTrap = new Trap();
        x = testTrap.getCenterX();
        y = testTrap.getCenterY();
    }

    @Test
    void testTrapSetOff() {
        assertTrue(testTrap.isTrapSetOff(x,y));
        assertFalse(testTrap.isTrapSetOff(x - 5,y));
        assertFalse(testTrap.isTrapSetOff(x,y - 5));
        assertFalse(testTrap.isTrapSetOff(x + 5,y));
        assertFalse(testTrap.isTrapSetOff(x,y + 5));
        testTrap.setTrapCenter( 250, 245);
        assertFalse(testTrap.isTrapSetOff(x,y));
    }
}
