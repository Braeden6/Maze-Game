package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.Character;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class WriterTest {
    private static final String TEST_FILE = "./data/testMap.txt";
    private Writer testWriter;
    private GameMap testMap;
    Random rand;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        String keyName;
        rand = new Random();
        testWriter = new Writer(new File(TEST_FILE));
        testMap = new GameMap("Jeff");
        // add 2 traps to game
        testMap.addGivenTrap(new Trap());
        testMap.addGivenTrap(new Trap());
        // add 2 keys to game
        testMap.addGivenKey(new Key(100,200, "testKey1"));
        testMap.addGivenKey(new Key(250,900, "testKey2"));
    }

    @Test
    void testWriteMap() {
        testWriter.write(testMap);
        testWriter.close();
    }
}

/*
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        chequing = new Account("Mae", 123.56);
        savings = new Account("Jo", 435.23);
    }

    @Test
    void testWriteAccounts() {
        // save chequing and savings accounts to file

        // now read them back in and verify that the accounts have the expected values
        try {
            List<Account> accounts = Reader.readAccounts(new File(TEST_FILE));
            Account chequing = accounts.get(0);
            assertEquals(1, chequing.getId());
            assertEquals("Mae", chequing.getName());
            assertEquals(123.56, chequing.getBalance());

            Account savings = accounts.get(1);
            assertEquals(2, savings.getId());
            assertEquals("Jo", savings.getName());
            assertEquals(435.23, savings.getBalance());

            // verify that ID of next account created is 3 (checks that nextAccountId was restored)
            Account next = new Account("Chris", 0.00);
            assertEquals(3, next.getId());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}*/
