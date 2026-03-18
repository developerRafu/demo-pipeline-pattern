package com.rafael.demopipelinepattern.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.client.InventoryClient;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("InventoryService")
class InventoryServiceTest {

  @Mock private InventoryClient inventoryClient;

  @InjectMocks private InventoryService inventoryService;

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_delegateToInventoryClient")
  void given_itemId_when_getByItemId_then_delegateToInventoryClient() {
    // given
    final String itemId = "item123";
    final InventoryResponse mockResponse =
        InventoryResponse.builder().id("inv1").itemId(itemId).quantity(10).available(true).build();
    when(inventoryClient.getByItemId(itemId)).thenReturn(mockResponse);

    // when
    final InventoryResponse result = inventoryService.getByItemId(itemId);

    // then
    assertNotNull(result);
    assertEquals(itemId, result.getItemId());
    verify(inventoryClient).getByItemId(itemId);
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnInventoryResponseFromClient")
  void given_itemId_when_getByItemId_then_returnInventoryResponseFromClient() {
    // given
    final String itemId = "item456";
    final InventoryResponse mockResponse =
        InventoryResponse.builder().id("inv2").itemId(itemId).quantity(25).available(true).build();
    when(inventoryClient.getByItemId(itemId)).thenReturn(mockResponse);

    // when
    final InventoryResponse result = inventoryService.getByItemId(itemId);

    // then
    assertEquals(25, result.getQuantity());
    assertTrue(result.getAvailable());
  }
}
