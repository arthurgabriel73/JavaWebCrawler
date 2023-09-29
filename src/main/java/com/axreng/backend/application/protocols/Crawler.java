package com.axreng.backend.application.protocols;

import com.axreng.backend.domain.model.URLAddress;

public interface Crawler {
    public URLAddress[] crawl(String keyword, int limit);
}
