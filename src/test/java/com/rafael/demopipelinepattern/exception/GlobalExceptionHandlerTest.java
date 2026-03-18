package com.rafael.demopipelinepattern.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  @DisplayName("given_badRequestException_when_handleBadRequest_then_returnBadRequestStatus")
  void given_badRequestException_when_handleBadRequest_then_returnBadRequestStatus() {
    // given
    final List<Message> errors =
        List.of(
            Message.builder()
                .text("Error 1")
                .type(MessageType.ERROR)
                .module(MessageModule.ENGINE_MODULE)
                .build());
    final BadRequest ex = new BadRequest(errors, "Bad request");

    // when
    final ResponseEntity<List<Message>> result = handler.handleBadRequest(ex);

    // then
    assertNotNull(result);
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals(errors, result.getBody());
  }

  @Test
  @DisplayName("given_genericException_when_handleGenericException_then_returnInternalServerError")
  void given_genericException_when_handleGenericException_then_returnInternalServerError() {
    // given
    final Exception ex = new Exception("Test error");

    // when
    final ResponseEntity<Message> result = handler.handleGenericException(ex);

    // then
    assertNotNull(result);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals("An unexpected error occurred", result.getBody().getText());
    assertEquals(MessageType.ERROR, result.getBody().getType());
  }

  @Test
  @DisplayName("given_exceptionWithMessage_when_handleGenericException_then_messageIncludesDetails")
  void given_exceptionWithMessage_when_handleGenericException_then_messageIncludesDetails() {
    // given
    final String errorMessage = "Specific error message";
    final Exception ex = new Exception(errorMessage);

    // when
    final ResponseEntity<Message> result = handler.handleGenericException(ex);

    // then
    assertNotNull(result.getBody());
    assertNotNull(result.getBody().getDetails());
    assertEquals(1, result.getBody().getDetails().size());
  }
}
