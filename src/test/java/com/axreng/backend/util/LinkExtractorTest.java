package com.axreng.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class LinkExtractorTest {

    private static LinkExtractor linkExtractor;

    private static final String URL_STRING = "";
    private static final String FULL_FROM_PARTIAL_URL_STRING = "";

    @BeforeAll
    public static void initialize() {
        linkExtractor = new LinkExtractor("");
    }

    @ParameterizedTest
    @MethodSource("data")
    void testExtraction(String HTML_DOCUMENT, String expectedValidation) {
        List<LinkAddress> linkElements = linkExtractor.extractHTMLLinks(HTML_DOCUMENT);
        for (int i = 0; i < linkElements.size(); i++) {
            LinkAddress linkElem = linkElements.get(i);
            assertEquals(expectedValidation, linkElem.getLinkAddress(), "Result");
        }
    }

    static Stream<Arguments> data() {
        return Stream.of(
            Arguments.of(
                "Blah blah blah <a href=\"\">AxrengTest</a> blah blah blah blah", URL_STRING),
            Arguments.of(
                "Blah blah blah <a HREF=\"\">AxrengTest</a> blah blah blah blah", URL_STRING),
            Arguments.of(
                "Blah blah blah <a target='_blank' HREF=\"\">AxrengTest</a> blah blah blah blah", URL_STRING),
            Arguments.of(
                "Blah blah blah <a accesskey='n' href=\"\">Next</a> blah blah blah blah", FULL_FROM_PARTIAL_URL_STRING)
        );
    }
                        
}