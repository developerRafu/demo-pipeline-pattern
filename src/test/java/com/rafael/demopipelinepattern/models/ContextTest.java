package com.rafael.demopipelinepattern.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.exception.BadRequest;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContextTest {

  private Context context;

  @BeforeEach
  void setUp() {
    context = new Context("test-item-id");
  }

  @Test
  void givenNewContext_whenCreated_thenIdShouldBeSet() {
    // Given & When
    Context newContext = new Context("item-123");

    // Then
    assertEquals("item-123", newContext.getId());
  }

  @Test
  void givenContext_whenAddFuture_thenFutureShouldBeRegistered() {
    // Given
    CompletableFuture<Void> future = new CompletableFuture<>();

    // When
    context.addFuture(StepDefinition.LOAD_ITEM, future);

    // Then
    assertNotNull(context.getFutureRequired(StepDefinition.LOAD_ITEM));
  }

  @Test
  void givenContext_whenGetFutureRequired_thenShouldReturnRegisteredFuture() {
    // Given
    CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
    context.addFuture(StepDefinition.LOAD_ITEM, future);

    // When
    CompletableFuture<Void> retrieved = context.getFutureRequired(StepDefinition.LOAD_ITEM);

    // Then
    assertEquals(future, retrieved);
  }

  @Test
  void givenContext_whenGetFutureRequiredForUnregisteredStep_thenShouldThrowException() {
    // Given & When & Then
    assertThrows(
        IllegalStateException.class, () -> context.getFutureRequired(StepDefinition.LOAD_ITEM));
  }

  @Test
  void givenContextWithoutErrors_whenHasErrors_thenShouldReturnFalse() {
    // Given & When
    boolean hasErrors = context.hasErrors();

    // Then
    assertFalse(hasErrors);
  }

  @Test
  void givenContextWithErrors_whenHasErrors_thenShouldReturnTrue() {
    // Given
    Message error =
        Message.builder()
            .text("Test error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    context.addError(error);

    // When
    boolean hasErrors = context.hasErrors();

    // Then
    assertTrue(hasErrors);
  }

  @Test
  void givenContextWithErrors_whenThrowBadRequest_thenShouldReturnBadRequestException() {
    // Given
    Message error =
        Message.builder()
            .text("Test error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build();
    context.addError(error);

    // When
    BadRequest badRequest = context.throwBadRequest("Test message");

    // Then
    assertNotNull(badRequest);
    assertEquals(1, badRequest.getErrors().size());
  }

  @Test
  void givenContext_whenSetAndGetItem_thenShouldStoreItem() {
    // Given
    ItemResponse item =
        ItemResponse.builder().id("item-1").name("Test Item").description("Test").build();

    // When
    context.setItem(item);

    // Then
    assertEquals(item, context.getItem());
  }

  @Test
  void givenContext_whenSetAndGetInventory_thenShouldStoreInventory() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder().id("inv-1").itemId("item-1").quantity(10).build();

    // When
    context.setInventory(inventory);

    // Then
    assertEquals(inventory, context.getInventory());
  }

  @Test
  void givenContext_whenSetAndGetPromotion_thenShouldStorePromotion() {
    // Given
    PromotionResponse promotion =
        PromotionResponse.builder().id("promo-1").itemId("item-1").percent(BigDecimal.TEN).build();

    // When
    context.setPromotion(promotion);

    // Then
    assertEquals(promotion, context.getPromotion());
  }

  @Test
  void givenContext_whenSetAndGetCatalogResponse_thenShouldStoreCatalogResponse() {
    // Given
    CatalogResponse catalogResponse = CatalogResponse.builder().build();

    // When
    context.setCatalogResponse(catalogResponse);

    // Then
    assertEquals(catalogResponse, context.getCatalogResponse());
  }
}
