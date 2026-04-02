package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.response.ItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemClientTest {

  private ItemClient itemClient;

  @BeforeEach
  void setUp() {
    itemClient = new ItemClient();
  }

  @Test
  void givenItemId_whenGetById_thenShouldReturnItemResponse() {
    // Given
    String itemId = "test-item-123";

    // When
    ItemResponse response = itemClient.getById(itemId);

    // Then
    assertNotNull(response);
    assertEquals(itemId, response.getId());
  }

  @Test
  void givenItemId_whenGetById_thenShouldReturnItemWithName() {
    // Given
    String itemId = "test-item-123";

    // When
    ItemResponse response = itemClient.getById(itemId);

    // Then
    assertNotNull(response.getName());
    assertEquals("item name", response.getName());
  }

  @Test
  void givenItemId_whenGetById_thenShouldReturnItemWithDescription() {
    // Given
    String itemId = "test-item-123";

    // When
    ItemResponse response = itemClient.getById(itemId);

    // Then
    assertNotNull(response.getDescription());
    assertEquals("Some item description", response.getDescription());
  }

  @Test
  void givenItemId_whenGetById_thenShouldReturnItemWithCategory() {
    // Given
    String itemId = "test-item-123";

    // When
    ItemResponse response = itemClient.getById(itemId);

    // Then
    assertNotNull(response.getCategoryResponse());
    assertNotNull(response.getCategoryResponse().getId());
  }

  @Test
  void givenItemId_whenGetById_thenShouldReturnItemWithPrice() {
    // Given
    String itemId = "test-item-123";

    // When
    ItemResponse response = itemClient.getById(itemId);

    // Then
    assertNotNull(response.getPrice());
    assertNotNull(response.getPrice().getPrice());
  }

  @Test
  void givenDifferentItemIds_whenGetById_thenShouldReturnDifferentIds() {
    // Given
    String itemId1 = "item-1";
    String itemId2 = "item-2";

    // When
    ItemResponse response1 = itemClient.getById(itemId1);
    ItemResponse response2 = itemClient.getById(itemId2);

    // Then
    assertEquals(itemId1, response1.getId());
    assertEquals(itemId2, response2.getId());
  }
}
