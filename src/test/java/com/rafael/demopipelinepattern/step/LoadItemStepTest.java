package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoadItemStep")
class LoadItemStepTest {

  @Mock private ItemService itemService;

  @InjectMocks private LoadItemStep loadItemStep;

  @Test
  @DisplayName("given_contextWithId_when_run_then_setItemInContext")
  void given_contextWithId_when_run_then_setItemInContext() {
    // given
    final String itemId = "item123";
    final Context context = new Context(itemId);
    final ItemResponse mockItemResponse =
        ItemResponse.builder().id(itemId).name("Test Item").build();
    when(itemService.getById(itemId)).thenReturn(mockItemResponse);

    // when
    loadItemStep.run(context);

    // then
    assertNotNull(context.getItem());
    assertEquals(itemId, context.getItem().getId());
    verify(itemService).getById(itemId);
  }

  @Test
  @DisplayName("given_contextWithId_when_run_then_delegateToItemService")
  void given_contextWithId_when_run_then_delegateToItemService() {
    // given
    final String itemId = "item456";
    final Context context = new Context(itemId);
    final ItemResponse mockItemResponse =
        ItemResponse.builder().id(itemId).name("Another Item").build();
    when(itemService.getById(itemId)).thenReturn(mockItemResponse);

    // when
    loadItemStep.run(context);

    // then
    assertEquals("Another Item", context.getItem().getName());
  }

  @Test
  @DisplayName("given_loadItemStep_when_definition_then_returnLoadItemDefinition")
  void given_loadItemStep_when_definition_then_returnLoadItemDefinition() {
    // when
    final StepDefinition definition = loadItemStep.definition();

    // then
    assertEquals(StepDefinition.LOAD_ITEM, definition);
  }
}
