package com.axreng.backend.domain.exceptions;

public class SearchNotFoundException extends ApplicationException {
  public SearchNotFoundException(String message) {
    super(message);
  }
}