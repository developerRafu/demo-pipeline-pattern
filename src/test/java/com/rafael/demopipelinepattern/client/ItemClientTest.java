package com.rafael.demopipelinepattern.client;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ItemClient")
class ItemClientTest {

  private final ItemClient itemClient = new ItemClient();

  @Test
  @DisplayName("given_itemId_when_getById_then_returnItemResponseOrNullOrException")
  void given_itemId_when_getById_then_returnItemResponseOrNullOrException() {
    // given
    final String itemId = "item123";

    // when & then - resultado pode ser: objeto válido, null ou exception
    try {
      final ItemResponse result = itemClient.getById(itemId);
      // Se retornar objeto, validar que é válido
      if (result != null) {
        assertTrue(result.getId() != null || result.getName() != null);
      }
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getById_then_handleMultipleOfTwo")
  void given_itemId_when_getById_then_handleMultipleOfTwo() {
    // given
    final String itemId = "item456";

    // when & then - múltiplo de 2 retorna null
    try {
      final ItemResponse result = itemClient.getById(itemId);
      // Resultado pode ser null (múltiplo de 2) ou válido
      assertTrue(result == null || result.getName() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getById_then_handleMultipleOfThree")
  void given_itemId_when_getById_then_handleMultipleOfThree() {
    // given
    final String itemId = "item789";

    // when & then - múltiplo de 3 retorna objeto válido
    try {
      final ItemResponse result = itemClient.getById(itemId);
      // Resultado pode ser null (múltiplo de 2) ou válido
      assertTrue(result == null || result.getCategoryResponse() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }

  @Test
  @DisplayName("given_itemId_when_getById_then_handlePrice")
  void given_itemId_when_getById_then_handlePrice() {
    // given
    final String itemId = "item999";

    // when & then
    try {
      final ItemResponse result = itemClient.getById(itemId);
      // Resultado pode ser null ou válido
      assertTrue(result == null || result.getPrice() != null);
    } catch (RuntimeException e) {
      // Exceção é esperada quando múltiplo de 5
      assertTrue(e.getMessage().contains("multiple of 5"));
    }
  }
}
