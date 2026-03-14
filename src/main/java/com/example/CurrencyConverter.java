package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * A simple currency converter using Java 21 features.
 * Demonstrates Maven build process with modern Java.
 */
public class CurrencyConverter {

    /**
     * Default constructor for CurrencyConverter.
     * Creates a new instance of the currency converter.
     */
    public CurrencyConverter() {
        // Default constructor
    }

    // Exchange rates relative to USD
    private static final Map<String, BigDecimal> EXCHANGE_RATES = Map.of(
        "USD", new BigDecimal("1.00"),
        "EUR", new BigDecimal("0.92"),
        "GBP", new BigDecimal("0.79"),
        "JPY", new BigDecimal("149.50"),
        "CAD", new BigDecimal("1.36")
    );

    /**
     * Converts an amount from one currency to another.
     *
     * @param amount the amount to convert
     * @param fromCurrency the source currency code
     * @param toCurrency the target currency code
     * @return the converted amount
     * @throws IllegalArgumentException if currency codes are invalid
     */
    public BigDecimal convert(BigDecimal amount, String fromCurrency, String toCurrency) {
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        // Convert to USD first, then to target currency
        BigDecimal amountInUsd = amount.divide(
            EXCHANGE_RATES.get(fromCurrency),
            4,
            RoundingMode.HALF_UP
        );

        return amountInUsd.multiply(EXCHANGE_RATES.get(toCurrency))
                          .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Checks if a currency code is supported.
     *
     * @param currencyCode the currency code to check
     * @return true if supported, false otherwise
     */
    public boolean isSupportedCurrency(String currencyCode) {
        return EXCHANGE_RATES.containsKey(currencyCode);
    }

    private void validateCurrency(String currencyCode) {
        if (!isSupportedCurrency(currencyCode)) {
            throw new IllegalArgumentException(
                "Unsupported currency: " + currencyCode +
                ". Supported currencies: " + EXCHANGE_RATES.keySet()
            );
        }
    }

    /**
     * Main method to demonstrate the currency converter functionality.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        var converter = new CurrencyConverter();

        System.out.println("=== Currency Converter Demo ===\n");

        // Demo conversions using Java 21 features
        var conversions = new Object[][] {
            {new BigDecimal("100"), "USD", "EUR"},
            {new BigDecimal("50"), "GBP", "JPY"},
            {new BigDecimal("1000"), "JPY", "CAD"}
        };

        for (var conversion : conversions) {
            BigDecimal amount = (BigDecimal) conversion[0];
            String from = (String) conversion[1];
            String to = (String) conversion[2];

            BigDecimal result = converter.convert(amount, from, to);
            System.out.printf("%s %s = %s %s%n", amount, from, result, to);
        }

        System.out.println("\nConversions complete.");
    }
}
