package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// REFERENCES: JsonSerializationDemo
public class JsonTest {
    protected void checkCustomer(String name, String phoneNumber, int accountNumber, double balance, Customer customer) {
        assertEquals(name, customer.getName());
        assertEquals(phoneNumber, customer.getPhoneNumber());
        assertEquals(accountNumber, customer.getAccountNumber());
        assertEquals(balance, customer.getBalance());
    }
}
