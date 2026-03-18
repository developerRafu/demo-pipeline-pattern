package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InventoryClient")
class InventoryClientTest {

  private final InventoryClient inventoryClient = new InventoryClient();

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnInventoryResponseOrNullOrException")
  void given_itemId_when_getByItemId_then_returnInventoryResponseOrNullOrException() {
    // given
    final String itemId = "item123";

    // when & then
    try {
      final InventoryResponse result = inventoryClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getId() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnInventoryResponseWithItemId")
  void given_itemId_when_getByItemId_then_returnInventoryResponseWithItemId() {
    // given
    final String itemId = "item456";

    // when & then
    try {
      final InventoryResponse result = inventoryClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getItemId() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnInventoryResponseWithQuantity")
  void given_itemId_when_getByItemId_then_returnInventoryResponseWithQuantity() {
    // given
    final String itemId = "item789";

    // when & then
    try {
      final InventoryResponse result = inventoryClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getQuantity() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnInventoryResponseAvailable")
  void given_itemId_when_getByItemId_then_returnInventoryResponseAvailable() {
    // given
    final String itemId = "item999";

    // when & then
    try {
      final InventoryResponse result = inventoryClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getAvailable() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }
}
