package com.rafael.demopipelinepattern.exception;

import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequest.class)
  public ResponseEntity<List<Message>> handleBadRequest(final BadRequest ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getErrors());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Message> handleGenericException(final Exception ex) {
    Message message =
        Message.builder()
            .text("An unexpected error occurred")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .details(List.of(ex.getMessage()))
            .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }
}
