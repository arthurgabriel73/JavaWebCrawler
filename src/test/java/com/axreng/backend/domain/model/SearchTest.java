package com.axreng.backend.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SearchTest {
    private Search search;

    @BeforeEach
    void setUp() {
        Keyword keyword = new Keyword("validKeyword");
        search = new Search(keyword);
    }
    @Test
    void testGetId() {
        // Act & Assert
        assertNotNull(search.getId());
    }

    @Test
    void testGetKeyword() {
        // Act & Assert
        assertNotNull(search.getKeyword());
    }

    @Test
    void testGetStatus() {
        // Act & Assert
        assertEquals(SearchStatus.ACTIVE, search.getStatus());
    }

    @Test
    void testSetStatus() {
        // Act
        search.setStatus(SearchStatus.DONE);

        // Assert
        assertEquals(SearchStatus.DONE, search.getStatus());
    }

    @Test
    void testGetUrlsEmpty() {
        // Act & Assert
        assertNotNull(search.getUrls());
        assertTrue(search.getUrls().isEmpty());
    }

    @Test
    void testGetUrlsNotEmpty() {
        // Arrange
        search.addURL(new URLAddress("http://example.com"));
        
        // Act & Assert
        assertNotNull(search.getUrls());
    }

    @Test
    void testSetUrls() {
        // Arrange
        List<URLAddress> urls = new ArrayList<>();
        urls.add(new URLAddress("http://example.com"));

        // Act
        search.setUrls(urls);

        // Assert
        assertEquals(urls, search.getUrls());
    }

    @Test
    void testMarkAsDone() {
        // Act
        search.markAsDone();

        // Assert
        assertEquals(SearchStatus.DONE, search.getStatus());
    }

    @Test
    void testAddURL() {
        // Arrange
        URLAddress url = new URLAddress("http://example.com");

        // Act
        search.addURL(url);

        // Assert
        assertTrue(search.getUrls().contains(url));
    }
}