package com.rafael.demopipelinepattern.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void givenBadRequestException_whenHandleBadRequest_thenShouldReturnBadRequestStatus() {
    // Given
    Message error =
        Message.builder()
            .text("Validation error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    BadRequest badRequest = new BadRequest(List.of(error), "Bad request");

    // When
    ResponseEntity<List<Message>> response = globalExceptionHandler.handleBadRequest(badRequest);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(1, response.getBody().size());
    assertEquals("Validation error", response.getBody().get(0).getText());
  }

  @Test
  void givenBadRequestWithMultipleErrors_whenHandleBadRequest_thenShouldReturnAllErrors() {
    // Given
    Message error1 =
        Message.builder()
            .text("Error 1")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    Message error2 =
        Message.builder()
            .text("Error 2")
            .type(MessageType.WARNING)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    BadRequest badRequest = new BadRequest(List.of(error1, error2), "Multiple errors");

    // When
    ResponseEntity<List<Message>> response = globalExceptionHandler.handleBadRequest(badRequest);

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals(2, response.getBody().size());
  }

  @Test
  void givenGenericException_whenHandleGenericException_thenShouldReturnInternalServerError() {
    // Given
    Exception exception = new RuntimeException("Test error");

    // When
    ResponseEntity<Message> response = globalExceptionHandler.handleGenericException(exception);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("An unexpected error occurred", response.getBody().getText());
    assertEquals(MessageType.ERROR, response.getBody().getType());
  }

  @Test
  void givenGenericException_whenHandleGenericException_thenShouldIncludeErrorDetails() {
    // Given
    String errorMessage = "Database connection failed";
    Exception exception = new RuntimeException(errorMessage);

    // When
    ResponseEntity<Message> response = globalExceptionHandler.handleGenericException(exception);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody().getDetails());
    assertEquals(errorMessage, response.getBody().getDetails().get(0));
  }

  @Test
  void givenNullPointerException_whenHandleGenericException_thenShouldReturnInternalServerError() {
    // Given
    Exception exception = new NullPointerException("Null value encountered");

    // When
    ResponseEntity<Message> response = globalExceptionHandler.handleGenericException(exception);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
