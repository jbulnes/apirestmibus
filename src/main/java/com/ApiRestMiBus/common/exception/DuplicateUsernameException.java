package com.ApiRestMiBus.common.exception;

public class DuplicateUsernameException extends RuntimeException {
  private final String code;

  public DuplicateUsernameException(String message) {
    super(message);
    this.code = "USERNAME_ALREADY_EXISTS";
  }

  public String getCode() {
    return code;
  }
}