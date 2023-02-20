package org.example;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WriteAndRead {
    public static void writeJsonToFile(JSONObject jsonObj) throws IOException {
        try (FileWriter writer = new FileWriter(jsonFile)) {
            writer.write(jsonObj.toString());
        } catch (Exception e) {
            throw new IOException("Error writing JSON file: " + e.getMessage(), e);
        }
    }
        public static boolean fileExists(){
            File file = new File(jsonFile);
            return file.exists() && file.isFile() && file.length() >0;
        }
    public static  JSONObject readJsonFromFile() throws IOException {
        JSONObject jsonObj;
        try (FileReader reader = new FileReader(jsonFile)) {
            jsonObj = new JSONObject(reader);
        } catch (Exception e) {
            throw new IOException("Error reading JSON file: " + e.getMessage(), e);
        }
        return jsonObj;
    }
    private static final String jsonFile = "quote.json";
}

