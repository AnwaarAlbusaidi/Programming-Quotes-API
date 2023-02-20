package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class QuoteFetcher {
    public static String fetchQuoteOfTheDay() throws IOException {
        String quote = null;
        WriteAndRead read = new WriteAndRead();

        // Try to read the quote from the JSON file
       if(read.fileExists() == true) {
            JSONObject jsonObj = read.readJsonFromFile();
            quote = jsonObj.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("quote");
        }

        // If the quote was not found in the JSON file, fetch it from the API
        if (quote == null) {
            JSONObject jsonObj = fetchJsonFromApi();
            quote = jsonObj.getJSONObject("contents").getJSONArray("quotes").getJSONObject(0).getString("quote");
            WriteAndRead.writeJsonToFile(jsonObj);
        }

        return quote;
    }

    private static JSONObject fetchJsonFromApi() throws IOException {
        JSONObject jsonObj;
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                String jsonString = new BufferedReader(new InputStreamReader(inputStream))
                        .lines().collect(Collectors.joining());
                jsonObj = new JSONObject(jsonString);
            } else {
                throw new IOException("Error fetching quote from API: HTTP error code " + responseCode);
            }
        } catch (Exception e) {
            throw new IOException("Error fetching quote from API: " + e.getMessage(), e);
        }
        return jsonObj;
    }

    private static final String API_URL = "https://quotes.rest/qod.json";


}

