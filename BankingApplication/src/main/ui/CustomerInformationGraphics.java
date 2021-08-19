package ui;

import model.*;
import model.exceptions.InvalidAmountException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Graphical implementation of the Customer Information
public class CustomerInformationGraphics extends BankingApplicationGraphics {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 500;
    private static final int FONT_SIZE = 20;

    private static final String MONEY_SOUND = "./data/money.wav";

    private Customer customer;
    private JPanel uiPanel;
    private JPanel visualPanel;
    private JPanel textPanel;
    private JLabel textBox;
    private JLabel name;
    private JLabel accountNumber;
    private JLabel balance;

    // EFFECTS: initializes individual customer graphics
    public CustomerInformationGraphics(Customer customer) {
        super(customer.getName(), "");
        this.customer = customer;
        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: initialize graphics
    // REFERENCES: SimpleDrawingPlayer
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttons();
        initializeCustomerGraphics();
        getContentPane().add(uiPanel, "West");
        getContentPane().add(textPanel, "North");
        getContentPane().add(visualPanel, "Center");
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    //check
    // MODIFIES: this
    // EFFECTS: initializes the buttons that appear
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void buttons() {
        uiPanel = new JPanel(new GridLayout(3, 1));
        visualPanel = new JPanel(new GridLayout(3, 1));
        depositMoneyButton();
        withdrawMoneyButton();
        exitOptionButton();
        textInput();
    }

    // check
    // MODIFIES: this
    // EFFECTS: initializes individual customer graphics
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void initializeCustomerGraphics() {
        visualPanel = new JPanel(new GridLayout(3, 1));
        name = new JLabel("        Customer Name: " + customer.getName());
        name.setFont(new Font(name.getName(), Font.ITALIC, FONT_SIZE));
        accountNumber = new JLabel("       Account Number: " + customer.getAccountNumber());
        accountNumber.setFont(new Font(accountNumber.getName(), Font.ITALIC, FONT_SIZE));
        balance = new JLabel("        Balance: $" + customer.getBalance());
        balance.setFont(new Font(balance.getName(), Font.ITALIC, FONT_SIZE));
        visualPanel.add(name);
        visualPanel.add(accountNumber);
        visualPanel.add(balance);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to deposit money into the selected account
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void depositMoneyButton() {
        JButton depositMoneyButton = new JButton("Deposit money");
        depositMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                double amount = convertToDouble("How much would you like to deposit?");
                try {
                    customer.depositMoney(amount);
                } catch (InvalidAmountException exception) {
                    JOptionPane.showMessageDialog(uiPanel, "The amount has to be greater than $0.00.");
                }
                sound(MONEY_SOUND);
                balance.setText("      Balance: $" + customer.getBalance());
            }
        });
        uiPanel.add(depositMoneyButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to withdraw money from the selected account
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void withdrawMoneyButton() {
        JButton withdrawMoneyButton = new JButton("Withdraw Money");
        withdrawMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                double amount = convertToDouble("How much would you like to withdraw?");
                try {
                    customer.withdrawMoney(amount);
                } catch (InvalidAmountException exception) {
                    JOptionPane.showMessageDialog(uiPanel, "The amount has to be greater than $0.00.");
                }
                sound(MONEY_SOUND);
                balance.setText("      Balance: $" + customer.getBalance());
            }
        });
        uiPanel.add(withdrawMoneyButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button for exiting the customer information page
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void exitOptionButton() {
        JButton exitButton = new JButton("Return to main menu");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                setVisible(false);
                dispose();
            }
        });
        uiPanel.add(exitButton);
    }

    // check
    // MODIFIES: this
    // EFFECTS: Creates the box to input text
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    private void textInput() {
        textPanel = new JPanel(new GridLayout(1, 1));
        textBox = new JLabel("");
        textPanel.add(textBox);
    }

}