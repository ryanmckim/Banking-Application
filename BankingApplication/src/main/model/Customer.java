package model;

import model.exceptions.InvalidNameException;
import model.exceptions.InvalidPhoneNumberException;
import model.exceptions.InvalidAccountNumberException;
import model.exceptions.InvalidAmountException;
import org.json.JSONObject;
import persistence.Writable;

// Contains fields that has information about the customers
public class Customer implements Writable {

    private final String name;
    private final String phoneNumber;
    private final int accountNumber;
    private double balance;

    // EFFECTS: initializes a new customer with given name, phone number, account number, and balance.
    // If name, phone number, or balance has an invalid input, throw exceptions accordingly.
    public Customer(String name, String phoneNumber, int accountNumber, double balance) throws InvalidNameException,
            InvalidPhoneNumberException, InvalidAccountNumberException {
        if (name.length() == 0) {
            throw new InvalidNameException();
        } else if (phoneNumber.length() != 10) {
            throw new InvalidPhoneNumberException();
        } else if (accountNumber < 1000000 || accountNumber > 9999999) {
            throw new InvalidAccountNumberException();
        } else {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.accountNumber = accountNumber;
            this.balance = balance;
        }
    }

    // EFFECTS: returns customer's name
    public String getName() {
        return name;
    }

    // EFFECTS: returns the ten digit phone number of customer
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // EFFECTS: returns customer's account number
    public int getAccountNumber() {
        return accountNumber;
    }

    // MODIFIES: this
    // EFFECTS: sets customer's current balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    // EFFECTS: returns customer's current balance
    public double getBalance() {
        return balance;
    }

    // MODIFIES: this
    // EFFECTS: deposits amount to customer's existing balance.
    public void depositMoney(double amount) throws InvalidAmountException {
        if (amount <= 0.00) {
            throw new InvalidAmountException();
        } else {
            this.balance += amount;
        }
    }

    // MODIFIES: this
    // EFFECTS: withdraws amount to customer's existing balance.
    public void withdrawMoney(double amount) throws InvalidAmountException {
        if (amount <= 0.00) {
            throw new InvalidAmountException();
        } else {
            this.balance -= amount;
        }
    }


    // EFFECTS: returns the fields in this customer as a JSON array
    // REFERENCES: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("phoneNumber", phoneNumber);
        json.put("accountNumber", accountNumber);
        json.put("balance", balance);
        return json;
    }

}