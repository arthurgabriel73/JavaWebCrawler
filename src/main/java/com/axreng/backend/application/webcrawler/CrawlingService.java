package com.axreng.backend.application.webcrawler;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface CrawlingService {
     public CompletableFuture<Void> crawl(String keyword, int maxDepth, Consumer<String> resultCallback);
}
