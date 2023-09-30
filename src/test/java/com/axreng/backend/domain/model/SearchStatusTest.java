package com.axreng.backend.domain.model;

import org.junit.jupiter.api.Test;

import com.axreng.backend.domain.exceptions.ApplicationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchStatusTest {

    @Test
    void testGetValue() {
        // Act & Assert
        assertEquals("active", SearchStatus.ACTIVE.getValue());
        assertEquals("done", SearchStatus.DONE.getValue());
    }

    @Test
    void testValidFromValue() {
        // Act & Assert
        assertEquals(SearchStatus.ACTIVE, SearchStatus.fromValue("active"));
        assertEquals(SearchStatus.DONE, SearchStatus.fromValue("done"));
    }

    @Test
    void testInvalidFromValue() {
        // Act & Assert
        assertThrows(ApplicationException.class, () -> SearchStatus.fromValue("invalid"));
        assertThrows(ApplicationException.class, () -> SearchStatus.fromValue(null));
    }
}
