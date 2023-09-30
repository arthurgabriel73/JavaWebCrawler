package com.axreng.backend.infra;

import com.axreng.backend.application.webcrawler.CrawlingService;
import com.axreng.backend.util.LinkAddress;
import com.axreng.backend.util.LinkExtractor;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.io.*;
import java.net.URL;
import java.util.*;

public class DefaultWebCrawler implements CrawlingService {
    private BlockingQueue<String> urlQueue = new LinkedBlockingQueue<>();
    private Set<String> visitedURLs = new HashSet<>();
    private String keyword;
    private String baseUrl;
    private LinkExtractor linkExtractor;
    private ExecutorService executorService;

    public DefaultWebCrawler(String baseUrl, int numThreads) {
        this.linkExtractor = new LinkExtractor(baseUrl);
        this.baseUrl = baseUrl;
        this.executorService = Executors.newFixedThreadPool(numThreads);
    }

    public CompletableFuture<Void> crawl(String keyword, int maxDepth, Consumer<String> resultCallback) {
        this.keyword = keyword;
        initializeCrawl(baseUrl);
        CompletableFuture<Void> crawlFuture = new CompletableFuture<>();
        CompletableFuture.runAsync(() -> {
            int remainingDepth = maxDepth;
            while (!urlQueue.isEmpty() && remainingDepth > 0) {
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                processUrls(remainingDepth, futures, baseUrl, resultCallback);
                CompletableFuture<Void> allOf = CompletableFuture.allOf(
                        futures.toArray(new CompletableFuture[0]));
                try {
                    allOf.get();
                } catch (InterruptedException | ExecutionException e) {}
                remainingDepth--;
            }
            crawlFuture.complete(null);
        }, executorService);
        return crawlFuture;
    }

    private void processUrls(int remainingDepth, List<CompletableFuture<Void>> futures, String baseUrl2,
        Consumer<String> resultCallback) {
            while (!urlQueue.isEmpty() && remainingDepth > 0) {
                String currentUrl = urlQueue.remove();
                futures.add(CompletableFuture.runAsync(() -> {
                    String sourceHtml = fetchHTML(currentUrl);
                    findAndEnqueueUrls(sourceHtml, baseUrl);
                    if (containsKeyword(sourceHtml)) {
                        resultCallback.accept(currentUrl);
                    }
                }, executorService));
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
            return "";
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

}