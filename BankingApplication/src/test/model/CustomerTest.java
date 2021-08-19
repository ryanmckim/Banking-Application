package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.DynAnyPackage.Invalid;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {
    private Bank bank;
    private Customer c1;
    private Customer c2;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        try {
            c1 = new Customer("Bob", "1234567890", 1234567, 100.00);
        } catch (Exception e) {
            fail("No values were violated.");
        }
        try {
            c2 = new Customer("Steve", "0987654321", 7654321, 0.00);
        } catch (Exception e) {
            fail("No values were violated");
        }
    }

    @Test
    public void testSetBalance() {
        c1.setBalance(1.00);
        assertEquals(c1.getBalance(), 1.00);
    }

    @Test
    public void testInvalidName() {
        try {
            new Customer("", "1234567890", 1234567, 10.00);
        } catch (InvalidNameException e) {
            // Expected
        } catch (InvalidPhoneNumberException e) {
            fail("InvalidPhoneNumberException was not supposed to be thrown.");
        } catch (InvalidAccountNumberException e) {
            fail ("InvalidAccountNumberException was not supposed to be thrown.");
        }
    }

    @Test
    public void testInvalidPhoneNumber() {
        try {
            new Customer("Ryan", "0292", 1234567, 10.00);
        } catch (InvalidNameException e) {
            fail("InvalidNameException was not supposed to be thrown.");
        } catch (InvalidPhoneNumberException e) {
            // Expected
        } catch (InvalidAccountNumberException e) {
            fail ("InvalidAccountNumberException was not supposed to be thrown.");
        }
    }

    @Test
    public void testInvalidAccountNumberLessThan() {
        try {
            new Customer("Bob", "1234567890", 111, 10.00);
        } catch (InvalidNameException e) {
            fail ("InvalidNameException was not supposed to be thrown.");
        } catch (InvalidPhoneNumberException e) {
            fail("InvalidPhoneNumberException was not supposed to be thrown.");
        } catch (InvalidAccountNumberException e) {
            // Expected
        }
    }

    @Test
    public void testInvalidAccountNumberGreaterThan() {
        try {
            new Customer("Bob", "1234567890", 1203129839, 10.00);
        } catch (InvalidNameException e) {
            fail ("InvalidNameException was not supposed to be thrown.");
        } catch (InvalidPhoneNumberException e) {
            fail("InvalidPhoneNumberException was not supposed to be thrown.");
        } catch (InvalidAccountNumberException e) {
            // Expected
        }
    }

    @Test
    public void testDepositMoney() {
        try {
            bank.addNewCustomer(c1);
        } catch (ExistingCustomerException e) {
            fail("This is a new customer.");
        }
        assertEquals(bank.getBank().size(), 1);
        try {
            c1.depositMoney(1000.00);
        } catch (InvalidAmountException e) {
            fail("The amount was valid.");
        }
        assertEquals(c1.getBalance(), 1100.00);
        try {
            c1.depositMoney(-500.00);
        } catch (InvalidAmountException e) {
            // Expected
        }
    }

    @Test
    public void testWithdrawMoney() {
        try {
            bank.addNewCustomer(c1);
        } catch (ExistingCustomerException e) {
            fail("This is a new customer.");
        }
        assertEquals(bank.getBank().size(), 1);
        assertEquals(c1.getBalance(), 100.00);
        try {
            c1.withdrawMoney(10.00);
        } catch (InvalidAmountException e) {
            fail("Amount was not invalid");
        }
        assertEquals(c1.getBalance(), 90.00);
        try {
            c1.withdrawMoney(-10.00);
        } catch (InvalidAmountException e) {
            // Expected
        }
    }

    @Test
    public void testJsonInvalidName() {
        try {
            new Customer("", "0000000000", 1111111, 0.00);
        } catch (InvalidNameException e) {
            // expected
        } catch (InvalidPhoneNumberException e) {
            fail("Not supposed to catch.");
        } catch (InvalidAccountNumberException e) {
            fail("Not supposed to catch.");
        }
    }

    @Test
    public void testJsonInvalidPhoneNumber() {
        try {
            new Customer("Bob", "1", 1111111, 0.00);
        } catch (InvalidNameException e) {
            fail("Not supposed to catch.");
        } catch (InvalidPhoneNumberException e) {
            // expected
        } catch (InvalidAccountNumberException e) {
            fail("Not supposed to catch.");
        }
    }

    @Test
    public void testJsonInvalidAccountNumber() {
        try {
            new Customer("Bob", "0000000000", 2, 0.00);
        } catch (InvalidNameException e) {
            fail("Not supposed to catch.");
        } catch (InvalidPhoneNumberException e) {
            fail("Not supposed to catch.");
        } catch (InvalidAccountNumberException e) {
            // expected
        }
    }

    @Test
    public void testGetName() {
        assertEquals(c1.getName(), "Bob");
    }

    @Test
    public void testGetLastFourDigitsOfPhoneNumber() {
        assertEquals(c1.getPhoneNumber(), "1234567890");
    }

    @Test
    public void testGetAccountNumber() {
        assertEquals(c1.getAccountNumber(), 1234567);
    }

    @Test
    public void testGetBalance() {
        assertEquals(c1.getBalance(), 100.0);
    }

}