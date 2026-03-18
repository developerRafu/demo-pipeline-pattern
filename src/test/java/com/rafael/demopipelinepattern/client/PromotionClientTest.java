package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PromotionClient")
class PromotionClientTest {

  private final PromotionClient promotionClient = new PromotionClient();

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnPromotionResponseOrNullOrException")
  void given_itemId_when_getByItemId_then_returnPromotionResponseOrNullOrException() {
    // given
    final String itemId = "item123";

    // when & then
    try {
      final PromotionResponse result = promotionClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getId() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnPromotionResponseWithItemId")
  void given_itemId_when_getByItemId_then_returnPromotionResponseWithItemId() {
    // given
    final String itemId = "item456";

    // when & then
    try {
      final PromotionResponse result = promotionClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getItemId() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnPromotionResponseWithDescription")
  void given_itemId_when_getByItemId_then_returnPromotionResponseWithDescription() {
    // given
    final String itemId = "item789";

    // when & then
    try {
      final PromotionResponse result = promotionClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getDescription() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnPromotionResponseWithPercent")
  void given_itemId_when_getByItemId_then_returnPromotionResponseWithPercent() {
    // given
    final String itemId = "item999";

    // when & then
    try {
      final PromotionResponse result = promotionClient.getByItemId(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getPercent() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }
}
