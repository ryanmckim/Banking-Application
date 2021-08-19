package persistence;

import model.Customer;
import model.Bank;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads Bank from JSON data stored in file
// Reference: JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    // REFERENCES: JsonSerializationDemo
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads bank from file and returns it;
    // throws IOException if an error occurs reading data from file
    // REFERENCES: JsonSerializationDemo
    public Bank read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBank(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // REFERENCES: JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Bank from JSON object and returns it
    // REFERENCES: JsonSerializationDemo
    private Bank parseBank(JSONObject jsonObject) {
        Bank bank = new Bank();
        addCustomers(bank, jsonObject);
        return bank;
    }

    // MODIFIES: bank
    // EFFECTS: parses customers from JSON object and adds them to bank
    // REFERENCES: JsonSerializationDemo
    private void addCustomers(Bank bank, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("customers");
        for (Object json : jsonArray) {
            JSONObject nextCustomer = (JSONObject) json;
            addCustomer(bank, nextCustomer);
        }
    }

    // MODIFIES: bank
    // EFFECTS: parses customers from JSON object and adds it to bank
    // REFERENCES: JsonSerializationDemo
    private void addCustomer(Bank bank, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String phoneNumber = jsonObject.getString("phoneNumber");
        int accountNumber = jsonObject.getInt("accountNumber");
        int balance = jsonObject.getInt("balance");
        try {
            Customer customer = new Customer(name, phoneNumber, accountNumber, balance);
            bank.addCustomer(customer);
        } catch (Exception e) {
            System.out.println("");
        }
    }

}