package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

// REFERENCES: JsonSerializationDemo
class JsonReaderTest extends JsonTest {

    // REFERENCES: JsonSerializationDemo
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Bank bank = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // REFERENCES: JsonSerializationDemo
    @Test
    void testReaderEmptyBank() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBank.json");
        try {
            Bank bank = reader.read();
            assertEquals(0, bank.getBank().size());
        } catch (IOException e) {
            fail("Failed.");
        }
    }

    // REFERENCES: JsonSerializationDemo
    @Test
    void testReaderGeneralBank() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralBank.json");
        try {
            Bank bank = reader.read();
            LinkedList<Customer> customers = bank.getBank();
            assertEquals(2, customers.size());
            checkCustomer("Steven", "1234567890", 1234567, 1000, customers.get(0));
            checkCustomer("Dave", "0987654321", 7654321, 500, customers.get(1));
        } catch (IOException e) {
            fail("Failed.");
        }
    }

    @Test
    public void testReaderException() {
        JsonReader reader = new JsonReader("./data/testReaderException.json");
        try {
            Bank bank = reader.read();
            LinkedList<Customer> customers = bank.getBank();
            assertEquals(2, customers.size());
            checkCustomer("Ryan", "1234567890", 1234567, 0.00, customers.get(0));
        } catch (IOException e) {
            fail("Failed.");
        }
    }

}