package com.rafael.demopipelinepattern.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Message")
class MessageTest {

  @Test
  @DisplayName("given_messageBuilder_when_build_then_createMessageWithAllFields")
  void given_messageBuilder_when_build_then_createMessageWithAllFields() {
    // given
    final String text = "Test message";
    final MessageType type = MessageType.ERROR;
    final MessageModule module = MessageModule.ENGINE_MODULE;
    final List<String> details = List.of("detail1", "detail2");

    // when
    final Message message =
        Message.builder().text(text).type(type).module(module).details(details).build();

    // then
    assertNotNull(message);
    assertEquals(text, message.getText());
    assertEquals(type, message.getType());
    assertEquals(module, message.getModule());
    assertEquals(details, message.getDetails());
  }

  @Test
  @DisplayName("given_message_when_setters_then_updateFields")
  void given_message_when_setters_then_updateFields() {
    // given
    final Message message = new Message();

    // when
    message.setText("Updated text");
    message.setType(MessageType.WARNING);
    message.setModule(MessageModule.ENGINE_MODULE);
    message.setDetails(List.of("updated"));

    // then
    assertEquals("Updated text", message.getText());
    assertEquals(MessageType.WARNING, message.getType());
    assertEquals(MessageModule.ENGINE_MODULE, message.getModule());
    assertEquals(List.of("updated"), message.getDetails());
  }

  @Test
  @DisplayName("given_messageConstructor_when_create_then_initializeAllFields")
  void given_messageConstructor_when_create_then_initializeAllFields() {
    // given
    final String text = "Constructor message";
    final MessageType type = MessageType.SUCCESS;
    final MessageModule module = MessageModule.ENGINE_MODULE;
    final List<String> details = List.of("success");

    // when
    final Message message = new Message(text, type, module, details);

    // then
    assertEquals(text, message.getText());
    assertEquals(type, message.getType());
    assertEquals(module, message.getModule());
    assertEquals(details, message.getDetails());
  }
}
