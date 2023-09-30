package com.axreng.backend.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.axreng.backend.domain.exceptions.ApplicationException;

class URLAddressTest {

    @Test
    void testValidURLAddress() {
        // Arrange
        String url = "https://www.example.com";

        // Act
        URLAddress urlAddress = new URLAddress(url);

        // Assert
        assertEquals(url, urlAddress.getValue());
    }

    @Test
    void testInalidURLAddress() {
        // Arrange
        String url = "invalid example";

        // Act & Assert
        assertThrows(ApplicationException.class, () -> new URLAddress(url));
    }
    
    @Test
    void testNullURLAddress() {
        // Arrange
        String nullUrl = "";

        // Act & Assert
        assertThrows(ApplicationException.class, () -> new URLAddress(nullUrl));
    }
}
