package org.example;


import java.io.IOException;

public class ConsoleApplication {
    public static void main(String[] args) {
        try {
            String quote = QuoteFetcher.fetchQuoteOfTheDay();
            System.out.println("Quote of the Day: " + quote);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}