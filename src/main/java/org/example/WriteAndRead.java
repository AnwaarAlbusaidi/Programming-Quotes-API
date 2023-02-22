package org.example;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 This class provides methods to write and read JSON objects to/from a file.
 */
public class WriteAndRead {

    /**
     * Writes the given JSON object to the file at the path specified by jsonFile.
     * @param jsonObj The JSON object to write to the file.
     * @throws IOException If there was an error writing the file.
     */
    public static void writeJsonToFile(JSONObject jsonObj) throws IOException {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonObj.toString());
        } catch (Exception e) {
            throw new IOException("Error writing JSON file: " + e.getMessage(), e);
        }
    }
    /**
     * Checks if the file specified by jsonFile exists and is a file with length greater than zero.
     *
     * @return true if the file exists and is a file with length greater than zero, false otherwise.
     */
        public static boolean fileExists(){
            File file = new File(jsonFile);
            return file.exists() && file.length() > 0;
        }
    /**
     * Reads the JSON object from the file at the path specified by jsonFile.
     *
     * @return The JSON object read from the file.
     * @throws IOException If there was an error reading the file.
     */
    public static  JSONObject readJsonFromFile() throws IOException {
        JSONObject jsonObj;
        try (FileReader reader = new FileReader(jsonFile)) {
            jsonObj = new JSONObject(reader);
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + e.getMessage(), e);
        }
        return jsonObj;
    }
    /**
     * The path to the JSON file.
     */
    private static final String jsonFile = "quote.json";
}

