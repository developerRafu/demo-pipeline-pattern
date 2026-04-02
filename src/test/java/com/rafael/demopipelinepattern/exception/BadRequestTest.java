package com.rafael.demopipelinepattern.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import java.util.List;
import org.junit.jupiter.api.Test;

class BadRequestTest {

  @Test
  void givenErrorsAndMessage_whenBadRequestCreated_thenShouldStoreErrors() {
    // Given
    Message error =
        Message.builder()
            .text("Validation error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    List<Message> errors = List.of(error);
    String message = "Bad request";

    // When
    BadRequest badRequest = new BadRequest(errors, message);

    // Then
    assertNotNull(badRequest);
    assertEquals(1, badRequest.getErrors().size());
    assertEquals("Validation error", badRequest.getErrors().get(0).getText());
  }

  @Test
  void givenMultipleErrors_whenBadRequestCreated_thenShouldStoreAllErrors() {
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
    List<Message> errors = List.of(error1, error2);
    String message = "Multiple errors";

    // When
    BadRequest badRequest = new BadRequest(errors, message);

    // Then
    assertEquals(2, badRequest.getErrors().size());
  }

  @Test
  void givenEmptyErrors_whenBadRequestCreated_thenShouldStoreEmptyList() {
    // Given
    List<Message> errors = List.of();
    String message = "No errors";

    // When
    BadRequest badRequest = new BadRequest(errors, message);

    // Then
    assertEquals(0, badRequest.getErrors().size());
  }

  @Test
  void givenBadRequest_whenGetErrors_thenShouldReturnStoredErrors() {
    // Given
    Message error =
        Message.builder()
            .text("Test error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    BadRequest badRequest = new BadRequest(List.of(error), "Test");

    // When
    List<Message> retrievedErrors = badRequest.getErrors();

    // Then
    assertNotNull(retrievedErrors);
    assertEquals(1, retrievedErrors.size());
    assertEquals("Test error", retrievedErrors.get(0).getText());
  }
}
