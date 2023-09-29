package com.axreng.backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.axreng.backend.infra.AppConfig;

public class LinkExtractorTest {

    private static String BASE_URL = new AppConfig().getBaseUrl();
    private static LinkExtractor linkExtractor;
    private static final String FULL_FROM_PARTIAL_URL_STRING = BASE_URL + "blasterknight.html";

    @BeforeAll
    public static void initialize() {
        linkExtractor = new LinkExtractor(BASE_URL);
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
                "Hello World <a href=\"\" + BASE_URL + \"\">Hello World</a> hellow world world world",
                BASE_URL),
            Arguments.of(
                "Hello World <a HREF=\"\" + BASE_URL + \"\">Hello World</a> hellow world hellow world",
                BASE_URL),
            Arguments.of(
                 "Hello World <a target='_blank' HREF=\"\" + BASE_URL + \"\">Hello World</a> hellow world",
                BASE_URL),
            Arguments.of(
                "Hello World <a accesskey='n' href=\"blasterknight.html\">Hello World</a> hellow world",
                FULL_FROM_PARTIAL_URL_STRING));
    }
                        
}