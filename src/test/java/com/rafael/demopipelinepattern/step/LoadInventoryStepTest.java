package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.service.InventoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoadInventoryStep")
class LoadInventoryStepTest {

  @Mock private InventoryService inventoryService;

  @InjectMocks private LoadInventoryStep loadInventoryStep;

  @Test
  @DisplayName("given_contextWithId_when_run_then_setInventoryInContext")
  void given_contextWithId_when_run_then_setInventoryInContext() {
    // given
    final String itemId = "item123";
    final Context context = new Context(itemId);
    final InventoryResponse mockInventoryResponse =
        InventoryResponse.builder().id("inv1").itemId(itemId).quantity(10).available(true).build();
    when(inventoryService.getByItemId(itemId)).thenReturn(mockInventoryResponse);

    // when
    loadInventoryStep.run(context);

    // then
    assertNotNull(context.getInventory());
    assertEquals(itemId, context.getInventory().getItemId());
    verify(inventoryService).getByItemId(itemId);
  }

  @Test
  @DisplayName("given_contextWithId_when_run_then_delegateToInventoryService")
  void given_contextWithId_when_run_then_delegateToInventoryService() {
    // given
    final String itemId = "item456";
    final Context context = new Context(itemId);
    final InventoryResponse mockInventoryResponse =
        InventoryResponse.builder().id("inv2").itemId(itemId).quantity(25).available(true).build();
    when(inventoryService.getByItemId(itemId)).thenReturn(mockInventoryResponse);

    // when
    loadInventoryStep.run(context);

    // then
    assertEquals(25, context.getInventory().getQuantity());
    assertTrue(context.getInventory().getAvailable());
  }

  @Test
  @DisplayName("given_loadInventoryStep_when_definition_then_returnLoadInventoryDefinition")
  void given_loadInventoryStep_when_definition_then_returnLoadInventoryDefinition() {
    // when
    final StepDefinition definition = loadInventoryStep.definition();

    // then
    assertEquals(StepDefinition.LOAD_INVENTORY, definition);
  }
}
