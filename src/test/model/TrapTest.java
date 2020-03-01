package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TrapTest {
    Trap testTrap;
    int x;
    int y;

    @BeforeEach
    void runBefore() {
        testTrap = new Trap();
        x = testTrap.getLocationX();
        y = testTrap.getLocationY();
    }

    @Test
    void testTrapSetOff() {
        assertTrue(testTrap.isTrapSetOff(x,y));
        assertFalse(testTrap.isTrapSetOff(x - 1 - Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP,y));
        assertFalse(testTrap.isTrapSetOff(x,y - 1 - Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP));
        assertFalse(testTrap.isTrapSetOff(x + 1 + Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP,y));
        assertFalse(testTrap.isTrapSetOff(x,y + 1 + Trap.DISTANCE_FROM_CENTER_TO_TRIGGER_TRAP));
        testTrap.setTrapCenter( 250, 245);
        assertFalse(testTrap.isTrapSetOff(x,y));
    }
}
