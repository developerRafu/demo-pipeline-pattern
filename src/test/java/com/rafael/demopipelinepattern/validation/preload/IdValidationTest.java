package com.rafael.demopipelinepattern.validation.preload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.MessageType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("IdValidation")
class IdValidationTest {

  private final IdValidation validation = new IdValidation();

  @Test
  @DisplayName("given_contextWithNullId_when_shouldRun_then_returnTrue")
  void given_contextWithNullId_when_shouldRun_then_returnTrue() {
    // given
    final Context context = new Context(null);

    // when
    final boolean result = validation.shouldRun(context);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("given_contextWithValidId_when_shouldRun_then_returnFalse")
  void given_contextWithValidId_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");

    // when
    final boolean result = validation.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithNullId_when_run_then_addErrorToContext")
  void given_contextWithNullId_when_run_then_addErrorToContext() {
    // given
    final Context context = new Context(null);

    // when
    validation.run(context);

    // then
    assertTrue(context.hasErrors());
    assertEquals(1, context.getErrors().size());
    assertEquals("Invalid id", context.getErrors().get(0).getText());
    assertEquals(MessageType.ERROR, context.getErrors().get(0).getType());
  }
}
