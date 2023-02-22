package org.example;
import java.io.IOException;
/**
 This class contains the main method for the console application that fetches the quote of the day
 and prints it to the console.
 */
public class ConsoleApplication {
    /**
     * The main method that fetches the quote of the day and prints it to the console.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        try {
            String quote = QuoteFetcher.fetchQuoteOfTheDay();
            System.out.println("Quote of the Day: " + quote);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}