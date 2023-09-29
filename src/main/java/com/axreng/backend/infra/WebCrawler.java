package com.axreng.backend.infra;

import com.axreng.backend.application.protocols.Crawler;
import com.axreng.backend.domain.model.URLAddress;
import com.axreng.backend.util.LinkAddress;
import com.axreng.backend.util.LinkExtractor;

import java.io.*;
import java.net.URL;
import java.util.*;

public class WebCrawler /* implements Crawler */ {
    private Queue<String> urlQueue = new LinkedList<>();
    private Set<String> visitedURLs = new HashSet<>();
    private String keyword;
    private String baseUrl;
    private LinkExtractor linkExtractor;

    public WebCrawler(String baseUrl) {
        this.linkExtractor = new LinkExtractor(baseUrl);
        this.baseUrl = baseUrl;
    }

     /* @Override
    public URLAddress[] crawl(String keyword, int limit) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crawl'");
    } */

    public void crawl(String keyword, int maxDepth) {
        this.keyword = keyword; 
        initializeCrawl(baseUrl);

        while (!urlQueue.isEmpty() && maxDepth > 0) {
            String currentUrl = urlQueue.remove();
            String sourceHtml = fetchHTML(currentUrl);

            checkForKeyword(currentUrl, sourceHtml);
            findAndEnqueueUrls(sourceHtml, baseUrl);

            maxDepth--;
        }
    }

    private void initializeCrawl(String baseUrl) {
        urlQueue.add(baseUrl);
        visitedURLs.add(baseUrl);
    }

    private String fetchHTML(String url) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            StringBuilder sourceHtml = new StringBuilder();
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                sourceHtml.append(inputLine);
            }

            return sourceHtml.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void checkForKeyword(String url, String sourceHtml) {
        if (containsKeyword(sourceHtml)) {
            printKeywordFound(url);
        }
    }

    private boolean containsKeyword(String sourceHtml) {
        return sourceHtml.toLowerCase().contains(keyword.toLowerCase());
    }

    private void findAndEnqueueUrls(String sourceHtml, String baseUrl) {
        List<LinkAddress> foundUrls = linkExtractor.extractHTMLLinks(sourceHtml);
        foundUrls.forEach(linkElement -> {
            String foundUrl = linkElement.getLinkAddress();
            if (isSameBaseUrl(foundUrl, baseUrl) && !hasVisited(foundUrl)) {
                visitAndEnqueue(foundUrl);
            }
        });
    }

    private boolean isSameBaseUrl(String url, String baseUrl) {
        return url.startsWith(baseUrl);
    }

    private boolean hasVisited(String url) {
        return visitedURLs.contains(url);
    }

    private void visitAndEnqueue(String url) {
        visitedURLs.add(url);
        urlQueue.add(url);
    }

    private void printKeywordFound(String url) {
        System.out.println("Keyword found: " + keyword);
        System.out.println("Keyword found at URL: " + url);
    }
    
}