package model;

import model.exceptions.ExistingCustomerException;
import model.exceptions.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {
    private Bank bank;
    private Customer customer;
    private Customer c;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        try {
            customer = new Customer("Bob", "1234567890", 1234567, 0.00);
        } catch (Exception e) {
            fail("No values were violated");
        }
        try {
            c = new Customer("mmm", "1010101010", 1029485, 0.00);
        } catch (Exception e) {
            fail("No values were violated");
        }
    }

    @Test
    public void testAddNewCustomer() {
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        assertEquals(bank.getBank().size(), 1);
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            // Expected
        }
        try {
            bank.addNewCustomer(c);
        } catch (ExistingCustomerException e) {
            fail("This is a new customer");
        }
    }

    @Test
    public void testRemoveExistingCustomer() {
        assertEquals(bank.getBank().size(), 0);
        assertEquals(bank.removeExistingCustomer(0000000), 0);
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        assertEquals(bank.getBank().size(), 1);
        assertEquals(bank.removeExistingCustomer(1111111), 1);
        assertEquals(bank.removeExistingCustomer(1234567), 1);
        assertEquals(bank.getBank().size(), 0);
    }

        @Test
    public void testCheckExistingPhoneNumber() {
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        assertTrue(bank.checkExistingPhoneNumber("1234567890"));
        assertFalse(bank.checkExistingPhoneNumber("0000000000"));
    }

    @Test
    public void testAccountInformation() {
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            // Expected
        }
        assertEquals(bank.accountInformation(1234567), customer);
        assertNull(bank.accountInformation(0000000));
    }

    @Test
    public void testReturnCustomer() {
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        assertEquals(bank.returnCustomer(1234567), customer);
        assertNull(bank.returnCustomer(0000000));
    }

    @Test
    public void testEnoughBalance() {
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            fail("Adding failed.");
        }
        try {
            customer.depositMoney(100.00);
        } catch (InvalidAmountException e) {
            fail("The amount was not invalid.");
        }
        assertEquals(customer.getBalance(), 100.00);
        assertTrue(bank.enoughBalance(customer, 50.00));
        assertFalse(bank.enoughBalance(customer, 200.00));
    }
}