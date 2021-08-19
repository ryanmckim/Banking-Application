package ui;

import model.*;

import model.exceptions.ExistingCustomerException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

// Graphical implementation of the Banking Application
public class BankingApplicationGraphics extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/bank.json";

    private static final String MONEY_IMAGE = "./data/Money Picture.JPG";
    protected static final String CLICKING_SOUND = "./data/Clicking Sound.wav";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JLabel text;
    private Bank bank;
    private JPanel userInputPanel;
    private JPanel customerInformation;
    private JPanel customersPanel;
    private Customer removeCustomer;
    private JPanel textPanel;
    private JLabel textBox;
    private JFrame frameDecision;
    private JPanel panelDecision;

    // EFFECTS: initializes banking application graphics
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // SimpleDrawingPlayer
    // check
    public BankingApplicationGraphics(String name, String command) {
        super(name);
        if (command.equals("go!")) {
            initializeFields();
            initializeGraphics();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields when starting program
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // https://www.edureka.co/blog/java-jframe/
    // SimpleDrawingPlayer
    private void initializeFields() {
        bank = new Bank();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        text = new JLabel("");
        userInputPanel = new JPanel(new GridLayout(5, 1));
        customersPanel = new JPanel();
        textBox = new JLabel("");
        textPanel = new JPanel(new GridLayout(1, 1));
        frameDecision = new JFrame();
        panelDecision = new JPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the fields when starting program
    // REFERENCES: SimpleDrawingPlayer
    private void initializeGraphics() {
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttons();
        getContentPane().add(userInputPanel, "South");
        getContentPane().add(textPanel, "North");
        getContentPane().add(customersPanel);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the graphics when starting the program
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // StackOverflow
    private void buttons() {
        addCustomerButton();
        removeCustomerButton();
        saveButton();
        loadButton();
        createTextBox();
        quitButton();
    }

    // MODIFIES: this
    // EFFECTS: creates the button to add new customer
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Java Oracle Doc
    private void addCustomerButton() {
        JButton addCustomerButton = new JButton("Add new customer");
        addCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                Customer c = createCustomerInformation();
                checkExistingCustomer(c);
            }
        });
        userInputPanel.add(addCustomerButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to remove new customer
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Java Oracle Doc
    private void removeCustomerButton() {
        JButton removeCustomerButton = new JButton("Remove existing customer");
        removeCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                int accountNumber = convertToInt(
                        "Please enter the account number of the customer you wish to remove: ");
                removeCustomer = bank.returnCustomer(accountNumber);
                if (removeCustomer == null) {
                    JOptionPane.showMessageDialog(userInputPanel, "That customer does not exist!");
                } else {
                    confirmDecision("Do you wish to delete this customer from the bank?", "delete");
                    textBox.setText("The customer has been deleted from the bank.");
                }

            }
        });
        userInputPanel.add(removeCustomerButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to save the banking session
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Java Oracle Doc
    private void saveButton() {
        JButton saveButton = new JButton("Save banking session");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                saveBank();
            }
        });
        userInputPanel.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to load a previous banking session
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Java Oracle Doc
    private void loadButton() {
        JButton loadButton = new JButton("Load banking session");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                try {
                    bank = jsonReader.read();
                    updateCustomerPanel();
                    textBox.setText("Loaded the banking session from: " + JSON_STORE);
                } catch (IOException ioException) {
                    textBox.setText("Unable to load banking session from: " + JSON_STORE);
                }
            }
        });
        userInputPanel.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the button to quit the current banking session
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Java Oracle Doc
    private void quitButton() {
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                confirmDecision("Would you like to save your session before quitting?", "save");
                setVisible(false);
                dispose();
            }
        });
        userInputPanel.add(quitButton);
    }

    // EFFECTS: plays the sound inputted as the parameter
    // REFERENCES: https://www.youtube.com/watch?v=nUKya2DvYSo&t=4s
    // https://www.tabnine.com/code/java/classes/javax.sound.sampled.Clip
    // Java Oracle Doc, IntelliJ's suggestion to use a try-catch
    protected void sound(String sound) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(sound).getAbsoluteFile());
            Clip c = AudioSystem.getClip();
            c.open(ais);
            c.start();
        } catch (Exception e) {
            System.out.println("Sorry! The sound could not play.");
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the customer created
    // REFERENCES:
    // https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // https://www.javatpoint.com/java-jtextfield
    // https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
    private Customer createCustomerInformation() {
        Random random = new Random();
        int accountNumber = random.nextInt(9000000) + 1000000;
        customerInformation = new JPanel();
        JTextField firstName = new JTextField(10);
        JTextField phoneNumber = new JTextField(10);
        customerInformation.add(new JLabel("First name: "));
        customerInformation.add(firstName);
        customerInformation.add(new JLabel("Phone Number (with no spaces and hyphens): "));
        customerInformation.add(phoneNumber);
        int confirm = JOptionPane.showConfirmDialog(null,
                customerInformation, "choose one", JOptionPane.OK_CANCEL_OPTION);
        if (confirm == JOptionPane.OK_OPTION) {
            sound(CLICKING_SOUND);
            try {
                return new Customer(firstName.getText(), phoneNumber.getText(), accountNumber, 0.00);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(userInputPanel, "One of the values inputted were not valid.");
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Searches bank to see if customer already exists in the system
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    private void checkExistingCustomer(Customer customer) {
        if (customer != null) {
            try {
                bank.addNewCustomer(customer);
            } catch (ExistingCustomerException e) {
                JOptionPane.showMessageDialog(userInputPanel, "That customer already exists.");
            }
            individualCustomerInformationButton(customer);
            textBox.setText("Customer has been added!");
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the button for information regarding every individual customer
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // https://stackoverflow.com/questions/7252983/resizing-image-java-getscaledinstance
    private void individualCustomerInformationButton(Customer customer) {
        JButton newCustomerPanel = new JButton(customer.getName());
        ImageIcon thumbnail = new ImageIcon(MONEY_IMAGE);
        Image rescaled = thumbnail.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        newCustomerPanel.setIcon(new ImageIcon(rescaled));
        newCustomerPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                new CustomerInformationGraphics(customer);
            }
        });
        customersPanel.add(newCustomerPanel);
        customersPanel.validate();
        customersPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: Confirms if the user wants to save/delete anything
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    private void confirmDecision(String confirmation, String option) {
        frameDecision.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                chooseSaveOrDelete(option);
                frameDecision.setVisible(false);
                frameDecision.dispose();
            }
        });
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sound(CLICKING_SOUND);
                frameDecision.setVisible(false);
                frameDecision.dispose();
            }
        });
        text.setText(confirmation);
        addSaveOrDelete(yesButton, noButton);
        frameDecision.pack();
        frameDecision.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds options to the yes/no display page
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    private void addSaveOrDelete(JButton yesButton, JButton noButton) {
        panelDecision.removeAll();
        frameDecision.getContentPane().add(text, "North");
        panelDecision.add(yesButton, "West");
        panelDecision.add(noButton, "East");
        frameDecision.getContentPane().add(panelDecision, "South");
    }

    // MODIFIES: this
    // EFFECTS: either saves or deletes customer depending on call
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    private void chooseSaveOrDelete(String option) {
        if (option.equals("save")) {
            saveBank();
        } else if (option.equals("delete")) {
            bank.removeExistingCustomer(removeCustomer.getAccountNumber());
            updateCustomerPanel();
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the customer panel
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    private void updateCustomerPanel() {
        customersPanel.removeAll();
        customersPanel.repaint();
        for (Customer c : bank.getBank()) {
            individualCustomerInformationButton(c);
        }
    }

    // EFFECTS: saves the banking session to designated location
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // IntelliJ's suggestion to use try catch
    private void saveBank() {
        try {
            jsonWriter.open();
            jsonWriter.write(bank);
            jsonWriter.close();
            textBox.setText("Saved the bank session to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            textBox.setText("Was unable to save session to: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the visual text box that users can see
    private void createTextBox() {
        textPanel.add(textBox);
    }

    // REQUIRES: a non-zero length string that is a double
    // MODIFIES: this
    // EFFECTS: converts the input of numbers (string) to double rounded to 2 decimal places
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // https://intellipaat.com/community/35143/how-to-round-up-to-2-decimal-places-in-java
    // Lab 7: parse-conversion functions
    protected double convertToDouble(String text) {
        String amountToConvert = JOptionPane.showInputDialog(userInputPanel, text, null);
        double converted =  Double.parseDouble(amountToConvert);
        return Math.round(converted * 100.00) / 100.00;
    }

    // REQUIRES: a non-zero length string that is an int
    // MODIFIES: this
    // EFFECTS: converts the input of numbers (string) to int
    // REFERENCES: https://www.youtube.com/watch?v=Kmgo00avvEw&t=8196s
    // Lab 7: parse-conversion functions
    protected int convertToInt(String text) {
        String amountToConvert = JOptionPane.showInputDialog(userInputPanel, text, null);
        return Integer.parseInt(amountToConvert);
    }

}