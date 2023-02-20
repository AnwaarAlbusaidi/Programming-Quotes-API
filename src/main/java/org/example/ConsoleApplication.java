package org.example;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

public class ConsoleApplication {
    private static final String JSON_FILE_PATH ="quote.json";
    private static final String API_URL = "https://quotes.rest/qod.json";
    public static void main(String[] args) {
        String quote = null;

        // Try to read the quote from the JSON file
        try {
            File jsonFile = new File(JSON_FILE_PATH);

            if (jsonFile.exists() && jsonFile.length() > 0) {
                BufferedReader br = new BufferedReader(new FileReader(jsonFile));
                String jsonStr = br.readLine();
                JSONObject jsonObj = new JSONObject(jsonStr);
                quote = jsonObj.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("quote");
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }

        // If the quote was not found in the JSON file, fetch it from the API
        if (quote == null) {
            try {
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response and extract the quote of the day
                JSONObject jsonObj = new JSONObject(response.toString());
                quote = jsonObj.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("quote");

                // Write the JSON response to the file
                FileWriter fw = new FileWriter(JSON_FILE_PATH);
                fw.write(jsonObj.toString());
                fw.close();
            } catch (Exception e) {
                System.out.println("Error fetching quote: " + e.getMessage());
                return;
            }
        }

        // Print the quote of the day
        System.out.println("Quote of the Day: " + quote);
    }
}