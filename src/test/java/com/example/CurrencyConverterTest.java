package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Test suite for CurrencyConverter using JUnit 5.
 */
class CurrencyConverterTest {
    
    private CurrencyConverter converter;
    
    @BeforeEach
    void setUp() {
        converter = new CurrencyConverter();
    }
    
    @Test
    @DisplayName("Should convert USD to EUR correctly")
    void testUsdToEur() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal result = converter.convert(amount, "USD", "EUR");
        
        // 100 USD * 0.92 = 92 EUR
        assertEquals(new BigDecimal("92.00"), result);
    }
    
    @Test
    @DisplayName("Should convert between non-USD currencies")
    void testGbpToJpy() {
        BigDecimal amount = new BigDecimal("10");
        BigDecimal result = converter.convert(amount, "GBP", "JPY");
        
        // 10 GBP -> USD -> JPY
        // Expected: approximately 1892.41 JPY
        assertTrue(result.compareTo(new BigDecimal("1890")) > 0);
        assertTrue(result.compareTo(new BigDecimal("1895")) < 0);
    }
    
    @Test
    @DisplayName("Should handle same currency conversion")
    void testSameCurrencyConversion() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal result = converter.convert(amount, "USD", "USD");
        
        assertEquals(new BigDecimal("100.00"), result);
    }
    
    @Test
    @DisplayName("Should throw exception for unsupported currency")
    void testUnsupportedCurrency() {
        BigDecimal amount = new BigDecimal("100");
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(amount, "USD", "XXX");
        });
    }
    
    @Test
    @DisplayName("Should throw exception for negative amount")
    void testNegativeAmount() {
        BigDecimal amount = new BigDecimal("-100");
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(amount, "USD", "EUR");
        });
    }
    
    @Test
    @DisplayName("Should correctly identify supported currencies")
    void testIsSupportedCurrency() {
        assertTrue(converter.isSupportedCurrency("USD"));
        assertTrue(converter.isSupportedCurrency("EUR"));
        assertTrue(converter.isSupportedCurrency("GBP"));
        assertFalse(converter.isSupportedCurrency("XXX"));
    }
    
    @Test
    @DisplayName("Should handle zero amount")
    void testZeroAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal result = converter.convert(amount, "USD", "EUR");
        
        assertEquals(new BigDecimal("0.00"), result);
    }
}
