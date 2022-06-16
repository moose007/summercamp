package com.mcc.summercamp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public UnauthorizedRequestException(String message) {
    super(message);
  }

  public UnauthorizedRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
