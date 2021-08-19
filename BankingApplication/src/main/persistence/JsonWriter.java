package persistence;

import model.Bank;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of bank to file
// REFERENCES: JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    // REFERENCES: JsonSerializationDemo
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    // REFERENCES: JsonSerializationDemo
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of bank to file
    // REFERENCES: JsonSerializationDemo
    public void write(Bank bank) {
        JSONObject json = bank.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // REFERENCES: JsonSerializationDemo
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    // REFERENCES: JsonSerializationDemo
    private void saveToFile(String json) {
        writer.print(json);
    }
}