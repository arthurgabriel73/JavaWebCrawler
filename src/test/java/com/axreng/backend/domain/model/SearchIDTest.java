package com.axreng.backend.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.axreng.backend.domain.exceptions.ApplicationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchIDTest {

    @Test
    void testValidSearchID() {
        // Arrange
        String validId = "AbCd1234";

        // Act
        SearchID searchID = new SearchID(validId);

        //Assert
        assertEquals(validId, searchID.getValue());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AbCd123_", "EfGh567", "Ij Kl9012"})
    void testInvalidSearchID(String invalidId) {
        // Act & Assert
        assertThrows(ApplicationException.class, () -> new SearchID(invalidId));
        
    }

    @Test
    void testGetId() {
        // Arrange
        String id = "AbCd1234";

        // Act
        SearchID searchID = new SearchID(id);

        // Assert
        assertEquals(id, searchID.getValue());
    }

   
}