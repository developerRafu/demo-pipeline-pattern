package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionClientTest {

  private PromotionClient promotionClient;

  @BeforeEach
  void setUp() {
    promotionClient = new PromotionClient();
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnPromotionResponse() {
    // Given
    String itemId = "test-item-123";

    // When
    PromotionResponse response = promotionClient.getByItemId(itemId);

    // Then
    assertNotNull(response);
    assertEquals(itemId, response.getItemId());
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnPromotionWithId() {
    // Given
    String itemId = "test-item-123";

    // When
    PromotionResponse response = promotionClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getId());
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnPromotionWithDescription() {
    // Given
    String itemId = "test-item-123";

    // When
    PromotionResponse response = promotionClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getDescription());
  }

  @Test
  void givenItemId_whenGetByItemId_thenShouldReturnPromotionWithPercent() {
    // Given
    String itemId = "test-item-123";

    // When
    PromotionResponse response = promotionClient.getByItemId(itemId);

    // Then
    assertNotNull(response.getPercent());
    assertEquals(BigDecimal.valueOf(5), response.getPercent());
  }

  @Test
  void givenDifferentItemIds_whenGetByItemId_thenShouldReturnDifferentItemIds() {
    // Given
    String itemId1 = "item-1";
    String itemId2 = "item-2";

    // When
    PromotionResponse response1 = promotionClient.getByItemId(itemId1);
    PromotionResponse response2 = promotionClient.getByItemId(itemId2);

    // Then
    assertEquals(itemId1, response1.getItemId());
    assertEquals(itemId2, response2.getItemId());
  }
}
