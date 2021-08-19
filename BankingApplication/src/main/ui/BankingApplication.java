package ui;

import model.*;
import model.exceptions.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

// Creates the banking application that the user will use
// REFERENCES: JsonSerializationDemo
public class BankingApplication {
    private static final String JSON_STORE = "./data/portfolio.json";
    private Bank bank;
    private Scanner scanner;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes a new bank
    // REFERENCES: TellerApp
    public BankingApplication() throws FileNotFoundException {
        scanner = new Scanner(System.in);
        bank = new Bank();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBank();
    }

    // MODIFIES: this
    // EFFECTS: takes in user input
    // REFERENCES: TellerApp
    // REFERENCES: JsonSerializationDemo
    private void runBank() {
        boolean isOn = true;
        String cmd = null;

        while (isOn) {
            showFrontPage();
            cmd = scanner.next();
            cmd = cmd.toLowerCase();

            if (cmd.equals("q")) {
                isOn = false;
            } else {
                keyboardCheck(cmd);
            }
        }

        System.out.println("\nProgram will exit!");
    }

    // EFFECTS: will display the menu options to the user
    // REFERENCES: TellerApp
    // REFERENCES: JsonSerializationDemo
    private void showFrontPage() {
        System.out.println("\nPlease select from:");
        System.out.println("\ta -> Add new customer");
        System.out.println("\tr -> Remove existing customer");
        System.out.println("\tm -> Deposit money to an account");
        System.out.println("\tn -> Withdraw money from an account");
        System.out.println("\tv -> View the list of existing customers");
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: will intake user input and run features of bank
    // REFERENCES: TellerApp
    // REFERENCES: JsonSerializationDemo
    private void keyboardCheck(String cmd) {
        if (cmd.equals("r")) {
            goRemoveCustomer();
        } else if (cmd.equals("a")) {
            goAddCustomer();
        } else if (cmd.equals("v")) {
            printBank();
        } else if (cmd.equals("m")) {
            depositMoney();
        } else if (cmd.equals("n")) {
            withdrawMoney();
        } else if (cmd.equals("s")) {
            saveBank();
        } else if (cmd.equals("l")) {
            loadBank();
        } else {
            System.out.println("Please choose a valid selection!");
        }
    }

    // REQUIRES: bank must not be empty
    // MODIFIES: this
    // EFFECTS: Removes customer in bank system.  If they are not in the system, no one is removed.
    private void goRemoveCustomer() {
        if (bank.getBank().isEmpty()) {
            System.out.println("Bank is currently empty.");
        } else {
            System.out.println("Please enter the account number of the customer you wish to delete.");
            int accountNumber = scanner.nextInt();

            if (bank.removeExistingCustomer(accountNumber) == 0) {
                System.out.println("Bank is currently empty.");
            } else if (bank.removeExistingCustomer(accountNumber) == 1) {
                System.out.println("Customer removed!");
            } else {
                System.out.println("This user does not exist.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds customer in bank system.  If they already in the system, no one is added.
    private void goAddCustomer() {
        System.out.println("Please input the customer's first name");
        String name = scanner.next();

        System.out.println("please enter customer's phone number (with no spaces or hyphens).");
        String phoneNumber = scanner.next();

        Random random = new Random();
        int accountNumber = random.nextInt(9000000) + 1000000;
        Customer customer = null;
        try {
            customer = new Customer(name, phoneNumber, accountNumber, 0);
        } catch (InvalidNameException e) {
            System.out.println("The name inputted is not valid.");
        } catch (InvalidPhoneNumberException e) {
            System.out.println("The phone number inputted is not valid.");
        } catch (InvalidAccountNumberException e) {
            System.out.println("The account number inputted is not valid.");
        }
        try {
            bank.addNewCustomer(customer);
        } catch (ExistingCustomerException e) {
            System.out.println("That customer already exists.");
        }
        System.out.println("New customer has been added!");
    }

    // MODIFIES: this
    // EFFECTS: deposits the money into an existing account.
    private void depositMoney() {
        System.out.println("Please enter the account number of the account you wish to deposit the money to.");
        int accountNumber = scanner.nextInt();
        if (bank.returnCustomer(accountNumber) == null) {
            System.out.println("The account you are looking for does not exist.");
        } else {
            System.out.println("How much money would you like to deposit?");
            int amount = scanner.nextInt();
            Customer c = bank.returnCustomer(accountNumber);
            try {
                c.depositMoney(amount);
            } catch (InvalidAmountException e) {
                System.out.println("Amount has to be greater than $0.00");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: withdraws the money from an existing account.  If not enough in the account, no deposit is made.
    private void withdrawMoney() {
        System.out.println("Please enter the account number of the account you wish to withdraw money from.");
        int accountNumber = scanner.nextInt();
        if (bank.returnCustomer(accountNumber) == null) {
            System.out.println("The account you are looking for does not exist.");
        } else {
            System.out.println("How much money would you like to withdraw?");
            int amount = scanner.nextInt();
            Customer c = bank.returnCustomer(accountNumber);

            if (!bank.enoughBalance(c, amount)) {
                System.out.println("There is not enough money in the account!");
            } else {
                try {
                    c.withdrawMoney(amount);
                } catch (InvalidAmountException exception) {
                    System.out.println("The amount has to be greater than $0.00.");
                }
            }
        }
    }

    // REQUIRES: bank must not be empty
    // EFFECTS: shows information of all customers in bank
    private void printBank() {
        if (bank.getBank().isEmpty()) {
            System.out.println("Bank is currently empty.");
        } else {
            System.out.println("Here are the list of customers:");
            for (Customer c : bank.getBank()) {
                System.out.println("Customer's name: " + c.getName());
                System.out.println("Customer's phone number: " + c.getPhoneNumber());
                System.out.println("Customer's account number: " + c.getAccountNumber());
                System.out.println("Customer's balance: " + c.getBalance());
            }
        }
    }

    // EFFECTS: saves the workroom to file
    // REFERENCES: JsonSerializationDemo
    private void saveBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(bank);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    // REFERENCES: JsonSerializationDemo
    private void loadBank() {
        try {
            bank = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}