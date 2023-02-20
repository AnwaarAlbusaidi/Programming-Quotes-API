package org.example;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
/**
 This class provides methods to fetch the quote of the day from an external API and to read and write the quote to a JSON file.
 */
public class QuoteFetcher {
    /**
     * Fetches the quote of the day from either the local JSON file or from the external API if it is not in the file.
     * @return The quote of the day.
     * @throws IOException If there was an error reading or writing the file.
     */
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
    /**
     * Fetches the JSON object that contains the quote of the day from the API.
     * @return The JSON object that contains the quote of the day.
     * @throws RuntimeException If there was an error fetching the JSON object from the API.
     */
    private static JSONObject fetchJsonFromApi(){
        // Fetch the quote of the day from the API
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://quotes.rest/qod.json")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JSONObject   jsonObj = new JSONObject(json);
            return jsonObj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * The URL for the API endpoint that provides the quote of the day.
     */
    private static final String API_URL = "https://quotes.rest/qod.json";


}

