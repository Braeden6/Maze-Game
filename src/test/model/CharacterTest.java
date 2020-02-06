package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    // delete or rename this class!
}

/*

package ca.ubc.cpsc210.bank.model;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    private Account testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new Account("Jane", 500.0);
    }

    @Test
    void testConstructor() {
        assertEquals("Jane", testAccount.getName());
        assertEquals(500.0, testAccount.getBalance());
        assertTrue(testAccount.getId() > 0);
    }

    @Test
    void testConstructorNegBalance() {
        testAccount = new Account("Matt", -25.0);
        assertEquals("Matt", testAccount.getName());
        assertEquals(0, testAccount.getBalance());
        assertTrue(testAccount.getId() > 0);
    }

    @Test
    void testDeposit() {
        testAccount.deposit(4.49);
        assertEquals(504.49, testAccount.getBalance());
    }

    @Test
    void testWithdraw() {
        testAccount.withdraw(150.50);
        assertEquals(349.50, testAccount.getBalance());
    }

    @Test
    void testMultipleDeposits() {
        testAccount.deposit(150.0);
        testAccount.deposit(1.49);
        assertEquals(651.49, testAccount.getBalance());
    }

    @Test
    void testMultipleWithdrawals() {
        testAccount.withdraw(150.0);
        testAccount.withdraw(2.50);
        assertEquals(347.50, testAccount.getBalance());
    }

    @Test
    void testToString() {
        assertTrue( testAccount.toString().contains("name = Jane, balance = $500.00"));
    }
}*/
