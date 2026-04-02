package com.rafael.demopipelinepattern.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.Test;

class MessageTest {

  @Test
  void givenMessageBuilder_whenBuild_thenShouldCreateMessage() {
    // Given & When
    Message message =
        Message.builder()
            .text("Test message")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .details(List.of("detail1", "detail2"))
            .build();

    // Then
    assertNotNull(message);
    assertEquals("Test message", message.getText());
    assertEquals(MessageType.ERROR, message.getType());
    assertEquals(MessageModule.ENGINE_MODULE, message.getModule());
    assertEquals(2, message.getDetails().size());
  }

  @Test
  void givenMessage_whenGetText_thenShouldReturnText() {
    // Given
    Message message = Message.builder().text("Test").type(MessageType.WARNING).build();

    // When
    String text = message.getText();

    // Then
    assertEquals("Test", text);
  }

  @Test
  void givenMessage_whenGetType_thenShouldReturnType() {
    // Given
    Message message =
        Message.builder()
            .text("Test")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();

    // When
    MessageType type = message.getType();

    // Then
    assertEquals(MessageType.ERROR, type);
  }

  @Test
  void givenMessage_whenGetModule_thenShouldReturnModule() {
    // Given
    Message message =
        Message.builder()
            .text("Test")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();

    // When
    MessageModule module = message.getModule();

    // Then
    assertEquals(MessageModule.ENGINE_MODULE, module);
  }

  @Test
  void givenMessage_whenGetDetails_thenShouldReturnDetails() {
    // Given
    List<String> details = List.of("detail1", "detail2");
    Message message =
        Message.builder()
            .text("Test")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .details(details)
            .build();

    // When
    List<String> retrievedDetails = message.getDetails();

    // Then
    assertEquals(2, retrievedDetails.size());
    assertEquals("detail1", retrievedDetails.get(0));
  }
}
