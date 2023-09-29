package com.axreng.backend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
public class LinkExtractor {
 
    private Matcher tagMatcher;
    private Pattern tagPattern;
    private String baseUrl;
    private static final String HTML_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";

    public LinkExtractor(String baseUrl) {
        this.tagPattern = Pattern.compile(HTML_TAG_PATTERN);
        this.baseUrl = baseUrl; 
    }

    public List<LinkAddress> extractHTMLLinks(final String sourceHtml) {
        ArrayList<LinkAddress> elements = new ArrayList<>();
        tagMatcher = tagPattern.matcher(sourceHtml);
        while (tagMatcher.find()) {
            String href = tagMatcher.group(1);
            ArrayList<String> links = extractLinksFromTag(href);
            for (String link : links) {
                link = link.replace("\"", "");
                String fullLink = link;

                if (!link.startsWith("http://") && !link.startsWith("https://")) {
                    fullLink = baseUrl + link; 
                }
                LinkAddress address = new LinkAddress();
                address.setLinkAddress(fullLink);
                elements.add(address);
            }
        }
        return elements;
    }

    private ArrayList<String> extractLinksFromTag(String tag) {
        ArrayList<String> links = new ArrayList<>();
        Pattern pLink = Pattern.compile("\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))");
        Matcher mLink = pLink.matcher(tag);

        while (mLink.find()) {
            String link = mLink.group(1);
            links.add(link);
        }

        return links;
    }
}