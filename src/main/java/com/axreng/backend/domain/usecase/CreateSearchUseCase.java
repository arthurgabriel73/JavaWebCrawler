package com.axreng.backend.domain.usecase;

import com.axreng.backend.domain.model.*;

public class CreateSearchUseCase {
    
    public Search createSearch(String keyword) {
        return new Search(new Keyword(keyword));
    }
}
