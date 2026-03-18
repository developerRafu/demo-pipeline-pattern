package com.rafael.demopipelinepattern.validation.postload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.MessageType;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ItemValidation")
class ItemValidationTest {

  private final ItemValidation validation = new ItemValidation();

  @Test
  @DisplayName("given_contextWithNullItem_when_shouldRun_then_returnTrue")
  void given_contextWithNullItem_when_shouldRun_then_returnTrue() {
    // given
    final Context context = new Context("item123");

    // when
    final boolean result = validation.shouldRun(context);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("given_contextWithItem_when_shouldRun_then_returnFalse")
  void given_contextWithItem_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");
    final ItemResponse item = ItemResponse.builder().id("item123").name("Test Item").build();
    context.setItem(item);

    // when
    final boolean result = validation.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithNullItem_when_run_then_addErrorToContext")
  void given_contextWithNullItem_when_run_then_addErrorToContext() {
    // given
    final Context context = new Context("item123");

    // when
    validation.run(context);

    // then
    assertTrue(context.hasErrors());
    assertEquals(1, context.getErrors().size());
    assertEquals("Item not found", context.getErrors().get(0).getText());
    assertEquals(MessageType.ERROR, context.getErrors().get(0).getType());
  }
}
