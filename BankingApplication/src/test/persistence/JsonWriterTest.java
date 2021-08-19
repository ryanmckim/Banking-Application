package persistence;

import model.*;
import model.exceptions.InvalidAccountNumberException;
import model.exceptions.InvalidNameException;
import model.exceptions.InvalidPhoneNumberException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// REFERENCES: JsonSerializationDemo
class JsonWriterTest extends JsonTest {

    // REFERENCES: JsonSerializationDemo
    @Test
    void testWriterInvalidFile() {
        try {
            Bank bank = new Bank();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // REFERENCES: JsonSerializationDemo
    @Test
    void testWriterEmptyBank() {
        try {
            Bank bank = new Bank();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBank.json");
            writer.open();
            writer.write(bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBank.json");
            bank = reader.read();
            assertEquals(0, bank.numCustomers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // REFERENCES: JsonSerializationDemo
    @Test
    void testWriterGeneralBank() {
        try {
            Bank bank = new Bank();
            bank.addCustomer(noExceptionCustomer("Bob", "0000000000", 1111111, 0));
            bank.addCustomer(noExceptionCustomer("Lee", "1111111111", 2222222, 0));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralBank.json");
            writer.open();
            writer.write(bank);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralBank.json");
            bank = reader.read();
            List<Customer> customers = bank.getCustomer();
            assertEquals(2, bank.getCustomer().size());
            checkCustomer("Bob", "0000000000", 1111111, 0.00, customers.get(0));
            checkCustomer("Lee", "1111111111", 2222222, 0.00, customers.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    public Customer noExceptionCustomer(String name, String phoneNumber, int accountNumber, double balance) {
        try {
            return new Customer(name, phoneNumber, accountNumber, balance);
        } catch (Exception e) {
            fail("Failed");
        }
        return null;
    }

}