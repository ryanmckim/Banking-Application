package model;

import model.exceptions.ExistingCustomerException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// A bank that has a list of customers
// REFERENCES: JsonSerializationDemo
public class Bank implements Writable {
    private LinkedList<Customer> bank;

    // EFFECTS: Creates an empty bank
    public Bank() {
        bank = new LinkedList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds customers to this bank
    // REFERENCES: JsonSerializationDemo
    public void addCustomer(Customer customer) {
        bank.add(customer);
    }

    // EFFECTS: returns an unmodifiable list of customers in this bank
    // REFERENCES: JsonSerializationDemo
    public List<Customer> getCustomer() {
        return bank;
    }

    // EFFECTS: returns number of customers in this bank
    // REFERENCES: JsonSerializationDemo
    public int numCustomers() {
        return bank.size();
    }

    // MODIFIES: this
    // EFFECTS: adds the information of a new customer into the bank, unless
    // already in system, then throw a ExistingCustomerException
    public void addNewCustomer(Customer customer) throws ExistingCustomerException {
        for (Customer c : bank) {
            if (c.getPhoneNumber().equals(customer.getPhoneNumber())) {
                throw new ExistingCustomerException();
            }
        }
        bank.add(customer);
    }

    // MODIFIES: this
    // EFFECTS: Removes the information of an existing customer, unless the bank is empty.  Return 0
    // if bank is initially empty, 1 if remove is successful.
    public int removeExistingCustomer(int an) {
        if (bank.isEmpty()) {
            return 0;
        } else {
            for (Customer c : bank) {
                if (c.getAccountNumber() == an) {
                    bank.remove(c);
                    return 1;
                }
            }
        }
        return 1;
    }

    // EFFECTS: Checks the phone number to see if the customer exists.  If
    // they do, return true.  Otherwise return false.
    public boolean checkExistingPhoneNumber(String pn)  {
        for (Customer c : bank) {
            if (pn.equals(c.getPhoneNumber())) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: Returns the customer with the matching account number
    // if found, otherwise return null.
    public Customer accountInformation(int an) {
        for (Customer c : bank) {
            if (an == (c.getAccountNumber())) {
                return c;
            }
        }
        return null;
    }

    // EFFECTS: returns customer if an matches their account number,
    // otherwise return null.
    public Customer returnCustomer(int an) {
        for (Customer c : bank) {
            if (c.getAccountNumber() == an) {
                return c;
            }
        }
        return null;
    }

    // EFFECTS: returns true if balance is greater than the amount
    public boolean enoughBalance(Customer c, double amount) {
        return c.getBalance() >= amount;
    }

    // EFFECTS: returns bank (list of customers)
    public LinkedList<Customer> getBank() {
        return bank;
    }

    // EFFECTS: returns the fields in bank as a JSON array
    // REFERENCES: JsonSerializationDemo
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("customers", customerToJson());
        return json;
    }

    // EFFECTS: returns customers in this bank as a JSON array
    // REFERENCES: JsonSerializationDemo
    private JSONArray customerToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Customer c : bank) {
            jsonArray.put(c.toJson());
        }

        return jsonArray;
    }

}