package com.axreng.backend.domain.exceptions;

public class ValidationException extends ApplicationException {
  public ValidationException(String message) {
    super(message);
  }
}