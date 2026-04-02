package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryClientTest {

  private InventoryClient inventoryClient;

  @BeforeEach
  void setUp() {
    inventoryClient = new InventoryClient();
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnInventoryResponse() {
    // Given
    String itemId = "test-item-123";

    // When
    InventoryResponse response = inventoryClient.getByItemId(itemId);

    // Then
    assertNotNull(response);
    assertEquals(itemId, response.getItemId());
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnInventoryWithId() {
    // Given
    String itemId = "test-item-123";

    // When
    InventoryResponse response = inventoryClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getId());
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnInventoryWithQuantity() {
    // Given
    String itemId = "test-item-123";

    // When
    InventoryResponse response = inventoryClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getQuantity());
    assertTrue(response.getQuantity() >= 0);
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnInventoryWithAvailableStatus() {
    // Given
    String itemId = "test-item-123";

    // When
    InventoryResponse response = inventoryClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getAvailable());
  }

  @Test
  void givenDifferentItemIds_whenGetByItemId_thenShouldReturnDifferentItemIds() {
    // Given
    String itemId1 = "item-1";
    String itemId2 = "item-2";

    // When
    InventoryResponse response1 = inventoryClient.getByItemId(itemId1);
    InventoryResponse response2 = inventoryClient.getByItemId(itemId2);

    // Then
    assertEquals(itemId1, response1.getItemId());
    assertEquals(itemId2, response2.getItemId());
  }
}
