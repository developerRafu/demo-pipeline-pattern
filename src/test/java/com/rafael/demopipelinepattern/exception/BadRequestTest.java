package com.rafael.demopipelinepattern.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BadRequest")
class BadRequestTest {

  @Test
  @DisplayName("given_errorsAndMessage_when_createBadRequest_then_storeErrorsAndMessage")
  void given_errorsAndMessage_when_createBadRequest_then_storeErrorsAndMessage() {
    // given
    final List<Message> errors =
        List.of(
            Message.builder()
                .text("Error 1")
                .type(MessageType.ERROR)
                .module(MessageModule.ENGINE_MODULE)
                .build());
    final String message = "Bad request occurred";

    // when
    final BadRequest badRequest = new BadRequest(errors, message);

    // then
    assertNotNull(badRequest);
    assertEquals(errors, badRequest.getErrors());
    assertEquals(message, badRequest.getMessage());
  }

  @Test
  @DisplayName("given_multipleErrors_when_createBadRequest_then_allErrorsAreStored")
  void given_multipleErrors_when_createBadRequest_then_allErrorsAreStored() {
    // given
    final List<Message> errors =
        List.of(
            Message.builder()
                .text("Error 1")
                .type(MessageType.ERROR)
                .module(MessageModule.ENGINE_MODULE)
                .build(),
            Message.builder()
                .text("Error 2")
                .type(MessageType.WARNING)
                .module(MessageModule.ENGINE_MODULE)
                .build());
    final String message = "Multiple errors";

    // when
    final BadRequest badRequest = new BadRequest(errors, message);

    // then
    assertEquals(2, badRequest.getErrors().size());
  }
}
